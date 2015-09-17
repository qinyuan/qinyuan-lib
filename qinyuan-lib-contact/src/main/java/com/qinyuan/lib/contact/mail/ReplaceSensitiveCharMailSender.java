package com.qinyuan.lib.contact.mail;

import java.util.List;

/**
 * Class to replace sensitive characters then send mail
 * Created by qinyuan on 15-7-1.
 */
public class ReplaceSensitiveCharMailSender implements MailSender {

    private SimpleMailSender simpleMailSender;

    public ReplaceSensitiveCharMailSender(String smtpHostname, String username, String password) {
        simpleMailSender = new SimpleMailSender(smtpHostname, username, password);
    }

    public ReplaceSensitiveCharMailSender(String username, String password) {
        simpleMailSender = new SimpleMailSender(username, password);
    }

    public void send(String recipient, String subject, Object content) {
        simpleMailSender.send(recipient, subject, replaceContent(content));
    }

    public void send(List<String> recipients, String subject, Object content) {
        simpleMailSender.send(recipients, subject, replaceContent(content));
    }

    private Object replaceContent(Object content) {
        if (content != null && content instanceof String) {
            String string = content.toString();

            string = string.replace("<p>", "<div>");
            string = string.replace("</p>", "</div>");
            string = string.replace("<p ", "<div ");

            return string;
        } else {
            return content;
        }
    }
}
