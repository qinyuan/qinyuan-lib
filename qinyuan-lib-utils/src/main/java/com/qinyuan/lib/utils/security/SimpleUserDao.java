package com.qinyuan.lib.utils.security;

import com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder;
import org.springframework.util.StringUtils;

/**
 * A simple dao to handle user
 * Created by qinyuan on 15-6-14.
 */
public class SimpleUserDao implements IUserDao {
    @Override
    public User getInstanceByName(String username) {
        if (!StringUtils.hasText(username)) {
            return null;
        }

        return new HibernateListBuilder()
                .addFilter("username=:username").addArgument("username", username)
                .getFirstItem(User.class);
    }

    @Override
    public Integer getIdByName(String username) {
        if (!StringUtils.hasText(username)) {
            return null;
        }

        User user = this.getInstanceByName(username);
        return user == null ? null : user.getId();
    }
}
