package com.qinyuan.lib.lang.time;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class WeightedTimeTest {
    @Test
    public void testAddWeights() {
        WeightedTime weightedTime = new WeightedTime("2015-01-12 15:15:15", "2015-01-12 15:15:14");

        weightedTime.addWeight(new Time(10, 10, 10), new Time(12, 10, 10), 1);

        try {
            weightedTime.addWeight(new Time(11, 10, 10), new Time(10, 10, 10), 1);
            fail("exception is not thrown");
        } catch (Exception e) {
            System.out.println(e);
            // nothing to do
        }

    }

    @Test
    public void testCountSeconds() throws Exception {
        WeightedTime weightedTime = new WeightedTime("2015-01-12 15:15:15", "2015-01-12 15:15:14");
        assertThat(weightedTime.countSeconds()).isZero();

        weightedTime = new WeightedTime("2015-01-12 15:00:00", "2015-01-12 18:00:00");
        assertThat(weightedTime.countSeconds()).isEqualTo(3600 * 3);

        weightedTime.addWeight(new Time(15, 0, 0), new Time(16, 0, 0), 3);
        assertThat(weightedTime.countSeconds()).isEqualTo(3600 * 3 + 3600 * 2);

        weightedTime.clearWeight().addWeight(new Time(10, 0, 0), new Time(16, 0, 0), 3);
        assertThat(weightedTime.countSeconds()).isEqualTo(3600 * 3 + 3600 * 2);

        weightedTime = new WeightedTime("2015-01-12 00:00:00", "2015-01-13 00:00:00");
        assertThat(weightedTime.countSeconds()).isEqualTo(3600 * 24);

        weightedTime.addWeight(new Time(12, 0, 0), new Time(13, 30, 0), 10);
        assertThat(weightedTime.countSeconds()).isEqualTo(3600 * 24 + 5400 * 9);

        weightedTime = new WeightedTime("2015-01-12 00:00:00", "2015-01-14 12:00:00");
        assertThat(weightedTime.countSeconds()).isEqualTo(3600 * 60);

        weightedTime.addWeight(new Time(12, 0, 0), new Time(13, 0, 0), 8);
        assertThat(weightedTime.countSeconds()).isEqualTo(3600 * 60 + 3600 * 2 * 7);

        weightedTime = new WeightedTime("2015-01-12 00:00:00", "2015-01-18 12:00:05");
        assertThat(weightedTime.countSeconds()).isEqualTo(3600 * 6 * 24 + 3600 * 12 + 5);

        weightedTime = new WeightedTime("2015-09-28 10:00:00", "2015-09-29 16:22:50");
        weightedTime.addWeight(new Time(10, 0, 0), new Time(22, 0, 0), 10);
        assertThat(weightedTime.countSeconds()).isEqualTo(12 * 3600 * 10 + 12 * 3600 + (3600 * 6 + 60 * 22 + 50) * 10);

        weightedTime = new WeightedTime("2015-09-28 10:00:00", "2015-09-29 22:00:00");
        weightedTime.addWeight(new Time(10, 0, 0), new Time(22, 0, 0), 10);
        assertThat(weightedTime.countSeconds()).isEqualTo(12 * 3600 * 10 + 12 * 3600 + 12 * 3600 * 10);

        weightedTime = new WeightedTime("2015-10-10 22:18:22", "2015-10-11 21:15:00");
        weightedTime.addWeight(new Time(10, 0, 0), new Time(22, 0, 0), 10);
        System.out.println(weightedTime.countSeconds());

        weightedTime = new WeightedTime("2015-09-28 10:00:00", "2015-09-29 22:00:00");
        weightedTime.addWeight(new Time(10, 0, 0), new Time(22, 0, 0), 10000000);
        System.out.println(weightedTime.countSeconds());
        System.out.println(Long.MAX_VALUE);
    }
}
