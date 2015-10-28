package com.qinyuan.lib.mvc.security;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import org.apache.commons.lang3.StringUtils;

/**
 * A simple dao to handle user
 * Created by qinyuan on 15-6-14.
 */
public class SimpleUserDao implements IUserDao {
    private boolean ignoreCase = true;

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    @Override
    public User getInstanceByName(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }

        HibernateListBuilder listBuilder = new HibernateListBuilder();
        if (ignoreCase) {
        //listBuilder.addFilter("LOWER(email")
        } else {
            listBuilder.addEqualFilter("username", username);
        }
        return listBuilder.getFirstItem(User.class);
        /*return new HibernateListBuilder().addEqualFilter("username", username)
                .getFirstItem(User.class);*/
    }

    @Override
    public Integer getIdByName(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }

        User user = this.getInstanceByName(username);
        return user == null ? null : user.getId();
    }
}
