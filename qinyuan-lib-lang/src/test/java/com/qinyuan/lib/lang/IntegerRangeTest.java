package com.qinyuan.lib.lang;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegerRangeTest {

    @Test
    public void test() {
        IntegerRange range = new IntegerRange("20~100");
        assertThat(range.getStart()).isEqualTo(20);
        assertThat(range.getEnd()).isEqualTo(100);
        assertThat(range.getDiff()).isEqualTo(80);

        range = new IntegerRange("100~50");
        assertThat(range.getStart()).isEqualTo(50);
        assertThat(range.getEnd()).isEqualTo(100);
        assertThat(range.getDiff()).isEqualTo(50);

        range = new IntegerRange(20, 30);
        assertThat(range.getStart()).isEqualTo(20);
        assertThat(range.getEnd()).isEqualTo(30);

        range = new IntegerRange(30, 20);
        assertThat(range.getStart()).isEqualTo(20);
        assertThat(range.getEnd()).isEqualTo(30);
    }

    @Test
    public void testValidate() {
        assertThat(IntegerRange.validate("20~50")).isTrue();
        assertThat(IntegerRange.validate("2050")).isFalse();
        assertThat(IntegerRange.validate("20^50")).isFalse();
    }
}
