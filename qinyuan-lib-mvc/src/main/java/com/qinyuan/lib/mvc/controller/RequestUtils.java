package com.qinyuan.lib.mvc.controller;

import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

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

    /**
     * get the real address when user visit via proxy
     *
     * @param request request object
     * @return real address of user
     */
    public static String getRealRemoteAddress(HttpServletRequest request) {
        String address = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(address)) {
            return request.getRemoteAddr();
        } else {
            return address.split(",")[0].trim();
        }
    }

    private static String toJson(Object obj) {
        return new GsonBuilder().create().toJson(obj);
    }
}
