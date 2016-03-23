package com.qinyuan.lib.lang;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheTest {
    private Cache cache;

    @Before
    public void setUp() {
        cache = new Cache();
    }

    @Test
    public void testGetValue() throws Exception {
        Object value;
        for (int i = 0; i < 10; i++) {
            value = cache.getValue("hello", new Cache.Source() {
                @Override
                public Object getValue() {
                    return "world";
                }
            });
            assertThat(value).isEqualTo("world");
            assertThat(cache.getUsedSize()).isEqualTo(1);
        }

        assertThat(cache.getValue("hello")).isEqualTo("world");
        assertThat(cache.getValue("world")).isNull();

        value = cache.getValue("world", new Cache.Source() {
            @Override
            public Object getValue() {
                return "hello";
            }
        });
        assertThat(value).isEqualTo("hello");
        assertThat(cache.getUsedSize()).isEqualTo(2);
        assertThat(cache.getValue("world")).isEqualTo("hello");
    }

    @Test
    public void testDeleteValue() throws Exception {
        cache.deleteValue("hello");
        cache.deleteValue(null);
        assertThat(cache.getUsedSize()).isZero();

        for (int i = 0; i < 5; i++) {
            final int value = i;
            cache.getValue("key" + value, new Cache.Source() {
                @Override
                public Object getValue() {
                    return ("value" + value);
                }
            });
        }
        assertThat(cache.getValue("key1")).isEqualTo("value1");
        assertThat(cache.getUsedSize()).isEqualTo(5);

        // test deleteValue() method
        cache.deleteValue("key1");
        assertThat(cache.getValue("key1")).isNull();
        assertThat(cache.getUsedSize()).isEqualTo(4);

        cache.deleteValue("hello");
        assertThat(cache.getUsedSize()).isEqualTo(4);
    }

    @Test
    public void testDeleteAll() {
        cache.deleteAll();
        assertThat(cache.getUsedSize()).isZero();

        for (int i = 0; i < 5; i++) {
            final int value = i;
            cache.getValue("key" + value, new Cache.Source() {
                @Override
                public Object getValue() {
                    return ("value" + value);
                }
            });
        }

        assertThat(cache.getUsedSize()).isEqualTo(5);
        cache.deleteAll();
        assertThat(cache.getUsedSize()).isZero();
    }
}
