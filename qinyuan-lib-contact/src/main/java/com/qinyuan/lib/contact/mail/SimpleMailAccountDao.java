package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;

public class SimpleMailAccountDao extends AbstractDao<SimpleMailAccount> {
    public Integer add(String host, String username, String password) {
        SimpleMailAccount account = new SimpleMailAccount();
        account.setHost(host);
        account.setUsername(username);
        account.setPassword(password);
        return HibernateUtils.save(account);
    }

    public void update(Integer id, String host, String password) {
        SimpleMailAccount account = getInstance(id);
        if (account != null) {
            account.setHost(host);
            account.setPassword(password);
            HibernateUtils.update(account);
        }
    }

    public boolean hasUsername(String username) {
        return new HibernateListBuilder().addEqualFilter("username", username).count(SimpleMailAccount.class) > 0;
    }
}
