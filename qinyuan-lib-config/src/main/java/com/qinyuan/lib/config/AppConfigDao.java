package com.qinyuan.lib.config;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Dao of AppConfig
 * Created by qinyuan on 15-6-15.
 */
public class AppConfigDao {
    public void save(String name, String value) {
        AppConfig appConfig = getInstanceByName(name);
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.setPropertyName(name);
            appConfig.setPropertyValue(value);
            HibernateUtils.save(appConfig);
        } else {
            appConfig.setPropertyValue(value);
            HibernateUtils.update(appConfig);
        }
    }

    private AppConfig getInstanceByName(String name) {
        return new HibernateListBuilder().addFilter("propertyName=:propertyName")
                .addArgument("propertyName", name).getFirstItem(AppConfig.class);
    }

    public String get(String name) {
        AppConfig appConfig = getInstanceByName(name);
        return appConfig == null ? null : appConfig.getPropertyValue();
    }

    public Boolean getBoolean(String name) {
        String booleanString = get(name);
        if (booleanString == null) {
            return null;
        } else {
            return Boolean.parseBoolean(booleanString);
        }
    }

    public void saveBoolean(String name, Boolean value) {
        if (value == null) {
            save(name, null);
        } else if (value) {
            save(name, "true");
        } else {
            save(name, "false");
        }
    }

    public Integer getInteger(String name) {
        String value = get(name);
        if (value != null && NumberUtils.isNumber(value)) {
            return Integer.parseInt(value);
        } else {
            return null;
        }
    }

    public Integer getPositiveInteger(String name) {
        Integer integer = getInteger(name);
        return IntegerUtils.isPositive(integer) ? integer : null;
    }

    public void saveInteger(String name, Integer value) {
        if (value == null) {
            save(name, null);
        } else {
            save(name, String.valueOf(value));
        }
    }

    public Double getDouble(String name) {
        String value = get(name);
        if (value != null && NumberUtils.isNumber(value)) {
            return Double.parseDouble(value);
        } else {
            return null;
        }
    }

    public void saveDouble(String name, Integer value) {
        if (value == null) {
            save(name, null);
        } else {
            save(name, String.valueOf(value));
        }
    }
}
