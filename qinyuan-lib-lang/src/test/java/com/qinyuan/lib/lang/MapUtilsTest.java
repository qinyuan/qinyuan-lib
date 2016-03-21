package com.qinyuan.lib.lang;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class MapUtilsTest {
    @Test
    public void testGetFirstKey() throws Exception {
        Map<String, String> map = new TreeMap<>();
        map.put("key2", "value2");
        map.put("key1", "value1");
        map.put("key3", "value3");

        String key = MapUtils.getFirstKey(map);
        assertThat(map.containsKey(key));
        assertThat(key).isEqualTo("key1");

        assertThat(MapUtils.getFirstKey(new HashMap<>())).isNull();
        assertThat(MapUtils.getFirstKey(null)).isNull();
    }

    @Test
    public void testRemoveFirst() {
        Map<String, String> map = new TreeMap<>();
        MapUtils.removeFirst(map);
        assertThat(map).isEmpty();

        map.put("key2", "value2");
        map.put("key1", "value1");
        map.put("key3", "value3");

        MapUtils.removeFirst(map);
        assertThat(map).doesNotContainKey("key1");
        assertThat(map).hasSize(2);

        MapUtils.removeFirst(map);
        assertThat(map).doesNotContainKey("key2");
        assertThat(map).hasSize(1);

        MapUtils.removeFirst(map);
        assertThat(map).doesNotContainKey("key3");
        assertThat(map).isEmpty();
    }
}
