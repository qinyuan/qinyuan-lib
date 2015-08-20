package com.qinyuan.lib.mvc.controller;

/**
 * Tool class about pagination
 * Created by qinyuan on 15-4-5.
 */
public class PaginationUtils {
    private PaginationUtils() {
    }

    public static int getPageCount(long itemCount, int pageSize) {
        return itemCount == 0 ? 1 : (int) (Math.ceil((double) itemCount / pageSize));
    }
}
