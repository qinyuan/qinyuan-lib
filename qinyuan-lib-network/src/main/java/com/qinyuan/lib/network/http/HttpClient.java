package com.qinyuan.lib.network.http;

import com.qinyuan.lib.lang.MapUtils;
import com.qinyuan.lib.network.url.UrlBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Wrap HttpClient of Apache
 * Created by qinyuan on 14-12-24.
 */
public class HttpClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);
    // default connection timeout is 10 seconds
    public final static int DEFAULT_TIMEOUT = 10000;
    private final static HttpUserAgentBuilder userAgentBuilder = new HttpUserAgentBuilder();

    private final HttpClientBuilder clientBuilder = HttpClientBuilder.create();
    private IProxy proxy;
    private int timeout = DEFAULT_TIMEOUT;
    private int requestTimeout = DEFAULT_TIMEOUT;
    private String userAgent = userAgentBuilder.buildRandomly();
    private int lastConnectTime = Integer.MAX_VALUE;
    private ProxyRecorder proxyRecorder;
    private Method method = Method.GET;

    public void setProxy(IProxy proxy) {
        this.proxy = proxy;
    }

    public IProxy getProxy() {
        return this.proxy;
    }

    public void setProxyRecorder(ProxyRecorder proxyRecorder) {
        this.proxyRecorder = proxyRecorder;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public int getLastConnectTime() {
        return this.lastConnectTime;
    }

    /*public CloseableHttpResponse postResponse(String url) throws IOException {
        if (!url.contains("://")) {
            url = "http://" + url;
        }
        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent", this.userAgent);

        // set config
        RequestConfig.Builder configBuilder = RequestConfig.custom()
                .setConnectTimeout(this.timeout)
                .setConnectionRequestTimeout(this.requestTimeout);
        if (proxy != null) {
            configBuilder.setProxy(new HttpHost(proxy.getHost(), proxy.getPort(), proxy.getType()));
        }
        post.setConfig(configBuilder.build());

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("hello", "world"));
        post.setEntity(new UrlEncodedFormEntity(params));

        LOGGER.info("connecting {} with proxy {}", url, proxy);
        return clientBuilder.build().execute(post);
    }*/

    private HttpRequestBase getRequest(String url) {
        switch (method) {
            case POST:
                return new HttpPost(url);
            default:
                return new HttpGet(url);
        }
    }

    public CloseableHttpResponse getResponse(String url) {
        return getResponse(url, null);
    }

    public CloseableHttpResponse getResponse(String url, Map<String, String> params) {
        try {
            if (!url.contains("://")) {
                url = "http://" + url;
            }

            if (MapUtils.isNotEmpty(params) && method.equals(Method.GET)) {
                url = new UrlBuilder().setBaseUrl(url).addParams(params).build();
            }

            HttpRequestBase request = getRequest(url);
            request.setHeader("User-Agent", this.userAgent);

            // set config
            RequestConfig.Builder configBuilder = RequestConfig.custom()
                    .setConnectTimeout(this.timeout)
                    .setConnectionRequestTimeout(this.requestTimeout);
            if (proxy != null) {
                configBuilder.setProxy(new HttpHost(proxy.getHost(), proxy.getPort(), proxy.getType()));
            }
            request.setConfig(configBuilder.build());

            if (MapUtils.isNotEmpty(params) && request instanceof HttpPost) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                ((HttpPost) request).setEntity(new UrlEncodedFormEntity(paramList));
            }

            LOGGER.info("connecting {} with proxy {}", url, proxy);
            return clientBuilder.build().execute(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse get(String url) {
        return get(url, null);
    }

    public HttpResponse get(String url, Map<String, String> params) {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("blank url: " + url);
        }

        url = url.trim();

        try {
            long startTime = System.currentTimeMillis();
            CloseableHttpResponse response = this.getResponse(url, params);
            LOGGER.info("connected to {} with proxy {}", url, proxy);
            String content = EntityUtils.toString(response.getEntity());
            LOGGER.info("parse content of {}", url);

            int status = response.getStatusLine().getStatusCode();
            LOGGER.info("parse status of {}", url);

            this.lastConnectTime = (int) (System.currentTimeMillis() - startTime);
            return new HttpResponse(content, status);
        } catch (Exception e) {
            LOGGER.error("fail to connect or parse {} with proxy {}, info: {}", url, proxy, e);
            this.lastConnectTime = Integer.MAX_VALUE;
            throw new java.lang.RuntimeException(e);
        } finally {
            this.recordSpeed();
        }
    }

    private void recordSpeed() {
        if (this.proxyRecorder != null) {
            this.proxyRecorder.recordSpeed(proxy, this.lastConnectTime);
        }
    }

    /**
     * After request, feed back that the request is rejected by validating the result
     */
    public void feedbackRejection(String url) {
        this.proxyRecorder.recordRejection(proxy, url);
        feedbackError();
    }

    public void feedbackError() {
        this.lastConnectTime = Integer.MAX_VALUE;
        this.recordSpeed();
    }

    public String getContent(String url) {
        return get(url).getContent();
    }

    public String getContent(String url, Map<String, String> params) {
        return get(url, params).getContent();
    }


    public void download(String url, String fileName) throws IOException {
        download(url, new File(fileName));
    }

    public void download(String url, File file) throws IOException {
        String content = getContent(url);
        FileUtils.write(file, content);
    }

    public static enum Method {
        POST, GET
    }
}
