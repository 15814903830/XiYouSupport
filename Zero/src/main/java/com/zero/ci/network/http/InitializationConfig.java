package com.zero.ci.network.http;

import android.content.Context;

import com.zero.ci.network.http.cache.CacheEntity;
import com.zero.ci.network.http.cache.DBCacheStore;
import com.zero.ci.network.http.cache.DiskCacheStore;
import com.zero.ci.network.http.cookie.DBCookieStore;
import com.zero.ci.network.http.rest.NetworkExecutor;
import com.zero.ci.network.http.rest.URLConnectionNetworkExecutor;
import com.zero.ci.network.http.rest.Interceptor;
import com.zero.ci.network.http.ssl.SSLUtils;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/**
 * Initialize the save parameters.
 */
public final class InitializationConfig {

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    private Context mContext;

    private int mConnectTimeout;
    private int mReadTimeout;

    private int mRetryCount;
    private SSLSocketFactory mSSLSocketFactory;
    private HostnameVerifier mHostnameVerifier;

    private MultiValueMap<String, String> mHeaders;
    private MultiValueMap<String, String> mParams;

    private CookieStore mCookieStore;
    private CookieManager mCookieManager;
    private CacheStore<CacheEntity> mCacheStore;

    private NetworkExecutor mNetworkExecutor;

    private Interceptor mInterceptor;

    private InitializationConfig(Builder builder) {
        this.mContext = builder.mContext;

        this.mConnectTimeout = builder.mConnectTimeout;
        this.mReadTimeout = builder.mReadTimeout;

        this.mRetryCount = builder.mRetryCount;
        this.mSSLSocketFactory = builder.mSSLSocketFactory;
        if (this.mSSLSocketFactory == null)
            this.mSSLSocketFactory = SSLUtils.defaultSSLSocketFactory();

        this.mHostnameVerifier = builder.mHostnameVerifier;
        if (this.mHostnameVerifier == null)
            this.mHostnameVerifier = SSLUtils.defaultHostnameVerifier();

        this.mHeaders = builder.mHeaders;
        this.mParams = builder.mParams;

        mCookieStore = builder.mCookieStore;
        if (mCookieStore == null)
            mCookieStore = new DBCookieStore(mContext);
        this.mCookieManager = new CookieManager(mCookieStore, CookiePolicy.ACCEPT_ALL);

        this.mCacheStore = builder.mCacheStore;
        if (this.mCacheStore == null)
            this.mCacheStore = new DBCacheStore(mContext);

        this.mNetworkExecutor = builder.mNetworkExecutor;
        if (this.mNetworkExecutor == null)
            this.mNetworkExecutor = new URLConnectionNetworkExecutor();

        this.mInterceptor = builder.mInterceptor;
    }

    public Context getContext() {
        return mContext;
    }

    public int getConnectTimeout() {
        return mConnectTimeout;
    }

    public int getReadTimeout() {
        return mReadTimeout;
    }

    public int getRetryCount() {
        return mRetryCount;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return mSSLSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return mHostnameVerifier;
    }

    public MultiValueMap<String, String> getHeaders() {
        return mHeaders;
    }

    public MultiValueMap<String, String> getParams() {
        return mParams;
    }

    public CookieStore getCookieStore() {
        return mCookieStore;
    }

    public CookieManager getCookieManager() {
        return mCookieManager;
    }

    public CacheStore<CacheEntity> getCacheStore() {
        return mCacheStore;
    }

    public NetworkExecutor getNetworkExecutor() {
        return mNetworkExecutor;
    }

    public Interceptor getInterceptor() {
        return mInterceptor;
    }

    public final static class Builder {

        private Context mContext;

        private int mConnectTimeout = 10 * 1000;
        private int mReadTimeout = 10 * 1000;

        private int mRetryCount;
        private SSLSocketFactory mSSLSocketFactory;
        private HostnameVerifier mHostnameVerifier;

        private MultiValueMap<String, String> mHeaders = new LinkedMultiValueMap<>();
        private MultiValueMap<String, String> mParams = new LinkedMultiValueMap<>();

        private CookieStore mCookieStore;
        private CacheStore<CacheEntity> mCacheStore;

        private NetworkExecutor mNetworkExecutor;

        private Interceptor mInterceptor;

        private Builder(Context context) {
            this.mContext = context.getApplicationContext();
        }

        /**
         * Connection timed out.
         *
         * @param millis millisecond.
         */
        public Builder connectionTimeout(int millis) {
            this.mConnectTimeout = millis;
            return this;
        }

        /**
         * Read the server's data timeout.
         *
         * @param millis millisecond.
         */
        public Builder readTimeout(int millis) {
            this.mReadTimeout = millis;
            return this;
        }

        /**
         * Retry count.
         */
        public Builder retry(int count) {
            this.mRetryCount = count;
            return this;
        }

        /**
         * Global SSLSocketFactory.
         *
         * @param sslSocketFactory {@link SSLSocketFactory}, {@link SSLUtils}.
         * @see SSLUtils
         */
        public Builder sslSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.mSSLSocketFactory = sslSocketFactory;
            return this;
        }

        /**
         * Global HostnameVerifier.
         */
        public Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.mHostnameVerifier = hostnameVerifier;
            return this;
        }

        /**
         * Add the global handle header.
         */
        public Builder addHeader(String key, String value) {
            mHeaders.add(key, value);
            return this;
        }

        /**
         * Add the global handle param.
         */
        public Builder addParam(String key, String value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Set the CookieStore.
         *
         * @see DBCacheStore
         */
        public Builder cookieStore(CookieStore cookieStore) {
            this.mCookieStore = cookieStore;
            return this;
        }

        /**
         * Set the CacheStore.
         *
         * @see DBCacheStore
         * @see DiskCacheStore
         */
        public Builder cacheStore(CacheStore<CacheEntity> cacheStore) {
            this.mCacheStore = cacheStore;
            return this;
        }

        /**
         * Set the NetworkExecutor, such as: URLConnectionNetworkExecutor, OkHttpNetworkExecutor.
         *
         * @see URLConnectionNetworkExecutor
         */
        public Builder networkExecutor(NetworkExecutor executor) {
            this.mNetworkExecutor = executor;
            return this;
        }

        public Builder interceptor(Interceptor interceptor) {
            this.mInterceptor = interceptor;
            return this;
        }

        public InitializationConfig build() {
            return new InitializationConfig(this);
        }
    }

}
