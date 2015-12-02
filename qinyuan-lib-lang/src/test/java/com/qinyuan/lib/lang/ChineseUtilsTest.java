package com.qinyuan.lib.lang;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test ChineseUtils
 * Created by qinyuan on 15-2-25.
 */
public class ChineseUtilsTest {
    @Test
    public void testGetPhoneticLetter() {
        String testString = "我的奋斗";
        String result = ChineseUtils.getPhoneticLetter(testString);
        assertThat(result).isEqualTo("WDFD");

        testString = "小";
        result = ChineseUtils.getPhoneticLetter(testString);
        assertThat(result).isEqualTo("X");

        testString = "gap";
        result = ChineseUtils.getPhoneticLetter(testString);
        assertThat(result).isEqualTo("GAP");
    }

    @Test
    public void testGetChineseStringLength() {
        assertThat(ChineseUtils.getChineseStringLength("中国")).isEqualTo(4);
        assertThat(ChineseUtils.getChineseStringLength("Hello")).isEqualTo(5);
        assertThat(ChineseUtils.getChineseStringLength("HELLO")).isEqualTo(5);
        assertThat(ChineseUtils.getChineseStringLength("hello, 中国")).isEqualTo(11);
        assertThat(ChineseUtils.getChineseStringLength(null)).isEqualTo(0);
    }
}
