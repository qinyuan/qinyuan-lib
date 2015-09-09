package com.qinyuan.lib.mvc.tag;

public class Select extends MyTagSupport {
    protected void printOption(String optionValue) {
        printOption(optionValue, getValue());
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
