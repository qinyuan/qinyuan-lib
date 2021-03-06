package com.qinyuan.lib.mvc.tag;

import javax.servlet.jsp.JspException;

/**
 * Tag class of js
 * Created by qinyuan on 15-6-14.
 */
public class Js extends CssJsBase {
    private String src;

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public int doEndTag() throws JspException {
        print("<script src=\"");
        if (!src.startsWith(PREFIX) && !src.startsWith("http://")) {
            print(JS_PREFIX);
        }
        print(src);

        if (!src.endsWith(".js")) {
            print(".js");
        }

        printVersion();
        print("\"></script>");
        return EVAL_PAGE;
    }
}
