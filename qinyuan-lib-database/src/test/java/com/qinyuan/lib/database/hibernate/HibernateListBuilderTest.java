package com.qinyuan.lib.database.hibernate;

import com.qinyuan.lib.database.User;
import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test HibernateListBuilder
 * Created by qinyuan on 15-6-26.
 */
public class HibernateListBuilderTest extends DatabaseTestCase {
    @Test
    public void testBuildBySQL() {
        List<Object[]> list = new HibernateListBuilder().buildBySQL("SELECT * FROM user");
        assertThat(list).hasSize(4);
        for (Object[] objects : list) {
            assertThat(objects).hasSize(3);
        }

        List<Integer> integers = new HibernateListBuilder().buildBySQL("SELECT id FROM user", Integer.class);
        assertThat(integers).hasSize(4);
        for (Integer integer : integers) {
            assertThat(integer).isExactlyInstanceOf(Integer.class);
        }

        List<String> strings = new HibernateListBuilder().buildBySQL("select password from user", String.class);
        assertThat(strings).hasSize(4);
        for (String string : strings) {
            assertThat(string).isExactlyInstanceOf(String.class);
        }

        strings = new HibernateListBuilder().buildBySQL("select DISTINCT(password) from user", String.class);
        assertThat(strings).hasSize(3);
    }

    @Test
    public void testCountBySQL() {
        assertThat(new HibernateListBuilder().countBySQL("user")).isEqualTo(4);
    }

    @Test
    public void testCount() {
        assertThat(new HibernateListBuilder().count(User.class)).isEqualTo(4);
        assertThat(new HibernateListBuilder().addEqualFilter("password", "password3").count(User.class)).isEqualTo(2);
    }

    @Test
    public void testBuild() {
        assertThat(new HibernateListBuilder().build(User.class)).hasSize(4);
        assertThat(new HibernateListBuilder().addEqualFilter("password", "password1").build(User.class)).hasSize(1);
        assertThat(new HibernateListBuilder().addEqualFilter("password", "password3").build(User.class)).hasSize(2);
        assertThat(new HibernateListBuilder().addGroup("password").build(User.class)).hasSize(3);

        assertThat(new HibernateListBuilder().build("User")).hasSize(4);
        assertThat(new HibernateListBuilder().build("FROM User")).hasSize(4);
        assertThat(new HibernateListBuilder().build("SELECT password FROM User")).hasSize(4);
        assertThat(new HibernateListBuilder().build("SELECT DISTINCT password FROM User")).hasSize(3);
        assertThat(new HibernateListBuilder().build("DISTINCT password FROM User")).hasSize(3);
    }

    @Test
    public void testGetFirstItem() {
        assertThat(new HibernateListBuilder().getFirstItem(User.class).getId()).isEqualTo(1);
        assertThat(new HibernateListBuilder().addOrder("id", false).getFirstItem(User.class).getId()).isEqualTo(4);
        assertThat(new HibernateListBuilder().addOrder("id", true).addEqualFilter("password", "password3")
                .getFirstItem(User.class).getId()).isEqualTo(3);

        assertThat(new HibernateListBuilder().getFirstItem("User")).isNotNull()
                .isExactlyInstanceOf(User.class);
        assertThat(((User) new HibernateListBuilder().getFirstItem("User")).getUsername()).isEqualTo("user1");
        assertThat(((User) new HibernateListBuilder().addOrder("id", false).getFirstItem("User")).getUsername())
                .isEqualTo("user4");
        assertThat(((User) new HibernateListBuilder().addEqualFilter("password", "password3").getFirstItem("User"))
                .getUsername()).isEqualTo("user3");
        assertThat(new HibernateListBuilder().getFirstItem("FROM User")).isNotNull()
                .isExactlyInstanceOf(User.class);
        assertThat(new HibernateListBuilder().getFirstItem("SELECT username FROM User")).isEqualTo("user1");
    }
}
