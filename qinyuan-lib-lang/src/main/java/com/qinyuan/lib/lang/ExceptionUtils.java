package com.qinyuan.lib.lang;

public class ExceptionUtils {
    public static String getMessage(Throwable e) {
        if (e == null) {
            return null;
        }

        while (e instanceof RuntimeException && e.getCause() != null) {
            e = e.getCause();
        }
        return e.getMessage();
    }
}
