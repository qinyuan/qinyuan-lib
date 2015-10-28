package com.qinyuan.lib.mvc.security;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleUserDaoTest extends DatabaseTestCase {
    private SimpleUserDao dao;

    @Before
    public void setUp2() {
        dao = new SimpleUserDao();
    }

    @Test
    public void testGetInstanceByName() throws Exception {
        assertThat(dao.getInstanceByName("user11")).isNull();

        User user = dao.getInstanceByName("user1");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("user1");
        assertThat(user.getPassword()).isEqualTo("password1");
        assertThat(user.getRole()).isEqualTo("role1");

        user = dao.getInstanceByName("User1");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("user1");
        assertThat(user.getPassword()).isEqualTo("password1");
        assertThat(user.getRole()).isEqualTo("role1");

        dao.setIgnoreCase(false);
        assertThat(dao.getInstanceByName("user1")).isNotNull();
        assertThat(dao.getInstanceByName("User1")).isNull();
    }

    @Test
    public void testGetIdByName() throws Exception {
        assertThat(dao.getIdByName("user1")).isEqualTo(1);
        assertThat(dao.getIdByName("user2")).isNull();
        assertThat(dao.getIdByName("user3")).isEqualTo(2);

        assertThat(dao.getIdByName("User1")).isEqualTo(1);
        dao.setIgnoreCase(false);
        assertThat(dao.getIdByName("User1")).isNull();
    }
}
