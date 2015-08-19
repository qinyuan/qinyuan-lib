package com.qinyuan.lib.utils.ip;

public class DatabaseIpLocationSaver implements IpLocationSaver {
    private final IpLocationDao dao = new IpLocationDao();

    @Override
    public void save(String ip, String location) {
        dao.save(ip, location);
    }
}
