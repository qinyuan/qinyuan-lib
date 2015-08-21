package com.qinyuan.lib.mvc.tag;

import org.springframework.util.StringUtils;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * Class to extends TagSupport
 * Created by qinyuan on 15-6-16.
 */
class MyTagSupport extends TagSupport {
    protected void print(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            this.pageContext.getOut().print(obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void printId() {
        String id = this.getId();
        if (StringUtils.hasText(id)) {
            print(" id=\"");
            print(id);
            print("\" ");
        }
    }
}
