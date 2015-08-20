package com.qinyuan.lib.network.ip;

import com.qinyuan.lib.lang.test.TestFileUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TaobaoIpLocationDataParserTest {
    @Test
    public void testParse() throws Exception {
        TaobaoIpLocationDataParser parser = new TaobaoIpLocationDataParser();
        String content = parser.parse(TestFileUtils.read("taobao-ip-location-data.json"));
        assertThat(content).isEqualTo("中国-华南-广东省-深圳市");
    }
}
