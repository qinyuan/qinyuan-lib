package com.qinyuan.lib.mvc.interceptor;

import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan.lib.mvc.controller.UserAgent;
import com.qinyuan.lib.mvc.visitor.VisitorRecorder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor to get visit record
 * Created by qinyuan on 16-3-11.
 */
public class VisitRecordInterceptor extends AbstractInterceptor {
    private VisitorRecorder visitorRecorder;

    public VisitRecordInterceptor(VisitorRecorder visitorRecorder) {
        this.visitorRecorder = visitorRecorder;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        if (isRequestToResources(request)) {
            return;
        }

        String ip = request.getRemoteAddr();
        String time = DateUtils.nowString();
        String userAgent = UserAgent.parse(request);
        String url = request.getRequestURL().toString();
        visitorRecorder.add(ip, time, userAgent, url);
    }
}
