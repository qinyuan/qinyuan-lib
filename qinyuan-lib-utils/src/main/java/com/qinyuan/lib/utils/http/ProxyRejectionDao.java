package com.qinyuan.lib.utils.http;

import com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.utils.database.hibernate.HibernateUtils;
import com.qinyuan.lib.utils.mvc.controller.PaginationItemFactory;

import java.util.List;

/**
 * Dao about ProxyRejection
 * Created by qinyuan on 15-6-4.
 */
public class ProxyRejectionDao {

    public ProxyRejection getInstance(Integer id) {
        return HibernateUtils.get(ProxyRejection.class, id);
    }

    public boolean hasInstance(int proxyId, String host) {
        int count = new HibernateListBuilder()
                .addFilter("host=:host")
                .addFilter("proxyId=:proxyId")
                .addArgument("host", host)
                .addArgument("proxyId", proxyId)
                .count(ProxyRejection.class);
        return count > 0;
    }

    public static Factory factory() {
        return new Factory();
    }

    public static class Factory implements PaginationItemFactory<ProxyRejection> {
        @Override
        public int getCount() {
            return new HibernateListBuilder().count(ProxyRejection.class);
        }

        @Override
        public List<ProxyRejection> getInstances(int firstResult, int maxResults) {
            return new HibernateListBuilder().addOrder("rejectTime", false).addOrder("id", false)
                    .limit(firstResult, maxResults).build(ProxyRejection.class);
        }
    }
}
