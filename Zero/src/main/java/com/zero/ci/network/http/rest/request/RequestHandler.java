package com.zero.ci.network.http.rest.request;

import android.os.SystemClock;

import com.zero.ci.network.http.Connection;
import com.zero.ci.network.http.HttpConnection;
import com.zero.ci.network.http.cache.CacheEntity;
import com.zero.ci.network.http.error.NotFoundCacheError;
import com.zero.ci.network.http.rest.CacheMode;
import com.zero.ci.network.http.rest.Interceptor;
import com.zero.ci.network.http.rest.NetworkExecutor;
import com.zero.ci.network.http.rest.headers.Headers;
import com.zero.ci.network.http.rest.response.Response;
import com.zero.ci.network.http.rest.response.RestResponse;
import com.zero.ci.network.http.CacheStore;
import com.zero.ci.network.http.tools.HeaderUtils;
import com.zero.ci.network.http.tools.IOUtils;

import java.io.IOException;

/**
 * Parse and execute the default behavior of NoHttp.
 * 配置NoHttp默认参数
 */
public class RequestHandler {

    private CacheStore<CacheEntity> mCacheStore;
    private HttpConnection mHttpConnection;
    private Interceptor mInterceptor;

    public RequestHandler(CacheStore<CacheEntity> cache, NetworkExecutor executor) {
        this(cache, new HttpConnection(executor));
    }

    public RequestHandler(CacheStore<CacheEntity> cache, HttpConnection httpConnection) {
        this.mCacheStore = cache;
        this.mHttpConnection = httpConnection;
    }

    public RequestHandler(CacheStore<CacheEntity> cache, NetworkExecutor executor, Interceptor interceptor) {
        this.mCacheStore = cache;
        this.mHttpConnection = new HttpConnection(executor);
        this.mInterceptor = interceptor;
    }

    public <T> Response<T> handle(Request<T> request) {
        long startTime = SystemClock.elapsedRealtime();

        if (mInterceptor != null) {
            RequestHandler handler = new RequestHandler(mCacheStore, mHttpConnection);
            return mInterceptor.intercept(handler, request);
        } else {
            String cacheKey = request.getCacheKey();
            CacheMode cacheMode = request.getCacheMode();
            CacheEntity localCache = mCacheStore.get(cacheKey);

            Protocol protocol = requestCacheOrNetwork(cacheMode, localCache, request);
            handleCache(cacheKey, cacheMode, localCache, protocol);

            T result = null;
            if (protocol.exception == null) {
                try {
                    result = request.parseResponse(protocol.headers, protocol.body);
                } catch (Exception e) {
                    protocol.exception = e;
                }
            }

            return new RestResponse<>(request, protocol.fromCache, protocol.headers, result,
                    SystemClock.elapsedRealtime() - startTime, protocol.exception);
        }
    }

    private Protocol requestCacheOrNetwork(CacheMode cacheMode, CacheEntity localCache, Request<?> request) {
        Protocol protocol = null;
        switch (cacheMode) {
            case ONLY_READ_CACHE: {// Only read cache.
                protocol = new Protocol();
                if (localCache == null)
                    protocol.exception = new NotFoundCacheError("The cache mode is ONLY_READ_CACHE, but did not find the cache.");
                else {
                    protocol.headers = localCache.getResponseHeaders();
                    protocol.body = localCache.getData();
                    protocol.fromCache = true;
                }
                break;
            }
            case ONLY_REQUEST_NETWORK: { // Only handle network.
                protocol = getHttpProtocol(request);
                break;
            }
            case NONE_CACHE_REQUEST_NETWORK: { // CacheStore none handle network.
                if (localCache != null) {
                    protocol = new Protocol();
                    protocol.headers = localCache.getResponseHeaders();
                    protocol.body = localCache.getData();
                    protocol.fromCache = true;
                } else
                    protocol = getHttpProtocol(request);
                break;
            }
            case REQUEST_NETWORK_FAILED_READ_CACHE: { // Request network failed read cache.
                setRequestCacheHeader(request, localCache);
                protocol = getHttpProtocol(request);
                if (protocol.exception != null && localCache != null) {
                    protocol.headers = localCache.getResponseHeaders();
                    protocol.body = localCache.getData();
                    protocol.fromCache = true;

                    protocol.exception = null;
                }
                break;
            }
            case DEFAULT: { // Default, Comply with the RFC2616.
                if (localCache != null && localCache.getLocalExpire() > System.currentTimeMillis()) {
                    protocol = new Protocol();
                    protocol.headers = localCache.getResponseHeaders();
                    protocol.body = localCache.getData();
                    protocol.fromCache = true;
                } else {
                    setRequestCacheHeader(request, localCache);
                    protocol = getHttpProtocol(request);
                }
                break;
            }
        }
        return protocol;
    }

