package com.qinyuan.lib.contact.qq;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QQValidator {
    private final static Logger LOGGER = LoggerFactory.getLogger(QQValidator.class);

    public boolean validate(String qq) {
        if (StringUtils.isBlank(qq)) {
            LOGGER.error("qq is blank: {}", qq);
            return false;
        }

        try {
            return qq.matches("^\\d+$");
        } catch (Exception e) {
            LOGGER.error("fail to validate qq '{}', info: {}", qq, e);
            return false;
        }
    }
}
