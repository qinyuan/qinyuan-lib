package com.qinyuan.lib.mvc.controller;

import javax.servlet.http.HttpServletRequest;

public class UserAgent {

    private final String userAgentString;

    public UserAgent(String userAgentString) {
        this.userAgentString = userAgentString;
    }

    public UserAgent(HttpServletRequest request) {
        this(parse(request));
    }

    public OS getOS() {
        if (userAgentString.contains("Android")) {
            return OS.ANDROID;
        } else if (userAgentString.contains("iPhone") || userAgentString.contains("iPad")) {
            return OS.IOS;
        } else if (userAgentString.contains("Linux")) {
            return OS.LINUX;
        } else if (userAgentString.contains("Windows NT")) {
            return OS.WINDOWS;
        } else {
            return OS.OTHER;
        }
    }

    public Browser getBrowser() {
        if (userAgentString.contains("MSIE")) {
            return Browser.IE;
        } else if (userAgentString.contains("Firefox")) {
            return Browser.FIREFOX;
        } else if (userAgentString.contains("OPR")) {
            return Browser.OPERA;
        } else if (userAgentString.contains("Chrome")) {
            return Browser.CHROME;
        } else if (userAgentString.contains("Safari")) {
            return Browser.SAFARI;
        } else {
            return Browser.OTHER;
        }
    }

    public static enum OS {
        LINUX, WINDOWS, ANDROID, IOS, OTHER
    }

    public static enum Browser {
        FIREFOX, CHROME, OPERA, SAFARI, IE, OTHER
    }

    public static String parse(HttpServletRequest request) {
        return request.getHeader("user-Agent");
    }
}
