package com.qinyuan.lib.mvc.visitor;

class VisitRecord {
    private String ip;
    private String time;
    private String userAgent;
    private String url;

    VisitRecord(String ip, String time, String userAgent, String url) {
        this.ip = ip;
        this.time = time;
        this.userAgent = userAgent;
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public String getTime() {
        return time;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getUrl() {
        return url;
    }
}
