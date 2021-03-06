package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.hibernate.PersistObject;

public class SimpleMailAccount extends PersistObject implements RealMailAccount {
    /**
     * host, such as smtp.sina.com
     */
    private String host;

    /**
     * username, such as test12345@sina.com
     */
    private String username;

    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
