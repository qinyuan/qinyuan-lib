package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.lang.ExceptionUtils;
import org.junit.Test;

public class SendCloudMailSenderTest {
    @Test
    public void testSend() throws Exception {
        //String user = "erikzhao_test_KhSmWe";
        String user = "new_user";
        //String domainName = "eXsuZZx5oC0MAW8hKz2Il2IZihcGhYbb.sendcloud.org";
        String domainName = "bud-vip.com";
        String apiKey = "IjGx8tQGs8CbwgAh";
        SendCloudMailSender sender = new SendCloudMailSender(user, domainName, apiKey);

        String to = "qinyuan15@qq.com";
        String subject = "来自SendCloud, qinyuan的第一封邮件";

        String content = "你太棒了！你已成功的从SendCloud发送了一封测试邮件，接下来快登录前台去完善账户信息吧！";
        sender.send(to, subject, content);

        content = "BBBBBBBBBBBBBBBBBBBBBB";
        try {
            new SendCloudMailSender(user, domainName, apiKey).send(to, subject, content);
        } catch (Exception e) {
            System.out.println("\nexception: " + ExceptionUtils.getMessage(e));
        }
    }
}
