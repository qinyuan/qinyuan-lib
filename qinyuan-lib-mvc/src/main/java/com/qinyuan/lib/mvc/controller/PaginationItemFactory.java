package com.qinyuan.lib.mvc.controller;

import java.util.List;

/**
 * Factory to provide pagination items
 * Created by qinyuan on 15-4-5.
 */
public interface PaginationItemFactory<T> {
    int getCount();

    List<T> getInstances(int firstResult, int maxResults);
}
