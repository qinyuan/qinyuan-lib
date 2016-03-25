package com.qinyuan.lib.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

public class MapUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(MapUtils.class);

    public static <T> T getFirstKey(Map<T, ?> map) {
        if (map == null || map.isEmpty()) {
            LOGGER.warn("invalid map: {}", map);
            return null;
        }

        Iterator<T> iterator = map.keySet().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

    public static void removeFirst(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            LOGGER.warn("invalid map: {}", map);
            return;
        }

        removeQuietly(map, getFirstKey(map));
    }

    public static void removeQuietly(Map<?, ?> map, Object key) {
        if (map == null || map.isEmpty()) {
            LOGGER.warn("invalid map: {}", map);
            return;
        }

        try {
            map.remove(key);
        } catch (Exception e) {
            LOGGER.warn("error in removing map element: {}", e);
        }
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }
}
