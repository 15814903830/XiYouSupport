package com.zero.ci.widget.lightkv;


import android.util.SparseArray;

import com.zero.ci.widget.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.concurrent.Executor;

/**
 * AsyncKV writes data extremely fast. <br/>
 * With MappedByteBuffer,we just need to put the data to buffer,
 * it will auto flush data to disk by system.<br/>
 * And with the record of offset, we can write data randomly. <br/>
 */
public class AsyncKV extends LightKV {
    private static final String TAG = "AsyncKV";

    private static final int GC_THRESHOLD = 1024;

    private FileChannel mChannel;
    private MappedByteBuffer mBuffer;
    private int mInvalidBytes = 0;

    AsyncKV(String path, String name, Class keyDefineClass,
            Executor executor , Encoder encoder) {
        super(path, name, keyDefineClass,
                executor, encoder, ASYNC_MODE);
    }

    @Override
    protected ByteBuffer loadData(String path) throws IOException {
        File file = new File(path, mFileName + ".kv");
        if (!LightKVUtils.existFile(file)) {
            throw new IllegalStateException("can not open file:" + mFileName);
        }
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        mChannel = accessFile.getChannel();
        long length = alignLength(accessFile.length());
        mBuffer = mChannel.map(FileChannel.MapMode.READ_WRITE, 0, length);
        return mBuffer;
    }

    @Override
    protected void clean(int invalidBytes) throws IOException {
        if (invalidBytes > GC_THRESHOLD || invalidBytes < 0) {
            if (invalidBytes > GC_THRESHOLD) {
                int fileLen = mBuffer.capacity();
                long newLen = alignLength(fileLen - invalidBytes);
                if (newLen != fileLen) {
                    mChannel.truncate(newLen);
                    mBuffer = mChannel.map(FileChannel.MapMode.READ_WRITE, 0, newLen);
                }
            }

            // compact
            mBuffer.clear();
            Parser.collect(mData, mBuffer);
            mDataEnd = mBuffer.position();
            while (mBuffer.remaining() >= 8) {
                mBuffer.putLong(0L);
            }
            while (mBuffer.hasRemaining()) {
                mBuffer.put((byte) 0);
            }
            mInvalidBytes = 0;
        } else {
            mDataEnd = mBuffer.position();
            mInvalidBytes = invalidBytes;
        }
    }

    private void ensureSize(int allocate) {
        final int end = mDataEnd;
        if (end + allocate > mBuffer.capacity()) {
            mBuffer.force();
            long newLen = alignLength(end + allocate);
            try {
                mBuffer = mChannel.map(FileChannel.MapMode.READ_WRITE, 0, newLen);
            } catch (IOException e) {
                Logger.e(e.getMessage());
            }
        }
        mBuffer.position(end);
    }

    @Override
    public synchronized void putBoolean(int key, boolean value) {
        Container.BooleanContainer container = (Container.BooleanContainer) mData.get(key);
        if (container == null) {
            ensureSize(5);
            mBuffer.putInt(key);
            mBuffer.put((byte) (value ? 1 : 0));
            mData.put(key, new Container.BooleanContainer(mDataEnd, value));
            mDataEnd += 5;
        } else if (container.value != value) {
            mBuffer.position(container.offset + 4);
            mBuffer.put((byte) (value ? 1 : 0));
            container.value = value;
        }
    }

    @Override
    public synchronized void putInt(int key, int value) {
        Container.IntContainer container = (Container.IntContainer) mData.get(key);
        if (container == null) {
            ensureSize(8);
            mBuffer.putInt(key);
            mBuffer.putInt(value);
            mData.put(key, new Container.IntContainer(mDataEnd, value));
            mDataEnd += 8;
        } else if (container.value != value) {
            mBuffer.position(container.offset + 4);
            mBuffer.putInt(value);
            container.value = value;
        }
    }

    @Override
    public synchronized void putFloat(int key, float value) {
        Container.FloatContainer container = (Container.FloatContainer) mData.get(key);
        if (container == null) {
            ensureSize(8);
            mBuffer.putInt(key);
            mBuffer.putFloat(value);
            mData.put(key, new Container.FloatContainer(mDataEnd, value));
            mDataEnd += 8;
        } else if (container.value != value) {
            mBuffer.position(container.offset + 4);
            mBuffer.putFloat(value);
            container.value = value;
        }
    }

    @Override
    public synchronized void putLong(int key, long value) {
        Container.LongContainer container = (Container.LongContainer) mData.get(key);
        if (container == null) {
            ensureSize(12);
            mBuffer.putInt(key);
            mBuffer.putLong(value);
            mData.put(key, new Container.LongContainer(mDataEnd, value));
            mDataEnd += 12;
        } else if (container.value != value) {
            mBuffer.putLong(container.offset + 4, value);
            container.value = value;
        }
    }

    @Override
    public synchronized void putDouble(int key, double value) {
        Container.DoubleContainer container = (Container.DoubleContainer) mData.get(key);
        if (container == null) {
            ensureSize(12);
            mBuffer.putInt(key);
            mBuffer.putDouble(value);
            mData.put(key, new Container.DoubleContainer(mDataEnd, value));
            mDataEnd += 12;
        } else if (container.value != value) {
            mBuffer.putDouble(container.offset + 4, value);
            container.value = value;
        }
    }

