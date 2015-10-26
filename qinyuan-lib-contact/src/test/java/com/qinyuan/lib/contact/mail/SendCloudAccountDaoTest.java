package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class SendCloudAccountDaoTest extends DatabaseTestCase {
    private SendCloudAccountDao dao = new SendCloudAccountDao();

    @Test
    public void testAdd() throws Exception {
        assertThat(dao.count()).isEqualTo(1);
        assertThat(new MailAccountDao().count()).isEqualTo(3);

        try {
            dao.add("user1", "domainName1", "serialKey1");
            fail("no exception thrown");
        } catch (Exception e) {
            // nothing to do
        }

        dao.add("user1", "domainName2", "serialKey2");
        assertThat(dao.count()).isEqualTo(2);
        assertThat(new MailAccountDao().count()).isEqualTo(4);
    }

    @Test
    public void testUpdate() throws Exception {
        dao.update(1, "user11", "domainName11", "apiKey11");
        SendCloudAccount account = dao.getInstance(1);
        assertThat(account.getUser()).isEqualTo("user11");
        assertThat(account.getApiKey()).isEqualTo("apiKey11");
        assertThat(account.getDomainName()).isEqualTo("domainName11");
        assertThat(account.getUsername()).isEqualTo("user11@domainName11");
    }
}