    /**
     * Perform the handle before, Handle the cache headers.
     *
     * @param request     the handle object.
     * @param cacheEntity cached entities.
     */
    private void setRequestCacheHeader(BasicRequest<?> request, CacheEntity cacheEntity) {
        if (cacheEntity == null) {
            request.getHeaders().remove(Headers.HEAD_KEY_IF_NONE_MATCH);
            request.getHeaders().remove(Headers.HEAD_KEY_IF_MODIFIED_SINCE);
        } else {
            Headers headers = cacheEntity.getResponseHeaders();
            String eTag = headers.getETag();
            if (eTag != null)
                request.getHeaders().set(Headers.HEAD_KEY_IF_NONE_MATCH, eTag);

            long lastModified = headers.getLastModified();
            if (lastModified > 0)
                request.getHeaders().set(Headers.HEAD_KEY_IF_MODIFIED_SINCE, HeaderUtils.formatMillisToGMT(lastModified));
        }
    }

    /**
     * Handle retries, and complete the handle network here.
     *
     * @param request handle object.
     * @return {@link Protocol}.
     */
    private Protocol getHttpProtocol(BasicRequest<?> request) {
        Protocol result = new Protocol();
        Connection connection = mHttpConnection.getConnection(request);
        result.headers = connection.responseHeaders();
        result.exception = connection.exception();
        if (result.exception == null && connection.serverStream() != null) {
            try {
                result.body = IOUtils.toByteArray(connection.serverStream());
            } catch (IOException e) {
                result.exception = e;
            }
        }
        IOUtils.closeQuietly(connection);
        return result;
    }

    /**
     * Process the response cache.
     */
    private void handleCache(String cacheKey, CacheMode cacheMode, CacheEntity localCache, Protocol result) {
        if (result.exception == null) {// Successfully.
            int responseCode = result.headers.getResponseCode();

            if (responseCode == 304) {
                if (localCache != null) { // Fix server error for 304.
                    result.fromCache = true;
                    result.headers = localCache.getResponseHeaders();
                    result.headers.set(Headers.HEAD_KEY_RESPONSE_CODE, "304");
                    result.body = localCache.getData();
                }
            } else {
                if (localCache == null) {
                    switch (cacheMode) {
                        case ONLY_READ_CACHE:// Only read cache.
                        case ONLY_REQUEST_NETWORK: {// Only handle network.
                            break;
                        }
                        case NONE_CACHE_REQUEST_NETWORK:// CacheStore none handle network.
                        case REQUEST_NETWORK_FAILED_READ_CACHE: {// Request network failed read cache.
                            long localExpire = HeaderUtils.getLocalExpires(result.headers);
                            localCache = new CacheEntity();
                            localCache.setResponseHeaders(result.headers);
                            localCache.setData(result.body);
                            localCache.setLocalExpire(localExpire);
                            mCacheStore.replace(cacheKey, localCache);
                            break;
                        }
                        case DEFAULT: {// Default, Comply with the RFC2616.
                            long localExpire = HeaderUtils.getLocalExpires(result.headers);
                            long lastModify = result.headers.getLastModified();
                            if (localExpire <= 0 && lastModify <= 0) return;
                            localCache = new CacheEntity();
                            localCache.setResponseHeaders(result.headers);
                            localCache.setData(result.body);
                            localCache.setLocalExpire(localExpire);
                            mCacheStore.replace(cacheKey, localCache);
                            break;
                        }
                    }

                } else if (!result.fromCache) {
                    long localExpire = HeaderUtils.getLocalExpires(result.headers);
                    localCache.setLocalExpire(localExpire);
                    localCache.getResponseHeaders().setAll(result.headers);
                    localCache.setData(result.body);
                    mCacheStore.replace(cacheKey, localCache);
                }
            }
        }
    }

    private static class Protocol {
        /**
         * Server response header.
         */
        private Headers headers;
        /**
         * Is the data from the cache.
         */
        private boolean fromCache;
        /**
         * Data.
         */
        private byte[] body;
        /**
         * Exception of connection.
         */
        private Exception exception;
    }
}