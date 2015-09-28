package com.qinyuan.lib.mvc.tag;

import com.qinyuan.lib.lang.time.DateUtils;

import javax.servlet.jsp.JspException;

public class DateSelect extends Select {
    private String prefix = "";
    private int startYear = 1900;
    private int endYear = 2100;

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    private void printNameOrId(String nameOrId) {
        String fullNameOrId = prefix == null ? nameOrId : prefix + nameOrId;
        if (fullNameOrId.length() == 0) {
            return;
        } else if (nameOrId.length() == 1) {
            fullNameOrId = fullNameOrId.toLowerCase();
        } else {
            fullNameOrId = Character.toLowerCase(fullNameOrId.charAt(0)) + fullNameOrId.substring(1);
        }

        print(" id=\"" + fullNameOrId + "\" name=\"" + fullNameOrId + "\"");
    }

    @Override
    public int doEndTag() throws JspException {
        DateElementHolder dateElementHolder = new DateElementHolder(getValue());

        // print year select
        print("<select");
        printNameOrId("Year");
        print(">");
        if (dateElementHolder.year < startYear || dateElementHolder.year > endYear) {
            printOption("", "");
        }
        for (int i = endYear; i >= startYear; i--) {
            printOption(i, dateElementHolder.year);
        }
        print("</select>年");

        // print month select
        print("<select");
        printNameOrId("Month");
        print(">");
        if (dateElementHolder.month < 1 || dateElementHolder.month > 12) {
            printOption("", "");
        }
        for (int i = 1; i <= 12; i++) {
            printOption(i, dateElementHolder.month);
        }
        print("</select>月");

        // print day select
        print("<select");
        printNameOrId("Day");
        print(">");
        if (dateElementHolder.day < 1 || dateElementHolder.day > 31) {
            printOption("", "");
        }
        for (int i = 1; i <= 31; i++) {
            printOption(i, dateElementHolder.day);
        }
        print("</select>日");

        return EVAL_PAGE;
    }

    private class DateElementHolder {
        int year = 0;
        int month = 0;
        int day = 0;

        private DateElementHolder(String date) {
            if (DateUtils.isDate(date)) {
                String[] dateArray = date.split("\\D");
                if (dateArray.length == 3) {
                    year = Integer.parseInt(dateArray[0]);
                    month = Integer.parseInt(dateArray[1]);
                    day = Integer.parseInt(dateArray[2]);
                }
            }
        }
    }
}
