package com.qinyuan.lib.mvc.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class PasswordValidator {
    public final static int MIN_LENGTH = 6;
    public final static int MAX_LENGTH = 20;

    public Pair<Boolean, String> validate(String password) {
        if (StringUtils.isBlank(password)) {
            return Pair.of(false, "密码不能为空！");
        } else if (password.length() < MIN_LENGTH) {
            return Pair.of(false, "密码至少需要" + MIN_LENGTH + "位字符！");
        } else if (password.length() > MAX_LENGTH) {
            return Pair.of(false, "密码不能超过" + MAX_LENGTH + "位字符！");
        } else if (password.contains(" ")) {
            return Pair.of(false, "密码不能包含空格！");
        } else {
            return Pair.of(true, null);
        }
    }
}
