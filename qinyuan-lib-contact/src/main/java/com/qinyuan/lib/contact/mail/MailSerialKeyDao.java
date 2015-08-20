package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.DateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;

public class MailSerialKeyDao {
    private final static int SERIAL_KEY_LENGTH = 100;

    private final String mailType;
    private final int expireSeconds;

    public MailSerialKeyDao(String mailType, int expireSeconds) {
        if (StringUtils.isBlank(mailType)) {
            throw new IllegalArgumentException("mailType can't be empty, real mailType: " + mailType);
        }

        if (expireSeconds <= 0) {
            throw new IllegalArgumentException("expireSeconds must be positive, real expireSeconds: " + expireSeconds);
        }

        this.mailType = mailType;
        this.expireSeconds = expireSeconds;
    }

    public Integer add(Integer userId) {
        return add(userId, "");
    }

    public Integer add(Integer userId, String serialKeyPrefix) {
        MailSerialKey mailSerialKey = new MailSerialKey();
        mailSerialKey.setUserId(userId);

        do {
            mailSerialKey.setSerialKey(serialKeyPrefix + RandomStringUtils.randomAlphanumeric(SERIAL_KEY_LENGTH));
        } while (getInstanceBySerialKey(mailSerialKey.getSerialKey()) != null);

        mailSerialKey.setSendTime(DateUtils.nowString());
        mailSerialKey.setMailType(mailType);
        return HibernateUtils.save(mailSerialKey);
    }

    public MailSerialKey getInstance(Integer id) {
        final MailSerialKey mailSerialKey = HibernateUtils.get(MailSerialKey.class, id);

        if (mailSerialKey == null) {
            return null;
        }

        // validate mailType
        if (mailSerialKey.getMailType() == null || (!mailType.equals(mailSerialKey.getMailType()))) {
            return null;
        }

        // validate send time
        if (StringUtils.isBlank(mailSerialKey.getSendTime())) {
            return null;
        }
        Date sendTime = DateUtils.newDate(DateUtils.trimMilliSecond(mailSerialKey.getSendTime()));
        if (DateUtils.getSecondDiff(sendTime, DateUtils.now()) > expireSeconds) {
            return null;
        }

        return mailSerialKey;
    }

    public MailSerialKey getInstanceByUserId(Integer userId) {
        return getListBuilder().addEqualFilter("userId", userId).getFirstItem(MailSerialKey.class);
    }

    public MailSerialKey getInstanceBySerialKey(String serialKey) {
        return getListBuilder().addEqualFilter("serialKey", serialKey).getFirstItem(MailSerialKey.class);
    }

    public boolean hasSerialKey(String serialKey) {
        return getListBuilder().addEqualFilter("serialKey", serialKey).count(MailSerialKey.class) > 0;
    }

    public void response(Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return;
        }

        String hql = "UPDATE MailSerialKey SET responseTime='" + DateUtils.nowString() + "' WHERE id=" + id;
        HibernateUtils.executeUpdate(hql);
    }

    private HibernateListBuilder getListBuilder() {
        Date earliestValidTime = new Date(System.currentTimeMillis() - expireSeconds * 1000);
        return new HibernateListBuilder().addEqualFilter("mailType", mailType).addFilter("sendTime>=:sendTime")
                .addArgument("sendTime", DateUtils.toLongString(earliestValidTime))
                .addOrder("id", false);
    }
}
