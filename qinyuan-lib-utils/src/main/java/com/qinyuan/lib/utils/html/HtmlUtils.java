package com.qinyuan.lib.utils.html;

public class HtmlUtils {
    public static String toText(String htmlContent) {
        return htmlContent == null ? null : htmlContent.replaceAll("<[^>]+>", "");
    }
}
