package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.hibernate.PersistObject;

public class MailAccount extends PersistObject {
    private Integer referenceId;
    private String type;

    public Integer getReferenceId() {
        return referenceId;
    }

    public String getType() {
        return type;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public void setType(String type) {
        this.type = type;
    }


    /////////////////////////// derive fields ///////////////////////////////
    public String getUsername() {
        return new MailAccountDao().getUsername(this);
    }
}
