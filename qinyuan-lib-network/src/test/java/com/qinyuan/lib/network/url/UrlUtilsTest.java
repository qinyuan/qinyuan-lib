package com.qinyuan.lib.network.url;

import org.assertj.core.data.MapEntry;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test UrlUtils
 * Created by qinyuan on 15-6-4.
 */
public class UrlUtilsTest {
    @Test
    public void testEncodeUrl() {
        assertThat(UrlUtils.encode("http://www.baidu.com")).isEqualTo("http%3A%2F%2Fwww.baidu.com");
    }

    @Test
    public void testGetHost() throws Exception {
        assertThat(UrlUtils.getHost("http://s.etao.com")).isEqualTo("http://s.etao.com");
        assertThat(UrlUtils.getHost("http://s.etao.com/")).isEqualTo("http://s.etao.com");
        assertThat(UrlUtils.getHost("http://s.etao.com/detail/12345.html"))
                .isEqualTo("http://s.etao.com");

        assertThat(UrlUtils.getHost("www.baidu.com")).isEqualTo("www.baidu.com");
        assertThat(UrlUtils.getHost("www.baidu.com/")).isEqualTo("www.baidu.com");
        assertThat(UrlUtils.getHost("www.baidu.com/index.php")).isEqualTo("www.baidu.com");
    }

    @Test
    public void testParseParameters() {
        Map<String, String> map = UrlUtils.parseParameters("test.html?key1=value1&key2=value2");
        assertThat(map).hasSize(2).contains(MapEntry.entry("key1", "value1"), MapEntry.entry("key2", "value2"));

        map = UrlUtils.parseParameters("test?key1=value1&key2=value2&");
        assertThat(map).hasSize(2).contains(MapEntry.entry("key1", "value1"), MapEntry.entry("key2", "value2"));

        map = UrlUtils.parseParameters("test.html");
        assertThat(map).isEmpty();

        map = UrlUtils.parseParameters("key1=value1&key2=value2&");
        assertThat(map).isEmpty();
    }
}
