package com.qinyuan.lib.network.http;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import org.junit.Test;

public class TimingVisitorTest {
    @Test
    public void testRun() throws Exception {
        TimingVisitor visitor = new TimingVisitor();
        visitor.setPages("http://www.bud-vip.com/commodity.html,http://www.bud-vip.com/index.html");
        visitor.setInterval(1);
        visitor.init();
        ThreadUtils.sleep(10);
    }
}
