package com.qinyuan.lib.mvc.tag;

import javax.servlet.jsp.JspException;

public class Hidden extends CssJsBase {

    @Override
    public int doEndTag() throws JspException {
        print("<input type=\"hidden\" ");

        printIdIfNotBlank();
        printNameIfNotBlank();
        printCssClassIfNotBlank();
        printValue();

        print("/>");

        return EVAL_PAGE;
    }
}
