package com.qinyuan.lib.lang.file;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileNameUtilsTest {
    @Test
    public void testGetAsciiFileName() throws Exception {
        assertThat(FileNameUtils.getAsciiFileName("hello 张三 world.txt")).isEqualTo(
                "hello_plus_percentE5percentBCpercentA0percentE4percentB8percent89_plus_world.txt");
        assertThat(FileNameUtils.getAsciiFileName("hello world.txt")).isEqualTo(
                "hello_plus_world.txt");
    }
}
