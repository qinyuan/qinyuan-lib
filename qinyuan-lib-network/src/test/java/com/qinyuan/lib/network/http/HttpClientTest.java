package com.qinyuan.lib.network.http;

import com.qinyuan.lib.lang.test.TestFileUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpClientTest {
    private HttpClient client;
    private String url = "http://localhost/test/test.php";

    @Before
    public void setUp() throws Exception {
        client = new HttpClient();
    }

    @Test
    public void testGetContent() throws Exception {
        System.out.println(client.getContent(url));

        Map<String, String> params = new HashMap<>();
        params.put("hello", "world");
        params.put("hello2", "world2");
        System.out.println(client.getContent(url, params));

        client.setMethod(HttpClient.Method.POST);
        System.out.println(client.getContent(url, params));
    }

    @Test
    public void testDownload() throws Exception {
        String filePath = TestFileUtils.tempDir + "/crawler/baidu.html";
        client.download(url, filePath);
    }
}
