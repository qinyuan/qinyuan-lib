package com.qinyuan.lib.mvc.controller;

import javax.servlet.http.HttpServletRequest;

public class UserAgentUtils {
    private UserAgentUtils() {
    }

    public static boolean isMobileUserAgent(HttpServletRequest request) {
        UserAgent userAgent = new UserAgent(request);

        UserAgent.OS os = userAgent.getOS();
        return os.equals(UserAgent.OS.IOS) || os.equals(UserAgent.OS.ANDROID);
    }
}
