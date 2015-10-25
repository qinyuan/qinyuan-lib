package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.hibernate.PersistObject;

public class SendCloudAccount extends PersistObject {
    /**
     * user, such as qinyuan_test_KhSmWe
     */
    private String user;

    /**
     * api key, such as IjGx8tQGs8CbwgAh
     */
    private String apiKey;

    /**
     * domain name
     */
    private String domainName;

    public String getUser() {
        return user;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
