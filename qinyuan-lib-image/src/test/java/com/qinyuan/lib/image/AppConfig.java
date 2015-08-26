package com.qinyuan.lib.image;

import com.qinyuan.lib.database.hibernate.PersistObject;

public class AppConfig extends PersistObject {
    private String propertyName;
    private String propertyValue;

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}
