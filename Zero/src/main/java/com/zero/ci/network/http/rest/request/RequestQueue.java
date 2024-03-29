package com.zero.ci.network.http.rest.request;

import com.zero.ci.network.http.rest.response.OnResponseListener;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Request Queue.
 */
public class RequestQueue {

    private AtomicInteger mInteger = new AtomicInteger();

    private final BlockingQueue<Request<?>> mUnFinishQueue = new LinkedBlockingDeque<>();
    private final BlockingQueue<Request<?>> mRequestQueue = new PriorityBlockingQueue<>();
    private final Map<Request<?>, RequestMessenger<?>> mMessengerMap = new LinkedHashMap<>();

    /**
     * Request queue polling thread array.
     */
    private RequestDispatcher[] mDispatchers;

    /**
     * Create handle queue manager.
     *
     * @param threadPoolSize number of thread pool.
     */
    public RequestQueue(int threadPoolSize) {
        mDispatchers = new RequestDispatcher[threadPoolSize];
    }

    /**
     * Start polling the handle queue, a one of the implementation of the download task, if you have started to poll
     * the download queue, then it will stop all the threads, to re create thread
     * execution.
     */
    public void start() {
        stop();
        for (int i = 0; i < mDispatchers.length; i++) {
            RequestDispatcher networkDispatcher = new RequestDispatcher(mRequestQueue, mUnFinishQueue, mMessengerMap);
            mDispatchers[i] = networkDispatcher;
            networkDispatcher.start();
        }
    }

    /**
     * Add a handle task to download queue, waiting for execution, if there is no task in the queue or the number of
     * tasks is less than the number of thread pool, will be executed immediately.
     *
     * @param what     the "what" will be the response is returned to you, so you can introduce multiple
     *                 {@link Request} results in an A with what, please distinguish which is the result of the
     *                 {@link Request}.
     * @param request  {@link Request}.
     * @param listener {@link OnResponseListener}.
     * @param <T>      {@link T}.
     */
    public <T> void add(int what, Request<T> request, OnResponseListener<T> listener) {
        request.setSequence(mInteger.incrementAndGet());
        mMessengerMap.put(request, RequestMessenger.newInstance(what, listener));
        mUnFinishQueue.add(request);
        mRequestQueue.add(request);
    }

    /**
     * Don't start return handle queue size.
     *
     * @return size.
     * @deprecated use {@link #unStartSize()} instead.
     */
    @Deprecated
    public int size() {
        return unStartSize();
    }

    /**
     * Don't start return request queue size.
     *
     * @return size.
     */
    public int unStartSize() {
        return mRequestQueue.size();
    }

    /**
     * Returns have started but not the end of the request queue size.
     *
     * @return size.
     */
    public int unFinishSize() {
        return mUnFinishQueue.size();
    }

    /**
     * Polling the queue will not be executed, and this will not be canceled.
     */
    public void stop() {
        for (RequestDispatcher dispatcher : mDispatchers) if (dispatcher != null) dispatcher.quit();
    }

    /**
     * All requests for the sign specified in the queue, if you are executing, will interrupt the task
     *
     * @param sign this sign will be the same as sign's Request, and if it is the same, then cancel the task.
     */
    public void cancelBySign(Object sign) {
        synchronized (mUnFinishQueue) {
            for (Request<?> request : mUnFinishQueue) request.cancelBySign(sign);
        }
    }

    /**
     * Cancel all requests, Already in the execution of the handle can't use this method
     */
    public void cancelAll() {
        synchronized (mUnFinishQueue) {
            for (Request<?> request : mUnFinishQueue) request.cancel();
        }
    }
}