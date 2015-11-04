package com.qinyuan.lib.mvc.controller;

import com.qinyuan.lib.config.ImageConfig;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageUrlAdapterTest {
    @Test
    public void testPathToUrl() throws Exception {
        ImageConfig config = new ImageConfig();

        config.setDirectory("/var/www/html/lottery");
        config.setContext("lottery");
        config.setHost("http://www.qin-yuan.site:8080/lottery");
        config.setPort(8080);
        config.setProtocal("http");

        ImageUrlAdapter adapter = new ImageUrlAdapter(config, "192.168.8.1");
        String testPath = "/var/www/html/lottery/helloworld.png";
        assertThat(adapter.pathToUrl(testPath)).isEqualTo("http://www.qin-yuan.site:8080/lottery/helloworld.png");

        config.setHost("http://www.qin-yuan.site:8080");
        assertThat(adapter.pathToUrl(testPath)).isEqualTo("http://www.qin-yuan.site:8080/lottery/helloworld.png");

        config.setHost(null);
        assertThat(adapter.pathToUrl(testPath)).isEqualTo("http://192.168.8.1:8080/lottery/helloworld.png");
    }
}
