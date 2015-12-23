package com.qinyuan.lib.lang.file;

import org.junit.Test;

import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test ClasspathFileUtils
 * Created by qinyuan on 15-6-11.
 */
public class ClasspathFileUtilsTest {
    @Test
    public void testGetProperties() {
        Properties properties = ClasspathFileUtils.getProperties("test.properties");
        assertThat(properties.getProperty("hello")).isEqualTo("world");
        assertThat(properties.getProperty("world")).isNull();
        System.out.println(properties.getProperty("chinese"));
    }

    @Test
    public void testGetPropertyMap() {
        Map<String, String> map = ClasspathFileUtils.getPropertyMap("test.properties");
        assertThat(map.get("hello")).isEqualTo("world");
        assertThat(map.get("world")).isNull();
        assertThat(map.get("chinese")).isEqualTo("中国");
    }

    @Test
    public void testGetFile() throws Exception {
        assertThat(ClasspathFileUtils.getFile("baidu.html")).isFile();
        assertThat(ClasspathFileUtils.getFile("baidu2.html")).isNull();
    }
}
