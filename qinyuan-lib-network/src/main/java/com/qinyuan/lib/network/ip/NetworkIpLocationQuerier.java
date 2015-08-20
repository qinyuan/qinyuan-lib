package com.qinyuan.lib.network.ip;

import com.qinyuan.lib.network.http.HttpClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Query ip location information from network
 * Created by qinyuan on 15-7-27.
 */
public abstract class NetworkIpLocationQuerier implements IpLocationQuerier {
    private final static Logger LOGGER = LoggerFactory.getLogger(NetworkIpLocationQuerier.class);

    private IpLocationSaver ipLocationSaver;

    public void setIpLocationSaver(IpLocationSaver ipLocationSaver) {
        this.ipLocationSaver = ipLocationSaver;
    }

    @Override
    public String getLocation(String ip) {
        if (!IpUtils.isIPv4(ip)) {
            throw new IllegalArgumentException("Invalid ip address: " + ip);
        }

        HttpClient client = new HttpClient();
        String content = client.getContent(getUrl(ip));
        if (StringUtils.isBlank(content)) {
            LOGGER.warn("Location data is empty: {}", content);
            return null;
        }

        String location = getDataParser().parse(content);
        if (location != null) {
            location = location.trim();
        }

        if (this.ipLocationSaver != null) {
            this.ipLocationSaver.save(ip, location);
        }

        return location;
    }

    protected abstract String getUrl(String ip);

    protected abstract IpLocationDataParser getDataParser();
}
