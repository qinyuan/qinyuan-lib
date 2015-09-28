package com.qinyuan.lib.network.http;

import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.time.DateUtils;

/**
 * Class to activate proxy
 * Created by qinyuan on 15-6-26.
 */
public class ProxyActivator {

    public void activateByProxyRejectionId(Integer proxyRejectionId) {
        ProxyRejection proxyRejection = new ProxyRejectionDao().getInstance(proxyRejectionId);
        if (proxyRejection == null) {
            return;
        }

        Proxy proxy = new ProxyDao().getInstance(proxyRejection.getProxyId());
        if (proxy == null) {
            return;
        }

        proxy.setSpeed(proxyRejection.getSpeed());
        proxy.setSpeedUpdateTime(DateUtils.nowString());
        HibernateUtils.update(proxy);
    }
}
