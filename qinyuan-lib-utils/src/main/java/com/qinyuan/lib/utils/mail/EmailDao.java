package com.qinyuan.lib.utils.mail;

import com.qinyuan.lib.utils.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.utils.database.hibernate.HibernateUtils;

import java.util.List;

public class EmailDao {
    public List<Email> getInstances() {
        return new HibernateListBuilder().build(Email.class);
    }

    public Email getInstance(Integer id) {
        return HibernateUtils.get(Email.class, id);
    }

    public Integer add(String subject, String content) {
        Email email = new Email();
        email.setSubject(subject);
        email.setContent(content);
        return HibernateUtils.save(email);
    }

    public void update(Integer id, String subject, String content) {
        Email email = getInstance(id);
        if (email != null) {
            email.setSubject(subject);
            email.setContent(content);
            HibernateUtils.update(email);
        }
    }
}
