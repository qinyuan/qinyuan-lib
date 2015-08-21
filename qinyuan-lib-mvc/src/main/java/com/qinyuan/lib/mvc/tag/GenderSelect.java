package com.qinyuan.lib.mvc.tag;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;

/**
 * Class of tag to remember login in Spring MVC
 * Created by qinyuan on 15-6-26.
 */
public class GenderSelect extends MyTagSupport {
    private String value;
    private String emptyText = "";
    private String name;

    public void setValue(String value) {
        this.value = value;
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int doEndTag() throws JspException {
        print("<select");
        if (StringUtils.isNotBlank(this.getId())) {
            print(" id=\"" + this.getId() + "\"");
        }

        if (StringUtils.isNotBlank(name)) {
            print(" name=\"" + name + "\"");
        }
        print(">");
        for (String string : Lists.newArrayList(emptyText, "男", "女")) {
            print("<option value=\"" + string + "\"");
            if (string.equals(value)) {
                print(" selected");
            }
            print(">" + string + "</option>");
        }
        print("</select>");
        return EVAL_PAGE;
    }
}
