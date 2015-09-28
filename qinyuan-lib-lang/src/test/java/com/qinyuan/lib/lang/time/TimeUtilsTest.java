package com.qinyuan.lib.lang.time;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeUtilsTest {
    @Test
    public void testIsTime() throws Exception {
        assertThat(TimeUtils.isTime("12:14:20")).isTrue();
        assertThat(TimeUtils.isTime("12-12-12")).isFalse();
        assertThat(TimeUtils.isTime("abc")).isFalse();
        assertThat(TimeUtils.isTime("")).isFalse();
        assertThat(TimeUtils.isTime(null)).isFalse();
    }

    @Test
    public void testIsIntersected() throws Exception {
        TimePeriod p1 = new TimePeriod(new Time(12, 0, 0), new Time(14, 0, 0));
        TimePeriod p2 = new TimePeriod(new Time(8, 0, 0), new Time(11, 0, 0));
        assertThat(TimeUtils.isIntersected(p1, p2)).isFalse();
        assertThat(TimeUtils.isIntersected(p2, p1)).isFalse();

        p2 = new TimePeriod(new Time(8, 0, 0), new Time(12, 0, 0));
        assertThat(TimeUtils.isIntersected(p1, p2)).isFalse();
        assertThat(TimeUtils.isIntersected(p2, p1)).isFalse();

        p2 = new TimePeriod(new Time(13, 59, 59), new Time(15, 0, 0));
        assertThat(TimeUtils.isIntersected(p1, p2)).isTrue();
        assertThat(TimeUtils.isIntersected(p2, p1)).isTrue();

        p2 = new TimePeriod(new Time(8, 0, 0), new Time(12, 1, 0));
        assertThat(TimeUtils.isIntersected(p1, p2)).isTrue();
        assertThat(TimeUtils.isIntersected(p2, p1)).isTrue();
    }

    @Test
    public void testIntersect() {
        TimePeriod p1 = new TimePeriod(new Time(12, 0, 0), new Time(14, 0, 0));
        TimePeriod p2 = new TimePeriod(new Time(8, 0, 0), new Time(11, 0, 0));
        assertThat(TimeUtils.intersect(p1, p2)).isNull();

        p1 = new TimePeriod(new Time(12, 0, 0), new Time(14, 0, 0));
        p2 = new TimePeriod(new Time(8, 0, 0), new Time(13, 0, 0));
        TimePeriod intersection = TimeUtils.intersect(p1, p2);
        assertThat(intersection.toString()).isEqualTo("12:00:00~13:00:00");
        intersection = TimeUtils.intersect(p2, p1);
        assertThat(intersection.toString()).isEqualTo("12:00:00~13:00:00");

        p2 = new TimePeriod(new Time(8, 0, 0), new Time(15, 0, 0));
        intersection = TimeUtils.intersect(p1, p2);
        assertThat(intersection.toString()).isEqualTo("12:00:00~14:00:00");
        intersection = TimeUtils.intersect(p2, p1);
        assertThat(intersection.toString()).isEqualTo("12:00:00~14:00:00");

        p2 = new TimePeriod(new Time(12, 15, 20), new Time(15, 0, 0));
        intersection = TimeUtils.intersect(p1, p2);
        assertThat(intersection.toString()).isEqualTo("12:15:20~14:00:00");
        intersection = TimeUtils.intersect(p2, p1);
        assertThat(intersection.toString()).isEqualTo("12:15:20~14:00:00");

        p2 = new TimePeriod(new Time(12, 15, 20), new Time(13, 0, 0));
        intersection = TimeUtils.intersect(p1, p2);
        assertThat(intersection.toString()).isEqualTo("12:15:20~13:00:00");
        intersection = TimeUtils.intersect(p2, p1);
        assertThat(intersection.toString()).isEqualTo("12:15:20~13:00:00");

        intersection = TimeUtils.intersect(p1, p1);
        assertThat(intersection.toString()).isEqualTo("12:00:00~14:00:00");
    }

    @Test
    public void testGetSecondDiff() throws Exception {
        assertThat(TimeUtils.getSecondDiff(new Time(12, 13, 14), new Time(12, 20, 14))).isEqualTo(420);
        assertThat(TimeUtils.getSecondDiff(new Time(12, 20, 14), new Time(12, 13, 14))).isEqualTo(-420);
        assertThat(TimeUtils.getSecondDiff(new Time(12, 13, 14), new Time(12, 20, 54))).isEqualTo(460);
        assertThat(TimeUtils.getSecondDiff(new Time(12, 13, 14), new Time(13, 20, 54))).isEqualTo(4060);
    }
}
