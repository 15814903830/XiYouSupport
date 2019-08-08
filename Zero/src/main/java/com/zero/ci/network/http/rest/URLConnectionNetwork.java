package com.zero.ci.network.http.rest;

import com.zero.ci.network.http.rest.headers.Headers;
import com.zero.ci.network.http.tools.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * Implement the network layer based on HttpURLConnection.
 */
public class URLConnectionNetwork implements Network {

    private HttpURLConnection mUrlConnection;

    public URLConnectionNetwork(HttpURLConnection urlConnection) {
        this.mUrlConnection = urlConnection;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return mUrlConnection.getOutputStream();
    }

    @Override
    public int getResponseCode() throws IOException {
        return mUrlConnection.getResponseCode();
    }

    @Override
    public Map<String, List<String>> getResponseHeaders() {
        return mUrlConnection.getHeaderFields();
    }

    @Override
    public InputStream getServerStream(int responseCode, Headers headers) throws IOException {
        return URLConnectionNetworkExecutor.getServerStream(responseCode, headers.getContentEncoding(), mUrlConnection);
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeQuietly(mUrlConnection);
    }
}