package com.qinyuan.lib.utils.database.hibernate;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Class to build Hibernate list
 * Created by qinyuan on 15-5-26.
 */
public class HibernateListBuilder {
    private final static Logger LOGGER = LoggerFactory.getLogger(HibernateListBuilder.class);

    private final SQLConditionBuilder conditionBuilder = new SQLConditionBuilder();
    private final HibernateQueryBuilder queryBuilder = new HibernateQueryBuilder();

    /**
     * add filter condition
     *
     * @param filter filter clause such as "hello=:hello"
     * @return Object itself
     */
    public HibernateListBuilder addFilter(String filter) {
        conditionBuilder.addFilter(filter);
        return this;
    }

    public HibernateListBuilder addEqualFilter(String field, Object value) {
        if (field.contains(".")) {
            String adjustField = field.replace(",", "__");
            conditionBuilder.addFilter(field + "=:" + adjustField);
            return this.addArgument(adjustField, value);
        } else {
            conditionBuilder.addEqualFilter(field);
            return this.addArgument(field, value);
        }
    }

    public HibernateListBuilder addOrder(String field, boolean asc) {
        conditionBuilder.addOrder(field, asc);
        return this;
    }

    public HibernateListBuilder addGroup(String field) {
        conditionBuilder.addGroup(field);
        return this;
    }

    public HibernateListBuilder limit(int firstResult, int maxResults) {
        this.queryBuilder.limit(firstResult, maxResults);
        return this;
    }

    public HibernateListBuilder addArgument(String key, Object value) {
        this.queryBuilder.addArgument(key, value);
        return this;
    }

    public int count(Class<?> clazz) {
        return count(clazz.getSimpleName());
    }

    public int count(String tableName) {
        String hql = "SELECT COUNT(*) FROM " + tableName;
        return convertCountResultToInt(getFirstItem(hql));
    }

    public int countBySQL(String tableName) {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        return convertCountResultToInt(getFirstItemBySQL(sql));
    }

    private int convertCountResultToInt(Object countResult) {
        if (countResult instanceof Number) {
            return ((Number) countResult).intValue();
        } else {
            countResult = ((Object[]) countResult)[0];
            return ((Number) countResult).intValue();
        }
    }

    public <T> List<T> build(Class<T> clazz) {
        Session session = HibernateUtils.getSession();
        try {
            String hql = "FROM " + clazz.getSimpleName() + conditionBuilder.build();
            @SuppressWarnings("unchecked")
            List<T> list = this.queryBuilder.buildQuery(session, hql).list();
            return list;
        } catch (Throwable e) {
            LOGGER.error("fail to get list: {}", e);
            throw e;
        } finally {
            session.close();   // ensure session is closed
        }
    }

    public List build(String hql) {
        Session session = HibernateUtils.getSession();
        try {
            hql = hql.trim();
            if (!hql.toLowerCase().startsWith("from") && !hql.toLowerCase().startsWith("select")) {
                hql = "FROM " + hql;
            }
            hql += conditionBuilder.build();
            @SuppressWarnings("unchecked")
            List list = this.queryBuilder.buildQuery(session, hql).list();
            return list;
        } catch (Throwable e) {
            LOGGER.error("fail to get list: {}", e);
            throw e;
        } finally {
            session.close();   // ensure session is closed
        }
    }

    /**
     * build list by raw SQL
     *
     * @return list build by raw SQL
     */
    public List<Object[]> buildBySQL(String sql) {
        Session session = HibernateUtils.getSession();
        try {
            sql = sql + conditionBuilder.build();
            @SuppressWarnings("unchecked")
            List<Object[]> list = this.queryBuilder.buildSQLQuery(session, sql).list();
            return list;
        } catch (Throwable e) {
            LOGGER.error("fail to get list: {}", e);
            throw e;
        } finally {
            session.close();   // ensure session is closed
        }
    }

    public <T> T getFirstItem(Class<T> clazz) {
        List<T> items = build(clazz);
        return items.size() == 0 ? null : items.get(0);
    }

    public Object getFirstItem(String hql) {
        List items = build(hql);
        return items.size() == 0 ? null : items.get(0);
    }

    public Object getFirstItemBySQL(String sql) {
        List items = buildBySQL(sql);
        return items.size() == 0 ? null : items.get(0);
    }
}
