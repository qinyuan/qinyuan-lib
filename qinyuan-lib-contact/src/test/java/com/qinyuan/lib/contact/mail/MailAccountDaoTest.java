package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class MailAccountDaoTest extends DatabaseTestCase {
    private MailAccountDao dao = new MailAccountDao();

    @Test
    public void testAdd() throws Exception {
        assertThat(dao.count()).isEqualTo(3);
        dao.add(1, "test");
        assertThat(dao.count()).isEqualTo(4);
    }

    @Test
    public void testDelete() {
        assertThat(dao.count()).isEqualTo(3);
        assertThat(new SimpleMailAccountDao().count()).isEqualTo(2);

        dao.delete(2);

        assertThat(dao.count()).isEqualTo(2);
        assertThat(new SimpleMailAccountDao().count()).isEqualTo(1);
        assertThat(new SimpleMailAccountDao().hasUsername("username1")).isFalse();
        assertThat(new SimpleMailAccountDao().hasUsername("username2")).isTrue();
    }

    @Test
    public void testGetReference() throws Exception {
        RealMailAccount account = dao.getReference(dao.getInstance(1));
        assertThat(account).isExactlyInstanceOf(SimpleMailAccount.class);
        assertThat(account.getUsername()).isEqualTo("username2");

        account = dao.getReference(dao.getInstance(3));
        assertThat(account).isExactlyInstanceOf(SendCloudAccount.class);
        assertThat(account.getUsername()).isEqualTo("user1@domainName1");

        dao.add(3, SendCloudAccount.class.getSimpleName());
        assertThat(dao.count()).isEqualTo(4);
        assertThat(dao.getReference(dao.getInstance(4))).isNull();

        try {
            dao.add(1, SimpleMailAccount.class.getSimpleName());
            fail("no exception thrown");
        } catch (Exception e) {
            // nothing to do
        }

        dao.add(1, "unkownType");
        assertThat(dao.count()).isEqualTo(5);
        assertThat(dao.getReference(dao.getInstance(5))).isNull();
    }
}
