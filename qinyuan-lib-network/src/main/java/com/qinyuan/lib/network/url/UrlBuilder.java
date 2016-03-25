package com.qinyuan.lib.network.url;

import com.google.common.base.Joiner;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.lang.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to build url with parameters
 * Created by qinyuan on 15-5-10.
 */
public class UrlBuilder {
    private final static Logger LOGGER = LoggerFactory.getLogger(UrlBuilder.class);

    private Map<String, Object> params = new HashMap<>();
    private String baseUrl;

    public UrlBuilder addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    public UrlBuilder addParams(Map<String, ?> params) {
        if (MapUtils.isNotEmpty(params)) {
            this.params.putAll(params);
        }
        return this;
    }

    public UrlBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String build() {
        if (StringUtils.isBlank(this.baseUrl)) {
            LOGGER.error("invalid baseUrl '{}'", baseUrl);
            return null;
        }

        String url = baseUrl;
        if (params.size() == 0) {
            return url;
        }

        if (!url.endsWith("?")) {
            url += "?";
        }

        List<String> keyValues = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }

            if (value instanceof Integer && (!IntegerUtils.isPositive((Integer) value))) {
                continue;
            }

            if (value instanceof String && (StringUtils.isBlank((String) value))) {
                continue;
            }

            keyValues.add(entry.getKey() + "=" + UrlUtils.encode(value.toString()));
        }

        String paramsString = Joiner.on("&").join(keyValues);
        return url + paramsString;
    }
}
