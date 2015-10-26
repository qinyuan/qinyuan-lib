package com.qinyuan.lib.contact.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Class to replace sensitive characters then send mail
 * Created by qinyuan on 15-7-1.
 */
public class ReplaceSensitiveCharMailSender implements MailSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(ReplaceSensitiveCharMailSender.class);

    private MailSender mailSender;

    /*public ReplaceSensitiveCharMailSender(String smtpHostname, String username, String password) {
        simpleMailSender = new SimpleMailSender(smtpHostname, username, password);
    }

    public ReplaceSensitiveCharMailSender(String username, String password) {
        simpleMailSender = new SimpleMailSender(username, password);
    }*/

    public ReplaceSensitiveCharMailSender(MailSender mailSender) {
        if (mailSender == null) {
            LOGGER.error("mail sender is null");
            throw new IllegalArgumentException("mail sender is null");
        }

        this.mailSender = mailSender;
    }

    public void send(String recipient, String subject, Object content) {
        mailSender.send(recipient, subject, replaceContent(content));
    }

    public void send(List<String> recipients, String subject, Object content) {
        mailSender.send(recipients, subject, replaceContent(content));
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
