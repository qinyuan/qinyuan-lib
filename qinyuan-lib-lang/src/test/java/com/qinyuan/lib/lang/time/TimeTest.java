package com.qinyuan.lib.lang.time;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeTest {
    @Test
    public void testToString() throws Exception {
        assertThat(new Time(10, 11, 12).toString()).isEqualTo("10:11:12");
    }
}
