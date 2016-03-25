package com.qinyuan.lib.network.http;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan.lib.network.url.UrlUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Class to visit certain url by given interval regularly
 * Created by qinyuan on 16-1-11.
 */
public class TimingVisitor {
    public final static int DEFAULT_INTERVAL = 60;  // default interval is 1 minute
    private int interval = DEFAULT_INTERVAL;
    private String getUrls;
    private String postUrls;

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
                url = "http://localhost/" + url;
            }
            HttpClient client = new HttpClient();
            client.setMethod(post ? HttpClient.Method.POST : HttpClient.Method.GET);
            client.get(UrlUtils.trimParameters(url), UrlUtils.parseParameters(url));
        }
    }

    private boolean isLocalUrl(String url) {
        return !url.startsWith("http://") && !url.startsWith("wwww.");
    }
}
