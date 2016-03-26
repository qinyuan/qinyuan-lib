package com.qinyuan.lib.network.http;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan.lib.network.url.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to visit certain url by given interval regularly
 * Created by qinyuan on 16-1-11.
 */
public class TimingVisitor {
    private final static Logger LOGGER = LoggerFactory.getLogger(TimingVisitor.class);
    public final static int DEFAULT_INTERVAL = 60;  // default interval is 1 minute
    private int interval = DEFAULT_INTERVAL;
    private String getUrls;
    private String postUrls;
    private String host = "localhost";
    private int port = 80;
    private String context = "";

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setGetUrls(String getUrls) {
        this.getUrls = getUrls;
    }

    public void setPostUrls(String postUrls) {
        this.postUrls = postUrls;
    }

    public void init() {
        (new Thread() {
            @Override
            public void run() {
                while (true) {
                    ThreadUtils.sleep(interval);
                    try {
                        if (StringUtils.isNotBlank(getUrls)) {
                            visitUrl(getUrls, false);
                        }
                        if (StringUtils.isNotBlank(postUrls)) {
                            visitUrl(postUrls, true);
                        }


                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void visitUrl(String urls, boolean post) {
        for (String url : urls.split(",")) {
            if (isLocalUrl(url)) {
                if (StringUtils.isNotBlank(context)) {
                    url = context + "/" + url;
                }
                url = host + ":" + port + "/" + url;
                while (url.startsWith("/")) {
                    url = url.substring(1);
                }
                url = url.replaceAll("/{2,}", "/");
                url = "http://" + url;
            }
            HttpClient client = new HttpClient();
            client.setMethod(post ? HttpClient.Method.POST : HttpClient.Method.GET);
            String content = client.getContent(UrlUtils.trimParameters(url), UrlUtils.parseParameters(url));
            LOGGER.info("Content FROM url {}: {}", url, content);
        }
    }

    private boolean isLocalUrl(String url) {
        return !url.startsWith("http://") && !url.startsWith("wwww.");
    }
}
