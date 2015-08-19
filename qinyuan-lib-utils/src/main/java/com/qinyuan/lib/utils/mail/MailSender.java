package com.qinyuan.lib.utils.mail;

import java.util.List;

public interface MailSender {
    void send(String recipient, String subject, Object content);

    void send(List<String> recipients, String subject, Object content);
}
