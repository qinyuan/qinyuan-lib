package com.qinyuan.lib.mvc.controller;

import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils {
    public static void addJavaScriptData(HttpServletRequest request, String key, Object value) {
        final String mapKey = "javascriptDatas";
        if (request.getAttribute(mapKey) == null) {
            request.setAttribute(mapKey, new HashMap<>());
        }
        @SuppressWarnings("unchecked")
        Map<String, String> datas = (Map) request.getAttribute(mapKey);
        datas.put(key, toJson(value));
    }

    public static void addAttributeAndJavaScriptData(HttpServletRequest request, String key, Object value) {
        request.setAttribute(key, value);
        addJavaScriptData(request, key, value);
    }

    private static String toJson(Object obj) {
        return new GsonBuilder().create().toJson(obj);
    }
}
