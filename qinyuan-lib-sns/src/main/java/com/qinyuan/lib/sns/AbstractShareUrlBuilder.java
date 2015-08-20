package com.qinyuan.lib.sns;

import com.qinyuan.lib.network.url.UrlUtils;

import java.util.Map;

public abstract class AbstractShareUrlBuilder implements ShareUrlBuilder {
    @Override
    public String build() {
        String url = getPageUrl();
        for (Map.Entry<String, String> entry : getParams().entrySet()) {
            if (url.contains("?")) {
                url += "&";
            } else {
                url += "?";
            }
            if (entry.getValue() != null) {
                url += entry.getKey() + "=" + UrlUtils.encode(entry.getValue());
            } else {
                url += entry.getKey() + "=";
            }
        }
        return url;
    }

    protected abstract String getPageUrl();

    protected abstract Map<String, String> getParams();
}
