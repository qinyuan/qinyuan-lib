package com.qinyuan.lib.lang;

import org.apache.commons.lang3.StringUtils;

public class SecrecyUtils {
    public static String makeTelSecret(String tel) {
        if (tel == null) {
            return null;
        }

        tel = tel.trim();

        if (tel.length() < 6) {
            return null;
        }

        return StringUtils.left(tel, 2) + "**" + StringUtils.right(tel, 4);
    }

    public static String makeEmailSecret(String email) {
        if (email == null) {
            return null;
        }

        email = email.trim();

        String[] strings = email.split("@");
        if (strings.length != 2) {
            return null;
        }

        return StringUtils.left(strings[0], 2) + "**" + "@" + strings[1];
    }
}
