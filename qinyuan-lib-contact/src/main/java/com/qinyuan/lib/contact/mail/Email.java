package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.hibernate.PersistObject;

public class Email extends PersistObject {
    private String subject;
    private String content;

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
