package com.qinyuan.lib.lang;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SecrecyUtilsTest {
    @Test
    public void testMakeTelSecret() throws Exception {
        assertThat(SecrecyUtils.makeTelSecret("1301324846")).isEqualTo("13**4846");
        assertThat(SecrecyUtils.makeTelSecret("134846")).isEqualTo("13**4846");
        assertThat(SecrecyUtils.makeTelSecret("13846")).isNull();
    }

    @Test
    public void testMakeEmailSecret() throws Exception {
        assertThat(SecrecyUtils.makeEmailSecret("testme@qq.com")).isEqualTo("te**@qq.com");
        assertThat(SecrecyUtils.makeEmailSecret("me@qq.com")).isEqualTo("me**@qq.com");
        assertThat(SecrecyUtils.makeEmailSecret("m@qq.com")).isEqualTo("m**@qq.com");
        assertThat(SecrecyUtils.makeEmailSecret("helloworld")).isNull();
        assertThat(SecrecyUtils.makeEmailSecret("hello@world@hello")).isNull();

    }
}
