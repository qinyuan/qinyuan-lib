package com.qinyuan.lib.lang;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test IntegerUtils
 * Created by qinyuan on 15-4-23.
 */
public class IntegerUtilsTest {
    @Test
    public void testIsPositive() throws Exception {
        assertThat(IntegerUtils.isPositive(1)).isTrue();
        assertThat(IntegerUtils.isPositive(0)).isFalse();
        assertThat(IntegerUtils.isPositive(-1)).isFalse();
        assertThat(IntegerUtils.isPositive((Integer) null)).isFalse();
    }

    @Test
    public void testIsPositive2() throws Exception {
        assertThat(IntegerUtils.isPositive("1")).isTrue();
        assertThat(IntegerUtils.isPositive("0")).isFalse();
        assertThat(IntegerUtils.isPositive("-1")).isFalse();
        assertThat(IntegerUtils.isPositive("abc")).isFalse();
        assertThat(IntegerUtils.isPositive("")).isFalse();
        assertThat(IntegerUtils.isPositive((String) null)).isFalse();
    }

    @Test
    public void testIsNotNegative() {
        assertThat(IntegerUtils.isNotNegative(1)).isTrue();
        assertThat(IntegerUtils.isNotNegative(0)).isTrue();
        assertThat(IntegerUtils.isNotNegative(-1)).isFalse();
        assertThat(IntegerUtils.isNotNegative((Integer) null)).isFalse();

    }

    @Test
    public void testIsNotNegative2() {
        assertThat(IntegerUtils.isNotNegative("1")).isTrue();
        assertThat(IntegerUtils.isNotNegative("0")).isTrue();
        assertThat(IntegerUtils.isNotNegative("-1")).isFalse();
        assertThat(IntegerUtils.isNotNegative((String) null)).isFalse();
    }

    @Test
    public void testGetMaxContinuationSize() throws Exception {
        List<Integer> integers = Lists.newArrayList(1, 2, 3, 5, 6, 7, 8, 10);
        assertThat(IntegerUtils.getMaxContinuationSize(integers)).isEqualTo(4);

        integers = Lists.newArrayList(1, 2, 3, 5, 6, 7, 8, 10, 3, 6);
        assertThat(IntegerUtils.getMaxContinuationSize(integers)).isEqualTo(4);

        integers = Lists.newArrayList(1, 5, 6, 7, 8, 10, 3, 2);
        assertThat(IntegerUtils.getMaxContinuationSize(integers)).isEqualTo(4);

        integers = Lists.newArrayList(-1, -2, -3, -4, 1, 2, 4, 3, 4, 7, 8);
        assertThat(IntegerUtils.getMaxContinuationSize(integers)).isEqualTo(4);
    }
}
