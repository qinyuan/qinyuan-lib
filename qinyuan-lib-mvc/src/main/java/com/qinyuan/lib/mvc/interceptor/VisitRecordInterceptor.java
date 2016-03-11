package com.qinyuan.lib.mvc.interceptor;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor to get visit record
 * Created by qinyuan on 16-3-11.
 */
public class VisitRecordInterceptor extends AbstractInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (isRequestToResources(request)) {
            return;
        }


    }
}
