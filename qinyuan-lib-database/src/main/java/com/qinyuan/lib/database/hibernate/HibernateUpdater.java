package com.qinyuan.lib.database.hibernate;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to update data
 * Created by qinyuan on 15-9-1.
 */
public class HibernateUpdater {
    private final static Logger LOGGER = LoggerFactory.getLogger(HibernateUpdater.class);

    private final SQLConditionBuilder conditionBuilder = new SQLConditionBuilder();
    private final HibernateQueryBuilder queryBuilder = new HibernateQueryBuilder();

    /**
     * add filter condition
     *
     * @param filter filter clause such as "hello=:hello"
     * @return Object itself
     */
    public HibernateUpdater addFilter(String filter) {
        conditionBuilder.addFilter(filter);
        return this;
    }

    public HibernateUpdater addEqualFilter(String field, Object value) {
        conditionBuilder.addEqualFilter(field);
        return this.addArgument(field, value);
    }

    public HibernateUpdater addArgument(String key, Object value) {
        this.queryBuilder.addArgument(key, value);
        return this;
    }

    public void update(Class<?> clazz, String setClause) {
        Session session = HibernateUtils.getSession();
        String hql = "UPDATE " + clazz.getSimpleName() + " SET " + setClause
                + " " + conditionBuilder.build();
        try {
            this.queryBuilder.buildQuery(session, hql).executeUpdate();
        } catch (Throwable e) {
            LOGGER.error("fail to update, hql: {}, info: {}", hql, e);
            throw e;
        } finally {
            HibernateUtils.commit(session);// ensure session is closed
        }
    }

    /**
     * delete data by raw SQL
     */
    public void updateBySQL(String sql) {
        Session session = HibernateUtils.getSession();
        try {
            sql = sql + conditionBuilder.build();
            this.queryBuilder.buildSQLQuery(session, sql).executeUpdate();
        } catch (Throwable e) {
            LOGGER.error("fail to update, sql: {}, list: {}", sql, e);
            throw e;
        } finally {
            HibernateUtils.commit(session);// ensure session is closed
        }
    }
}
