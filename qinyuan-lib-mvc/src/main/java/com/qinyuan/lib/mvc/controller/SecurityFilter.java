package com.qinyuan.lib.mvc.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!isInvalidRequest(request)) {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    private final static String[] invalidRequestPrefixes = {"/plus/", "/phpmyadmin", "/testproxy.php", "/wwwroot.rar",
            "/data/", "/cdn-cgi/", "/uc_server/", "/wp-", "/console/", "/web.rar", "/web.zip", "/wwwroot.rar",
            "/wwwroot.zip", "/www_", "/web/", "/VSServices/", "/user/", "/resources.zip", "/domlog", "/domcfg", "/.",
            "info.php"};

    private final static String[] invalidRequestSuffixes = {".php"};

    private boolean isInvalidRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            return false;
        }

        String uri = ((HttpServletRequest) request).getRequestURI();
        for (String suffix : invalidRequestSuffixes) {
            if (uri.endsWith(suffix)) {
                return true;
            }
        }
        String path = ((HttpServletRequest) request).getServletPath();
        for (String prefix : invalidRequestPrefixes) {
            if (uri.startsWith(prefix) || path.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
