package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import org.apache.commons.lang3.StringUtils;

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

    public RealMailAccount getReference(MailAccount account) {
        String type = account.getType();
        if (StringUtils.isBlank(type)) {
            throw new RuntimeException("blank type: " + type);
        }

        if (type.equals(SimpleMailAccount.class.getSimpleName())) {
            return new SimpleMailAccountDao().getInstance(account.getReferenceId());
        } else if (type.equals(SendCloudAccount.class.getSimpleName())) {
            return new SendCloudAccountDao().getInstance(account.getReferenceId());
        } else {
            throw new RuntimeException("unrecognized type: " + type);
        }
    }
}
