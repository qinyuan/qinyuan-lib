package com.qinyuan.lib.database.hibernate;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.lang.file.ClasspathFileUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Util class for Hibernate
 * Created by qinyuan on 14-12-26.
 */
public class HibernateUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(HibernateUtils.class);

    public final static String CONFIG_FILE = "hibernate.cfg.xml";
    public final static String TEST_CONFIG_FILE = "hibernate.test.cfg.xml";
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
        if (ClasspathFileUtils.isFile(TEST_CONFIG_FILE)) {
            return new Configuration().configure(TEST_CONFIG_FILE); // load test config file first
        } else {
            return new Configuration().configure(CONFIG_FILE);
        }
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

    public static void executeSQLUpdate(String... sqls) {
        Session session = HibernateUtils.getSession();
        try {
            for (String sql : sqls) {
                session.createSQLQuery(sql).executeUpdate();
            }
        } catch (Throwable e) {
            LOGGER.error("fail to execute update: {}", e);
            throw e;
        } finally {
            commit(session);  // ensure session is closed
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

    public static int getMaxId(Class<?> clazz) {
        Integer id = (Integer) new HibernateListBuilder().getFirstItem("SELECT MAX(id) FROM " + clazz.getSimpleName());
        return IntegerUtils.isPositive(id) ? id : 0;
    }
}
