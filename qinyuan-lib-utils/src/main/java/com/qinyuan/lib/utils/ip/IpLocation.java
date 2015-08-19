package com.qinyuan.lib.utils.ip;

import com.qinyuan.lib.utils.database.hibernate.PersistObject;

public class IpLocation extends PersistObject {
    private String ip;
    private String location;

    public String getLocation() {
        return location;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
