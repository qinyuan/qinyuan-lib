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
        VisitRecord visitRecord = new VisitRecord(ip, time, userAgent, url);
        visitorRecorder.add(visitRecord);
        assertThat(visitorRecorder.getRecords()).hasSize(1);

        // test if getRecords() method returns duplication
        visitorRecorder.getRecords().clear();
        assertThat(visitorRecorder.getRecords()).hasSize(1);
    }
}
