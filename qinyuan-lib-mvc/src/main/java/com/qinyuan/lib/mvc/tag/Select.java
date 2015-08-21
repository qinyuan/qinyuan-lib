package com.qinyuan.lib.mvc.tag;

import org.apache.commons.lang3.StringUtils;

public class Select extends MyTagSupport {
    protected String value;
    protected String name;

    public void setValue(String value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected void printName() {
        if (StringUtils.isNotBlank(name)) {
            print(" name=\"" + name + "\"");
        }
    }

    protected void printOption(String optionValue) {
        printOption(optionValue, value);
    }

    protected void printOption(String optionValue, String defaultValue) {
        print("<option value=\"" + optionValue + "\"");
        if (optionValue.equals(defaultValue)) {
            print(" selected");
        }
        print(">" + optionValue + "</option>");
    }

    protected void printOption(int optionValue, int defaultValue) {
        printOption(String.valueOf(optionValue), String.valueOf(defaultValue));
    }
}
