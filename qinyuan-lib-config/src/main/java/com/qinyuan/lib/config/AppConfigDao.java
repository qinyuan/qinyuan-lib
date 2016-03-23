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
        return booleanString == null ? null : Boolean.parseBoolean(booleanString);
    }

    public static void main(String[] args) {
        System.out.println(NumberUtils.isNumber(null));
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
        return NumberUtils.isNumber(value) ? Integer.parseInt(value) : null;
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
        return NumberUtils.isNumber(value) ? Double.parseDouble(value) : null;
    }

    public void saveDouble(String name, Double value) {
        if (value == null) {
            save(name, null);
        } else {
            save(name, String.valueOf(value));
        }
    }
}
