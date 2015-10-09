package com.qinyuan.lib.database.test;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.lang.test.TestFileUtils;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseInitializerTest {
    @Test
    public void testInit() {
        new DatabaseInitializer().init();
        List<Object[]> instances = new HibernateListBuilder().buildBySQL("SELECT * FROM test_table1");
        assertThat(instances).hasSize(3);
        assertThat(instances.get(0)[0]).isEqualTo(1);
        assertThat(instances.get(0)[1]).isEqualTo("aaa");
        assertThat(instances.get(0)[2]).isEqualTo("bbb");
        assertThat(instances.get(1)[0]).isEqualTo(2);
        assertThat(instances.get(1)[1]).isEqualTo("bbb");
        assertThat(instances.get(1)[2]).isEqualTo("ccc");
        assertThat(instances.get(2)[0]).isEqualTo(3);
        assertThat(instances.get(2)[1]).isEqualTo("张三");
        assertThat(instances.get(2)[2]).isEqualTo("李四");
    }

    @Test
    public void testInit2() {
        new DatabaseInitializer().init(TestFileUtils.getFile("init2.sql"));

        List<Object[]> instances = new HibernateListBuilder().buildBySQL("SELECT * FROM hello_world");
        assertThat(instances).hasSize(3);
    }
}
