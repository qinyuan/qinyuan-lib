package com.qinyuan.lib.lang.time;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeTest {
    @Test
    public void testToString() throws Exception {
        assertThat(new Time(10, 11, 12).toString()).isEqualTo("10:11:12");
    }

    @Test
    public void testConstructor() {
        Time time = new Time("10:02:13");
        assertThat(time.getHour()).isEqualTo(10);
        assertThat(time.getMinute()).isEqualTo(2);
        assertThat(time.getSecond()).isEqualTo(13);

        time = new Time(DateUtils.newDate("2012-12-12 15:16:17"));
        assertThat(time.getHour()).isEqualTo(15);
        assertThat(time.getMinute()).isEqualTo(16);
        assertThat(time.getSecond()).isEqualTo(17);
    }

    @Test
    public void testIsLaterThan() {
        assertThat(new Time(10, 11, 12).isLaterThan(new Time(10, 11, 11))).isTrue();
        assertThat(new Time(10, 11, 12).isLaterThan(new Time(10, 11, 13))).isFalse();
        assertThat(new Time(10, 11, 12).isLaterThan(null)).isFalse();
    }

    @Test
    public void testIsEarlierThan() {
        assertThat(new Time(10, 11, 12).isEarlierThan(new Time(10, 11, 10))).isFalse();
        assertThat(new Time(10, 11, 12).isEarlierThan(null)).isFalse();
        assertThat(new Time(10, 11, 12).isEarlierThan(new Time(10, 12, 10))).isTrue();
    }
}
