package com.qinyuan.lib.contact.mail;

import java.util.List;

public interface MailSender {
    void send(String recipient, String subject, Object content);

    void send(List<String> recipients, String subject, Object content);
}
