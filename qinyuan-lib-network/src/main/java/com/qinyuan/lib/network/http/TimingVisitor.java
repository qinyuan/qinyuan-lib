package com.qinyuan.lib.network.http;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Class to visit certain url by given interval regularly
 * Created by qinyuan on 16-1-11.
 */
public class TimingVisitor {
    public final static int DEFAULT_INTERVAL = 60;  // default interval is 1 minute
    private int interval = DEFAULT_INTERVAL;
    private String pages;

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public void init() {
        (new Thread() {
            @Override
            public void run() {
                while (true) {
                    ThreadUtils.sleep(interval);
                    try {
                        if (StringUtils.isBlank(pages)) {
                            continue;
                        }

                        String[] urls = pages.split(",");
                        for (String url : urls) {
                            if (isLocalUrl(url)) {
                                url = " http://localhost/" + url;
                            }
                            new HttpClient().get(url);
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private boolean isLocalUrl(String url) {
        return !url.startsWith("http://") && !url.startsWith("wwww.");
    }
}
