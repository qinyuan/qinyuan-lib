package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.hibernate.HibernateDeleter;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;

import java.util.List;

public class MailAccountDao {
    public MailAccount getInstance(Integer id) {
        return HibernateUtils.get(MailAccount.class, id);
    }

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

    public void delete(Integer id) {
        HibernateDeleter.deleteById(MailAccount.class, id);
    }

    public List<MailAccount> getInstances() {
        return new HibernateListBuilder().build(MailAccount.class);
    }

    public boolean hasUsername(String username) {
        return new HibernateListBuilder().addEqualFilter("username", username).count(MailAccount.class) > 0;
    }
}
