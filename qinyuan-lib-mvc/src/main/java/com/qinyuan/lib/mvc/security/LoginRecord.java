package com.qinyuan.lib.mvc.security;

import com.qinyuan.lib.database.hibernate.PersistObject;
import com.qinyuan.lib.lang.time.DateUtils;

public class LoginRecord extends PersistObject {
    private Integer userId;
    private String loginTime;
    private String ip;
    private String location;
    private String platform;

    public Integer getUserId() {
        return userId;
    }

    public String getLoginTime() {
        return DateUtils.trimMilliSecond(loginTime);
    }

    public String getIp() {
        return ip;
    }

    public String getLocation() {
        return location;
    }

    public String getPlatform() {
        return platform;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
