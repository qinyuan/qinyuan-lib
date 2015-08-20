package com.qinyuan.lib.network.html;

public class HtmlUtils {
    public static String toText(String htmlContent) {
        return htmlContent == null ? null : htmlContent.replaceAll("<[^>]+>", "");
    }
}
