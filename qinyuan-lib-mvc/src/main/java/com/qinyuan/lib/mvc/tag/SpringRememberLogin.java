package com.qinyuan.lib.mvc.tag;

import javax.servlet.jsp.JspException;

/**
 * Class of tag to remember login in Spring MVC
 * Created by qinyuan on 15-6-26.
 */
public class SpringRememberLogin extends MyTagSupport {
    @Override
    public int doEndTag() throws JspException {
        print("<input type=\"checkbox\" name=\"_spring_security_remember_me\"");
        printId();
        print("/> ");
        return EVAL_PAGE;
    }
}
