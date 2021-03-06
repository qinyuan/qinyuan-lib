package com.qinyuan.lib.network.ip;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parse ip content from taobao
 * Created by qinyuan on 15-7-27.
 */
class SogouIpLocationDataParser implements IpLocationDataParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(SogouIpLocationDataParser.class);

    @Override
    public String parse(String locationData) {
        if (StringUtils.isBlank(locationData)) {
            LOGGER.error("Fail to page location, location data is empty: {}", locationData);
            return null;
        }

        String location = locationData.trim().replace("\");", "").replace("');", "");
        return location.replaceAll("^.*\\s", "");
    }
}
