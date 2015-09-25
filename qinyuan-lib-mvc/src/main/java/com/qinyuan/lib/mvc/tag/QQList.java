package com.qinyuan.lib.mvc.tag;

import javax.servlet.jsp.JspException;

/**
 * Tag about qqlist
 * Created by qinyuan on 15-9-25.
 */
public class QQList extends MyTagSupport {
    private final static String ACTION = "http://list.qq.com/cgi-bin/qf_compose_send";
    private boolean showInput;
    private String nId;

    public void setShowInput(boolean showInput) {
        this.showInput = showInput;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    @Override
    public int doStartTag() throws JspException {
        print("<form ");
        printIdIfNotBlank();
        printCssClassIfNotBlank();
        print(" action=\"" + ACTION + "\" target=\"_blank\" method=\"post\">");

        print("<input type=\"hidden\" name=\"t\" value=\"qf_booked_feedback\">");
        print("<input type=\"hidden\" name=\"id\" value=\"" + nId + "\">");
        if (showInput) {
            print("<input type=\"text\" name=\"to\" class=\"form-control\">");
        } else {
            print("<input type=\"hidden\" name=\"to\">");
        }

        return EVAL_PAGE;
    }

    @Override
    public int doEndTag() throws JspException {
        print("</form>");
        return EVAL_PAGE;
    }
}
