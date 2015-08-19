package com.qinyuan.lib.utils.database.hibernate;

import com.qinyuan.lib.utils.IntegerUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * Util class for Hibernate
 * Created by qinyuan on 14-12-26.
 */
public class HibernateUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(HibernateUtils.class);

    public final static String CONFIG_FILE = "hibernate.cfg.xml";
    private final static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = getConfiguration();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable e) {
            LOGGER.error("fail to connect database: {}", e);
            throw e;
        }
    }

    static Configuration getConfiguration() {
        return new Configuration().configure(CONFIG_FILE);
    }

    public static Session getSession() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    public static void commit(Session session) {
        try {
            session.getTransaction().commit();
        } catch (Throwable e) {
            LOGGER.error("fail to commit: {}", e);
            throw e;
        } finally {
            session.close();    /// ensure session is closed
        }
    }

    public static Integer save(Object object) {
        Session session = getSession();
        try {
            return (Integer) session.save(object);
        } catch (Throwable e) {
            LOGGER.error("fail to save: {}", e);
            throw e;
        } finally {
            commit(session);    // ensure session is closed
        }
    }

    public static void batchSave(List<?> instances) {
        Session session = getSession();
        try {
            for (Object instance : instances) {
                try {
                    session.save(instance);
                } catch (Throwable e) {
                    LOGGER.error("fail to save: {}", e);
                    throw e;
                }
            }
        } finally {
            commit(session);    // ensure session is closed
        }
    }

    public static void saveOrUpdate(Object object) {
        Session session = getSession();
        try {
            session.saveOrUpdate(object);
        } catch (Throwable e) {
            LOGGER.error("fail to saveOrUpdate: {}", e);
            throw e;
        } finally {
            commit(session);    // ensure session is closed
        }
    }

    public static void update(Object object) {
        Session session = getSession();
        try {
            session.update(object);
        } catch (Throwable e) {
            LOGGER.error("fail to update: {}", e);
            throw e;
        } finally {
            commit(session);    // ensure session is closed
        }
    }

    public static void executeUpdate(String hql) {
        Session session = HibernateUtils.getSession();
        try {
            session.createQuery(hql).executeUpdate();
        } catch (Throwable e) {
            LOGGER.error("fail to execute update: {}", e);
            throw e;
        } finally {
            commit(session);  // ensure session is closed
        }
    }

    /**
     * delete data
     *
     * @param clazz       class of persist object
     * @param whereClause delete condition
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateDeleter#delete(Class)}
     */
    public static void delete(Class<?> clazz, String whereClause) {
        String hql = "DELETE FROM " + clazz.getSimpleName() + " " +
                adjustWhereClause(whereClause);
        executeUpdate(hql);
    }

    /**
     * delete all data of certain table
     *
     * @param clazz class of persist object
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateDeleter#deleteAll(Class)}
     */
    public static void deleteAll(Class<?> clazz) {
        delete(clazz, "");
    }

    /**
     * delete data by id
     *
     * @param clazz class of persist object
     * @param id    the id of persist object to delete
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateDeleter#deleteById(Class, Integer)}
     */
    public static void delete(Class<?> clazz, Integer id) {
        Session session = HibernateUtils.getSession();
        try {
            Object object = session.get(clazz, id);
            if (object != null) {
                session.delete(object);
            }
        } catch (Throwable e) {
            LOGGER.error("fail to delete: {}", e);
            throw e;
        } finally {
            commit(session);    // ensure session is closed
        }
    }

    private static String adjustWhereClause(String whereClause) {
        if (!StringUtils.hasText(whereClause)) {
            return "";
        }
        String lowerCaseString = whereClause.trim().toLowerCase();

        if (!(lowerCaseString.startsWith("where") || lowerCaseString.startsWith("order by"))) {
            return " WHERE " + whereClause;
        } else {
            return whereClause;
        }
    }

    public static <T> T get(Class<T> clazz, Integer id) {
        if (id == null) {
            return null;
        }

        Session session = HibernateUtils.getSession();
        @SuppressWarnings("unchecked")
        T object = (T) session.get(clazz, id);
        session.close();
        return object;
    }

    /**
     * @param clazz class of persist objects to get
     * @param <T>   type of persist object
     * @return list containing all instances of target persists object
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder#build(Class)}
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getList(Class<T> clazz) {
        return getList(clazz.getSimpleName());
    }

    /**
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder#build(Class)}
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getList(Class<T> clazz, String whereClause) {
        return getList(clazz.getSimpleName() + " " + adjustWhereClause(whereClause));
    }


    /**
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder#build(Class)}
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getList(Class<T> clazz, String whereClause, int firstResult, int maxResults) {
        return getList(clazz.getSimpleName() + " " + adjustWhereClause(whereClause), firstResult, maxResults);
    }

    /**
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder#build(Class)}
     */
    public static List getList(String hql) {
        return getList(hql, -1, -1);
    }

    /**
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder#getFirstItem(Class)}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFirstItem(Class<T> clazz, String whereClause) {
        return (T) getFirstItem("FROM " + clazz.getSimpleName() + " " + adjustWhereClause(whereClause));
    }

    /**
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder#getFirstItem(Class)}
     */
    public static <T> T getFirstItem(Class<T> clazz) {
        return getFirstItem(clazz, null);
    }

    /**
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder#getFirstItem(Class)}
     */
    public static Object getFirstItem(String hql) {
        List list = getList(hql, 0, 1);
        return list.size() > 0 ? list.get(0) : null;
    }

    /**
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder#build(Class)}
     */
    public static List getList(String hql, int firstResult, int maxResults) {
        Session session = getSession();
        try {
            hql = hql.trim();
            if (!hql.toLowerCase().startsWith("from") &&
                    !hql.toLowerCase().startsWith("select")) {
                hql = "FROM " + hql;
            }
            Query query = session.createQuery(hql);
            if (firstResult >= 0 && maxResults > 0) {
                query.setFirstResult(firstResult).setMaxResults(maxResults);
            }
            return query.list();
        } catch (Throwable e) {
            LOGGER.error("fail to get list: {}", e);
            throw e;
        } finally {
            commit(session);
            //session.close();    // ensure session is closed
        }
    }

    /**
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder#count(Class)}
     */
    public static long getCount(Class<?> clazz) {
        return getCount(clazz, "");
    }

    /**
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder#count(Class)}
     */
    public static long getCount(String className) {
        return getCount(className, "");
    }

    /**
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder#count(Class)}
     */
    public static long getCount(Class<?> clazz, String whereCondition) {
        return getCount(clazz.getSimpleName(), whereCondition);
    }

    /**
     * @deprecated use {@link com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder#count(Class)}
     */
    public static long getCount(String className, String whereCondition) {
        List list = getList("SELECT COUNT(*) FROM " + className + " " +
                adjustWhereClause(whereCondition));
        return (Long) list.get(0);
    }

    public static int getMaxId(Class<?> clazz) {
        Integer id = (Integer) new HibernateListBuilder().getFirstItem("SELECT MAX(id) FROM " + clazz.getSimpleName());
        return IntegerUtils.isPositive(id) ? id : 0;
    }
}
