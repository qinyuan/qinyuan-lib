package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;

public class MailAccountDao extends AbstractDao<MailAccount> {
    public Integer add(String host, String username, String password) {
        MailAccount account = new MailAccount();
        account.setHost(host);
        account.setUsername(username);
        account.setPassword(password);
        return HibernateUtils.save(account);
    }

    public void update(Integer id, String host, String password) {
        MailAccount account = getInstance(id);
        if (account != null) {
            account.setHost(host);
            account.setPassword(password);
            HibernateUtils.update(account);
        }
    }

    public boolean hasUsername(String username) {
        return new HibernateListBuilder().addEqualFilter("username", username).count(MailAccount.class) > 0;
    }
}
