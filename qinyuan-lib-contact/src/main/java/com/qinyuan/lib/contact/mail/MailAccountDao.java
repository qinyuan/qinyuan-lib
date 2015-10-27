package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import org.apache.commons.lang3.StringUtils;

public class MailAccountDao extends AbstractDao<MailAccount> {
    @Override
    protected HibernateListBuilder getListBuilder() {
        return super.getListBuilder().addOrder("type", false).addOrder("id", true);
    }

    public Integer add(Integer referenceId, String type) {
        MailAccount account = new MailAccount();
        account.setReferenceId(referenceId);
        account.setType(type);
        return HibernateUtils.save(account);
    }

    @Override
    public void delete(Integer id) {
        MailAccount account = getInstance(id);
        if (account != null) {
            AbstractDao<? extends RealMailAccount> referenceDao = getReferenceDao(account);
            if (referenceDao != null) {
                referenceDao.delete(account.getReferenceId());
            }
        }

        super.delete(id);
    }

    public RealMailAccount getReference(Integer id) {
        return getReference(getInstance(id));
    }

    public Integer getReferenceId(Integer id) {
        MailAccount account = getInstance(id);
        return account == null ? null : account.getReferenceId();
    }

    public RealMailAccount getReference(MailAccount account) {
        if (account == null) {
            return null;
        }
        AbstractDao<? extends RealMailAccount> referenceDao = getReferenceDao(account);
        return referenceDao == null ? null : referenceDao.getInstance(account.getReferenceId());
    }

    private AbstractDao<? extends RealMailAccount> getReferenceDao(MailAccount account) {
        if (account == null) {
            return null;
        }

        String type = account.getType();
        if (StringUtils.isBlank(type)) {
            return null;
        }

        if (type.equals(SimpleMailAccount.class.getSimpleName())) {
            return new SimpleMailAccountDao();
        } else if (type.equals(SendCloudAccount.class.getSimpleName())) {
            return new SendCloudAccountDao();
        } else {
            return null;
        }
    }

    public String getUsername(MailAccount account) {
        RealMailAccount realMailAccount = getReference(account);
        if (realMailAccount == null) {
            return null;
        } else {
            return realMailAccount.getUsername();
        }
    }
}
