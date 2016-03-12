package com.qinyuan.lib.mvc.visitor;

import com.qinyuan.lib.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VisitorRecorderTest {
    private VisitorRecorder visitorRecorder;

    @Before
    public void setUp() {
        visitorRecorder = new VisitorRecorder();
    }

    @Test
    public void testGetRecords() throws Exception {
        assertThat(visitorRecorder.getRecords()).isEmpty();

        // test add() method
        String ip = "192.168.8.1";
        String time = DateUtils.nowString();
        String userAgent = "Firefox";
        String url = "test.html";
        visitorRecorder.add(ip, time, userAgent, url);
        assertThat(visitorRecorder.getRecords()).hasSize(1);

        // test if getRecords() method returns duplication
        visitorRecorder.getRecords().clear();
        assertThat(visitorRecorder.getRecords()).hasSize(1);

        // test order
        ip = "192.168.8.2";
        time = DateUtils.nowString();
        userAgent = "Google Chrome";
        url = "hello.json";
        visitorRecorder.add(ip, time, userAgent, url);
        assertThat(visitorRecorder.getRecords()).hasSize(2);
        assertThat(visitorRecorder.getRecords().get(0).getIp()).isEqualTo("192.168.8.2");
        assertThat(visitorRecorder.getRecords().get(1).getIp()).isEqualTo("192.168.8.1");
    }
}
