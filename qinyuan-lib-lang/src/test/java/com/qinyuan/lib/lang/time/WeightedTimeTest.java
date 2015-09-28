package com.qinyuan.lib.lang.time;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WeightedTimeTest {
    @Test
    public void testCountSeconds() throws Exception {
        WeightedTime weightedTime = new WeightedTime("2015-01-12 15:15:15", "2015-01-12 15:15:14");
        assertThat(weightedTime.countSeconds()).isZero();
    }
}