    /**
     * insert or update string
     *
     * @param key   key
     * @param value should not be null normally, it will be dealt with remove if value is null
     */
    @Override
    public synchronized void putString(int key, String value) {
        if (value == null) {
            remove(key);
        } else {
            Container.StringContainer container = (Container.StringContainer) mData.get(key);
            if (container == null) {
                byte[] bytes = value.getBytes();
                bytes = (key & DataType.ENCODE) != 0 ? mEncoder.encode(bytes) : bytes;
                int offset = wrapArray(key, bytes);
                mData.put(key, new Container.StringContainer(offset, value, bytes));
            } else if (!value.equals(container.value)) {
                byte[] bytes = value.getBytes();
                bytes = (key & DataType.ENCODE) != 0 ? mEncoder.encode(bytes) : bytes;
                if (container.bytes.length == bytes.length) {
                    mBuffer.position(container.offset + 8);
                    mBuffer.put(bytes);
                    container.value = value;
                    container.bytes = bytes;
                } else {
                    // mark old string in buffer invalid
                    mBuffer.putInt(container.offset, key | 0x80000000);
                    int offset = wrapArray(key, bytes);
                    mData.put(key, new Container.StringContainer(offset, value, bytes));
                }
            }
        }
    }

    /**
     * insert or update array
     *
     * @param key   key
     * @param value should not be null normally, it will be dealt with remove if value is null
     */
    @Override
    public synchronized void putArray(int key, byte[] value) {
        if (value == null) {
            remove(key);
        } else {
            Container.ArrayContainer container = (Container.ArrayContainer) mData.get(key);
            if (container == null) {
                byte[] bytes = (key & DataType.ENCODE) != 0 ? mEncoder.encode(value) : value;
                int offset = wrapArray(key, bytes);
                mData.put(key, new Container.ArrayContainer(offset, value, bytes));
            } else if (!Arrays.equals(value, container.value)) {
                byte[] bytes = (key & DataType.ENCODE) != 0 ? mEncoder.encode(value) : value;
                if (container.bytes.length == bytes.length) {
                    mBuffer.position(container.offset + 8);
                    mBuffer.put(bytes);
                    container.value = value;
                    container.bytes = bytes;
                } else {
                    // mark old array in buffer invalid
                    mBuffer.putInt(container.offset, key | 0x80000000);
                    int offset = wrapArray(key, bytes);
                    mData.put(key, new Container.ArrayContainer(offset, value, bytes));
                }
            }
        }
    }

    private int wrapArray(int key, byte[] bytes) {
        ensureSize(8 + bytes.length);
        int end = mDataEnd;
        mBuffer.putInt(key);
        mBuffer.putInt(bytes.length);
        mBuffer.put(bytes);
        mDataEnd += 8 + bytes.length;
        return end;
    }

    @Override
    public synchronized void remove(int key) {
        int index = mData.indexOfKey(key);
        if (index >= 0) {
            Container.BaseContainer container = (Container.BaseContainer) mData.valueAt(index);
            if (container != null) {
                mBuffer.putInt(container.offset, key | 0x80000000);
                mData.removeAt(index);
                checkGC(key, container);
            }
        }
    }

    private void checkGC(int key, Container.BaseContainer container) {
        mInvalidBytes += getContainerLength(key, container);
        if (mInvalidBytes >= PAGE_SIZE) {
            try {
                clean(mInvalidBytes);
            } catch (IOException e) {
                Logger.e(e.getMessage());
            }
        }
    }

    @Override
    public synchronized void clear() {
        int fileLen = mBuffer.capacity();
        if (fileLen != PAGE_SIZE) {
            try {
                mChannel.truncate(PAGE_SIZE);
                mBuffer = mChannel.map(FileChannel.MapMode.READ_WRITE, 0, PAGE_SIZE);
            } catch (Exception e) {
                Logger.e(e.getMessage());
            }
        }
        eraseData();
    }

    private void eraseData() {
        mBuffer.clear();
        while (mBuffer.hasRemaining()) {
            mBuffer.putLong(0L);
        }
        mBuffer.clear();
        mDataEnd = 0;
        mData.clear();
        mInvalidBytes = 0;
    }

    public synchronized void copy(final LightKV src) {
        if (src == null || this.mMode != src.mMode) {
            return;
        }
        eraseData();

        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (src) {
            if (src.mDataEnd <= 0) {
                return;
            }

            mDataEnd = src.mDataEnd;

            SparseArray<Object> data = src.mData;
            int n = data.size();
            for (int i = 0; i < n; i++) {
                mData.put(data.keyAt(i), data.valueAt(i));
            }

            AsyncKV otherKV = (AsyncKV) src;

            int newCapacity = otherKV.mBuffer.capacity();
            if (newCapacity != mBuffer.capacity()) {
                try {
                    mChannel.truncate(newCapacity);
                    mBuffer = mChannel.map(FileChannel.MapMode.READ_WRITE, 0, newCapacity);
                } catch (Exception e) {
                    Logger.e(e.getMessage());
                }
            }

            otherKV.mBuffer.position(src.mDataEnd);
            otherKV.mBuffer.flip();
            mBuffer.put(otherKV.mBuffer);
        }
    }

    /**
     * System will auto flush, so it's not recommended to invoke this method.
     */
    @Override
    public synchronized void commit() {
        mBuffer.force();
    }
}
