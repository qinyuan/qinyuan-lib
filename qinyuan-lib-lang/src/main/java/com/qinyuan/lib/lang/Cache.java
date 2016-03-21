package com.qinyuan.lib.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    private final static Logger LOGGER = LoggerFactory.getLogger(Cache.class);

    public final static int DEFAULT_SIZE = 100000;
    private int size = DEFAULT_SIZE;
    private Map<String, Object> map = new HashMap<>();

    public void setSize(int size) {
        this.size = size;
    }

    public Object getValue(String key) {
        return getValue(key, null);
    }

    public Object getValue(String key, Source source) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            if (source == null) {
                return null;
            }

            Object value = source.getValue();
            LOGGER.debug("add ({}, {}) to cache", key, value);
            addValue(key, value);
            return value;
        }
    }

    public int getUsedSize() {
        return map.size();
    }

    private void addValue(String key, Object value) {
        while (map.size() >= size) {
            MapUtils.removeFirst(map);
        }
        map.put(key, value);
    }

    public void deleteValue(String key) {
        MapUtils.removeQuietly(map, key);
    }

    public interface Source {
        Object getValue();
    }
}
