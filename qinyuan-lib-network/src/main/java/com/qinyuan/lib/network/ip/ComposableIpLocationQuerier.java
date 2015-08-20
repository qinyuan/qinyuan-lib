package com.qinyuan.lib.network.ip;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ComposableIpLocationQuerier implements IpLocationQuerier {
    private final static Logger LOGGER = LoggerFactory.getLogger(ComposableIpLocationQuerier.class);
    private final List<IpLocationQuerier> queriers;

    public ComposableIpLocationQuerier(List<IpLocationQuerier> queriers) {
        this.queriers = queriers;
    }

    public ComposableIpLocationQuerier(IpLocationQuerier querier, IpLocationQuerier... queriers) {
        List<IpLocationQuerier> list = Lists.newArrayList(querier);
        list.addAll(Lists.newArrayList(queriers));
        this.queriers = list;
    }

    @Override
    public String getLocation(String ip) {
        for (IpLocationQuerier querier : this.queriers) {
            if (querier == null) {
                continue;
            }
            try {
                String location = querier.getLocation(ip);
                if (StringUtils.isNotBlank(location)) {
                    return location;
                }
            } catch (Exception e) {
                LOGGER.error("Fail to get location, ip: {}, querier: {}, info: {}", ip, querier, e);
            }
        }
        return null;
    }
}
