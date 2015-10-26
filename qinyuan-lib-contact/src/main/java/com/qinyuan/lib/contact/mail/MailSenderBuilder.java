package com.qinyuan.lib.contact.mail;

import org.apache.commons.lang3.StringUtils;

public class MailSenderBuilder {
    public MailSender build(int mailAccountId) {
        MailAccount account = new MailAccountDao().getInstance(mailAccountId);
        if (account == null) {
            throw new RuntimeException("No mail account found with mail account id " + mailAccountId);
        }

        String type = account.getType();
        if (StringUtils.isBlank(type)) {
            throw new RuntimeException("empty type with mail account id " + mailAccountId);
        }

        MailSender mailSender;
        if (type.equals(SimpleMailAccount.class.getSimpleName())) {
            SimpleMailAccount simpleMailAccount = new SimpleMailAccountDao().getInstance(account.getReferenceId());
            if (simpleMailAccount == null) {
                throw new RuntimeException("no simple mail account with id " + account.getReferenceId());
            }
            mailSender = new SimpleMailSender(simpleMailAccount.getHost(), simpleMailAccount.getUsername(), simpleMailAccount.getPassword());
        } else if (type.equals(SendCloudAccount.class.getSimpleName())) {
            SendCloudAccount sendCloudAccount = new SendCloudAccountDao().getInstance(account.getReferenceId());
            if (sendCloudAccount == null) {
                throw new RuntimeException("no simple mail account with id " + account.getReferenceId());
            }
            mailSender = new SendCloudMailSender(sendCloudAccount.getUser(), sendCloudAccount.getDomainName(), sendCloudAccount.getApiKey());
        } else {
            throw new RuntimeException("unrecognized type: " + type);
        }
        return new ReplaceSensitiveCharMailSender(mailSender);
    }
}
