package com.qinyuan.lib.mvc.controller;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return !isInvalidRequest(request);
    }

    protected boolean isRequestToResources(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains("/resources/css/") || uri.contains("/resources/js/");
    }

    private final static String[] invalidRequestPrefixes = {"/plus/", "/phpmyadmin", "/testproxy.php", "/wwwroot.rar",
            "/data/", "/cdn-cgi/", "/uc_server/", "/wp-", "/console/", "/web.rar", "/web.zip", "/wwwroot.rar",
            "/wwwroot.zip", "/www_", "/web/", "/VSServices/", "/user/", "/resources.zip", "/domlog", "/domcfg", "/.",
            "info.php"};

    private final static String[] invalidRequestSuffixes = {".php"};

    private boolean isInvalidRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        for (String suffix : invalidRequestSuffixes) {
            if (suffix.endsWith(suffix)) {
                return true;
            }
        }

        for (String prefix : invalidRequestPrefixes) {
            if (uri.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
