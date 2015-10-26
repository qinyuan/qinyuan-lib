package com.qinyuan.lib.contact.mail;

public class MailSenderBuilder {
    public MailSender build(int mailAccountId) {
        MailAccount account = new MailAccountDao().getInstance(mailAccountId);
        if (account == null) {
            throw new RuntimeException("No mail account found with mail account id " + mailAccountId);
        }

        MailSender mailSender;
        RealMailAccount mailAccount = new MailAccountDao().getReference(account);
        if (mailAccount instanceof SimpleMailAccount) {
            SimpleMailAccount simpleMailAccount = (SimpleMailAccount) mailAccount;
            mailSender = new SimpleMailSender(simpleMailAccount.getHost(), simpleMailAccount.getUsername(), simpleMailAccount.getPassword());
        } else if (mailAccount instanceof SendCloudAccount) {
            SendCloudAccount sendCloudAccount = (SendCloudAccount) mailAccount;
            mailSender = new SendCloudMailSender(sendCloudAccount.getUser(), sendCloudAccount.getDomainName(), sendCloudAccount.getApiKey());
        } else {
            throw new RuntimeException("unrecognized mail account type: " + mailAccount.getClass().getName());
        }
        return new ReplaceSensitiveCharMailSender(mailSender);
    }
}
