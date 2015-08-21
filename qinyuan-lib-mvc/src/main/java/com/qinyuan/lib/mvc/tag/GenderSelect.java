package com.qinyuan.lib.mvc.tag;

import com.google.common.collect.Lists;

import javax.servlet.jsp.JspException;

public class GenderSelect extends Select {
    private String emptyText = "";

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }

    @Override
    public int doEndTag() throws JspException {
        print("<select");
        printId();
        printName();
        print(">");

        for (String string : Lists.newArrayList(emptyText, "男", "女")) {
            printOption(string);
        }

        print("</select>");
        return EVAL_PAGE;
    }
}
