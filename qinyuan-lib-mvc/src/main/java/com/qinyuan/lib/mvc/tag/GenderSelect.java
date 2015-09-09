package com.qinyuan.lib.mvc.tag;

import com.google.common.collect.Lists;

import javax.servlet.jsp.JspException;
import java.util.List;

public class GenderSelect extends Select {
    private String emptyText = "";

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }

    @Override
    public int doEndTag() throws JspException {
        print("<select");
        printIdIfNotBlank();
        printNameIfNotBlank();
        print(">");

        List<String> options = Lists.newArrayList("男", "女");
        if (!options.contains(getValue())) {
            options.add(0, emptyText);
        }
        for (String string : options) {
            printOption(string);
        }

        print("</select>");
        return EVAL_PAGE;
    }
}
