package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateUtils;

public class MailAccountDao extends AbstractDao<MailAccount> {
    public Integer add(Integer referenceId, String type) {
        MailAccount account = new MailAccount();
        account.setReferenceId(referenceId);
        account.setType(type);
        return HibernateUtils.save(account);
    }

    public void update(Integer id, Integer referenceId, String type) {
        MailAccount account = getInstance(id);
        if (account != null) {
            account.setReferenceId(referenceId);
            account.setType(type);
            HibernateUtils.update(account);
        }
    }
}
