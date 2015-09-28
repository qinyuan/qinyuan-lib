package com.qinyuan.lib.lang.time;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TimePeriodTest {
    @Test
    public void testGetSeconds() throws Exception {
        Time t1 = new Time(12, 14, 15);
        Time t2 = new Time(12, 15, 0);
        TimePeriod p = new TimePeriod(t1, t2);
        assertThat(p.getSeconds()).isEqualTo(45);
    }
}
