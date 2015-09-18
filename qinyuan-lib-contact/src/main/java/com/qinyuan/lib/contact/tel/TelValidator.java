package com.qinyuan.lib.contact.tel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Class to validate telephone number
 * Created by qinyuan on 15-7-31.
 */
public class TelValidator {
    /**
     * Validate certain tel
     *
     * @param tel tel to validate
     * @return a pair, key represents if tel is valid, value represents invalid information if invalid
     */
    public Pair<Boolean, String> validate(String tel) {
        if (StringUtils.isBlank(tel)) {
            return Pair.of(false, "电话号码不能为空");
        } else if (!tel.matches("^\\d{11}$")) {
            return Pair.of(false, "电话号码必须为11位数字");
        } else {
            return Pair.of(true, null);
        }
    }
}
