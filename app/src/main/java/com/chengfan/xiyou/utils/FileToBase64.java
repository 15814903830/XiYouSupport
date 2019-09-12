package com.chengfan.xiyou.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.chengfan.xiyou.ui.UIApplication;
import com.zero.ci.network.http.rest.binary.BasicBinary;
import com.zero.ci.network.http.rest.binary.FileBinary;
import com.zero.ci.network.http.rest.binary.InputStreamBinary;
import com.zero.ci.network.zrequest.response.UploadFile;
import com.zero.ci.widget.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-20/21:26
 * @Description:
 */
public class FileToBase64 {
    public static String getBase64FromInputStream(InputStream in) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String encode64 = android.util.Base64.encodeToString(data, android.util.Base64.DEFAULT);

        return encode64;
    }
  // Logger.d("FileToBase64  : " + file.getMode());

    public static String best64(UploadFile file) {
        /**
         * 将图片转换成Base64编码的字符串
         */
            if(TextUtils.isEmpty(""+file.getMode())){
                return null;
            }
            InputStream is = null;
            byte[] data = null;
            String result = null;
            try{
                is = new FileInputStream(""+compressReSave(file.getMode().toString(),UIApplication.context,600));
                //创建一个字符流大小的数组。
                data = new byte[is.available()];
                //写入数组
                is.read(data);
                //用默认的编码格式进行编码
                result = Base64.encodeToString(data,Base64.DEFAULT);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(null !=is){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            return result;
    }


    public static String best64(String file) {
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(compressReSave(file,UIApplication.context,300));
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }



    /**
     * 质量压缩
     *
     * @param srcPath 原图路径
     * @param context
     * @param max 要压缩到多大以内（单位：kb）
     * @return
     */
    public static  String compressReSave(String srcPath, Context context, int max) {
        String filePath = "";
        Bitmap image = BitmapFactory.decodeFile(srcPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > max) { // 循环判断如果压缩后图片是否大于maxkb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            if (options > 10){
                options -= 10;// 每次都减少10
            } else {
                options -= 5;
            }

            if (options == 5){//这里最多压缩到5，options不能小于0
                break;
            }
        }
        FileOutputStream outStream = null;
        filePath = createFile(context, "myImg");
        try {
            outStream = new FileOutputStream(filePath);
            // 把数据写入文件
            outStream.write(baos.toByteArray());
            // 记得要关闭流！
            outStream.close();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (outStream != null) {
                    // 记得要关闭流！
                    outStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static String createFile(Context context, String pathName) {
        String path = "";
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + pathName);
        } else {
            file = new File(context.getFilesDir().getPath() + File.separator + pathName);
        }
        if (file != null) {
            if (!file.exists()) {
                file.mkdir();
            }
            File output = new File(file, System.currentTimeMillis() + ".png");
            try {
                if (output.exists()) {
                    output.delete();
                } else {
                    output.createNewFile();
                }
                path = output.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }









}
