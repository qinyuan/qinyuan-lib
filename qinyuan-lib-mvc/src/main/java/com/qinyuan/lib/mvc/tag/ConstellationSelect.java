package com.qinyuan.lib.mvc.tag;

import javax.servlet.jsp.JspException;
import java.util.Arrays;

public class ConstellationSelect extends Select {
    private final static String[] constellations = {
            "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座",
            "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"
    };

    @Override
    public int doEndTag() throws JspException {
        print("<select ");
        printId();
        printName();
        print(">");

        if (Arrays.binarySearch(constellations, value) < 0) {
            printOption("", "");
        }
        for (String constellation : constellations) {
            printOption(constellation);
        }

        print("</select>");

        return EVAL_PAGE;
    }
}
