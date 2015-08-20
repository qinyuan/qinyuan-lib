package com.qinyuan.lib.network.ip;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SogouIpLocationQuerierTest {
    @Test
    public void testParse() {
        SogouIpLocationQuerier querier = new SogouIpLocationQuerier();
        assertThat(querier.getLocation("113.87.101.227")).isEqualTo("广东省深圳市电信");
    }
}
