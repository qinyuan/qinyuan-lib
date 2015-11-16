package com.qinyuan.lib.network.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {
    }

    public static String parseJsonFromJsonp(String jsonp) {
        if (StringUtils.isBlank(jsonp)) {
            return null;
        }

        int startIndex = jsonp.indexOf("(");
        int endIndex = jsonp.indexOf(")");
        if (startIndex >= 0 && startIndex < endIndex) {
            return jsonp.substring(startIndex + 1, endIndex).trim();
        } else {
            return null;
        }
    }

    public static Object parseJsonObjectFromJsonp(String jsonp) {
        String json = parseJsonFromJsonp(jsonp);
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(json, Object.class);
        } catch (IOException e) {
            LOGGER.error("fail to parse json object from '{}', info: {}", jsonp, e);
            throw new RuntimeException(e);
        }
    }
}
