package com.qinyuan.lib.mvc.controller;

import java.util.HashMap;
import java.util.Map;

public class JsonResult {
    public final static String SUCCESS = "success";
    public final static String DETAIL = "detail";

    public static Map<String, Object> build(boolean success, Object detail) {
        Map<String, Object> map = new HashMap<>();
        map.put(SUCCESS, success);
        map.put(DETAIL, detail);
        return map;
    }
}
