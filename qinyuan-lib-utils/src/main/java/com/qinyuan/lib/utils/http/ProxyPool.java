package com.qinyuan.lib.utils.http;

import java.util.List;

/**
 * Pool of Http Proxy
 * Created by qinyuan on 14-12-24.
 */
public interface ProxyPool {

    IProxy next();

    List<IProxy> next(int n);

    int size();
}
