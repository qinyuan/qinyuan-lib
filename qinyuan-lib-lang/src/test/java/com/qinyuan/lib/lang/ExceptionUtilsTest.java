package com.qinyuan.lib.lang;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionUtilsTest {
    @Test
    public void testGetMessage() throws Exception {
        Exception e = new Exception("HelloWorld");
        assertThat(ExceptionUtils.getMessage(e)).isEqualTo("HelloWorld");

        e = new RuntimeException("HelloWorld");
        assertThat(ExceptionUtils.getMessage(e)).isEqualTo("HelloWorld");

        e = new RuntimeException(new Exception("HelloWorld"));
        assertThat(ExceptionUtils.getMessage(e)).isEqualTo("HelloWorld");
    }
}
