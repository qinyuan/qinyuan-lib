package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateUtils;

public class SendCloudAccountDao extends AbstractDao<SendCloudAccount> {
    public Integer add(String user, String apiKey, String domainName) {
        SendCloudAccount account = new SendCloudAccount();
        account.setUser(user);
        account.setApiKey(apiKey);
        account.setDomainName(domainName);
        Integer id = HibernateUtils.save(account);
        new MailAccountDao().add(id, getPersistClass().getSimpleName());
        return id;
    }

    public void update(Integer id, String user, String apiKey, String domainName) {
        SendCloudAccount account = getInstance(id);
        if (account != null) {
            account.setUser(user);
            account.setApiKey(apiKey);
            account.setDomainName(domainName);
            HibernateUtils.update(account);
        }
    }
}
