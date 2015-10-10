package com.qinyuan.lib.database.hibernate;

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
    private int firstResult = -1;
    private int maxResults = -1;

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
            String adjustField = field.replace(".", "__");
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
        this.firstResult = firstResult;
        this.maxResults = maxResults;
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

    @SuppressWarnings("unchecked")
    public <T> List<T> build(Class<T> clazz) {
        return build(buildHqlByClass(clazz), this.firstResult, this.maxResults);
    }

    private String buildHqlByClass(Class<?> clazz) {
        return "FROM " + clazz.getSimpleName();
    }

    public List build(String hql) {
        return build(adjustHql(hql), this.firstResult, this.maxResults);
    }

    private String adjustHql(String hql) {
        hql = hql.trim();
        String lowerCaseHql = hql.toLowerCase();
        if (lowerCaseHql.startsWith("distinct")) {
            hql = "SELECT " + hql;
        } else if (!lowerCaseHql.startsWith("from") && !lowerCaseHql.startsWith("select")) {
            hql = "FROM " + hql;
        }
        return hql;
    }

    private List build(String hql, int firstResult, int maxResults) {
        hql += conditionBuilder.build();
        Session session = HibernateUtils.getSession();
        try {
            @SuppressWarnings("unchecked")
            List list = this.queryBuilder.limit(firstResult, maxResults).buildQuery(session, hql).list();
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
        return buildBySQL(sql, Object[].class);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> buildBySQL(String sql, Class<T> clazz) {
        sql = sql + conditionBuilder.build();
        return buildBySQL(sql, this.firstResult, this.maxResults);
    }

    public List buildBySQL(String finalSql, int firstResult, int maxResults) {
        Session session = HibernateUtils.getSession();
        try {
            return this.queryBuilder.limit(firstResult, maxResults).buildSQLQuery(session, finalSql).list();
        } catch (Throwable e) {
            LOGGER.error("fail to get list: {}", e);
            throw e;
        } finally {
            session.close();   // ensure session is closed
        }
    }

    public <T> T getFirstItem(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        List<T> items = build(buildHqlByClass(clazz), 0, 1);
        return items.size() == 0 ? null : items.get(0);
    }

    public Object getFirstItem(String hql) {
        List items = build(adjustHql(hql), 0, 1);
        return items.size() == 0 ? null : items.get(0);
    }

    public Object getFirstItemBySQL(String sql) {
        List items = buildBySQL(sql, 0, 1);
        return items.size() == 0 ? null : items.get(0);
    }
}
