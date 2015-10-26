package com.qinyuan.lib.contact.mail;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleMailAccountDaoTest extends DatabaseTestCase {
    private SimpleMailAccountDao dao = new SimpleMailAccountDao();

    @Test
    public void testAdd() throws Exception {
        assertThat(dao.count()).isEqualTo(2);
        assertThat(new MailAccountDao().count()).isEqualTo(3);
        dao.add("host3", "username3", "password3");
        assertThat(dao.count()).isEqualTo(3);
        assertThat(new MailAccountDao().count()).isEqualTo(4);
    }

    @Test
    public void testUpdate() throws Exception {
        dao.update(1, "host11", "password11");
        assertThat(dao.getInstance(1).getHost()).isEqualTo("host11");
        assertThat(dao.getInstance(1).getPassword()).isEqualTo("password11");
    }

    @Test
    public void testHasUsername() throws Exception {
        assertThat(dao.hasUsername("username1")).isTrue();
        assertThat(dao.hasUsername("username3")).isFalse();
    }
}
