package com.qinyuan.lib.mvc.security;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUpdater;
import com.qinyuan.lib.lang.IntegerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractUserDao<T extends User> extends AbstractDao<T> implements IUserDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractUserDao.class);

    public boolean hasUsername(String username) {
        return new HibernateListBuilder().addEqualFilter("username", username).count(getPersistClass()) > 0;
    }

    public void updatePassword(Integer id, String password) {
        if (!IntegerUtils.isPositive(id)) {
            LOGGER.warn("id '{}' is not positive, give up updating password of user", id);
        }
        new HibernateUpdater().addEqualFilter("id", id).addArgument("password", password)
                .update(getPersistClass(), "password=:password");
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getInstanceByName(String username) {
        return (T) new HibernateListBuilder().addEqualFilter("username", username)
                .getFirstItem(getPersistClass());
    }

    @Override
    public Integer getIdByName(String username) {
        return (Integer) new HibernateListBuilder().addEqualFilter("username", username)
                .getFirstItem("SELECT id FROM " + getPersistClass().getSimpleName());
    }
}
