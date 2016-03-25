package com.qinyuan.lib.network.url;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class about url
 * Created by qinyuan on 15-5-11.
 */
public class UrlUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

    private UrlUtils() {
    }

    public static String decode(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        try {
            return URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Fail to encode url {}, info: {}", url, e);
            return url;
        }
    }

    public static String encode(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        try {
            return URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Fail to encode url {}, info: {}", url, e);
            return url;
        }
    }

    public static String getHost(String url) {
        int fromIndex = url.indexOf("://");
        int endIndex;
        if (fromIndex >= 0) {
            endIndex = url.indexOf('/', fromIndex + 3);
        } else {
            endIndex = url.indexOf('/');
        }

        if (endIndex > 0) {
            return url.substring(0, endIndex);
        } else {
            return url;
        }
    }

    public static String trimParameters(String url) {
        if (StringUtils.isBlank(url) || !url.contains("?")) {
            return url;
        } else {
            return url.replaceAll("\\?.*$", "");
        }
    }

    public static Map<String, String> parseParameters(String url) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(url) || !url.contains("?")) {
            return map;
        }

        url = url.replaceAll("^.*\\?", "").replaceAll("#.*$", "");
        String[] keyValues = url.split("&");
        for (String keyValue : keyValues) {
            if (StringUtils.isBlank(keyValue)) {
                continue;
            }
            String[] arr = keyValue.split("=");
            String key = arr[0];
            String value = arr.length > 1 ? arr[1] : "";
            map.put(key, value);
        }

        return map;
    }
}
