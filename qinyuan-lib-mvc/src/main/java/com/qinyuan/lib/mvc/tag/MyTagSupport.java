package com.qinyuan.lib.mvc.tag;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * Class to extends TagSupport
 * Created by qinyuan on 15-6-16.
 */
class MyTagSupport extends TagSupport {
    private String cssClass;
    private String name;
    private String value;

    public String getCssClass() {
        return cssClass;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getEscapedValue() {
        return StringEscapeUtils.escapeHtml4(getValue());
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

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

    protected void printAttributeIfNotBlank(String key, String value) {
        if (StringUtils.hasText(value)) {
            printAttribute(key, value);
        }
    }

    protected void printAttribute(String key, String value) {
        print(" " + key + "=\"" + value + "\" ");
    }

    protected void printIdIfNotBlank() {
        printAttributeIfNotBlank("id", getId());
    }

    protected void printNameIfNotBlank() {
        printAttributeIfNotBlank("name", getName());
    }

    protected void printCssClassIfNotBlank() {
        printAttributeIfNotBlank("class", getCssClass());
    }

    protected void printValueIfNotBlank() {
        printAttributeIfNotBlank("value", getEscapedValue());
    }

    protected void printValue() {
        printAttributeIfNotBlank("value", getEscapedValue());
    }
}
