package com.qinyuan.lib.contact.tel;

import org.apache.commons.lang3.StringUtils;

/**
 * Class to validate telephone number
 * Created by qinyuan on 15-7-31.
 */
public class TelValidator {
    public boolean validate(String tel) {
        return StringUtils.isNotBlank(tel) && tel.matches("^\\d{11}$");
    }
}
