package com.qinyuan.lib.mvc.tag;

import javax.servlet.jsp.JspException;

/**
 * Tag class of multipart-form
 * Created by qinyuan on 15-6-16.
 */
public class MultipartForm extends MyTagSupport {
    private String action;

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public int doStartTag() throws JspException {
        print("<form method=\"post\" ");
        printId();
        printCssClass();
        print("action=\"");
        print(action);
        print("\" enctype=\"multipart/form-data\">");
        return EVAL_PAGE;
    }

    @Override
    public int doEndTag() throws JspException {
        print("</form>");
        return EVAL_PAGE;
    }
}
