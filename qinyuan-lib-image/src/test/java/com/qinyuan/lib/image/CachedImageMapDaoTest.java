package com.qinyuan.lib.image;

import com.qinyuan.lib.database.test.DatabaseInitializer;
import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CachedImageMapDaoTest extends DatabaseTestCase {
    private CachedImageMapDao dao = new CachedImageMapDao("testType");
    private CachedImageMapDao dao2 = new CachedImageMapDao("testType2");

    @Test
    public void testGetInstance() throws Exception {
        assertThat(dao.getInstance(1).getHref()).isEqualTo("href1");

        dao.add(1, 1, 1, 1, 1, "href2", "comment2");

        assertThat(dao.getInstance(2).getHref()).isEqualTo("href2");

        dao2.add(1, 1, 1, 1, 1, "href3", "comment3");

        assertThat(dao.getInstance(3)).isNull();

        new DatabaseInitializer().init();
        assertThat(dao.getInstance(2).getHref()).isEqualTo("href2");

        dao.clearCache();
        assertThat(dao.getInstance(2)).isNull();

    }

    @Test
    public void testGetInstances() throws Exception {
        assertThat(dao.getInstances()).hasSize(1);
        assertThat(dao2.getInstances()).isEmpty();

        dao.add(1, 1, 1, 1, 1, "href1", "comment1");

        assertThat(dao.getInstances()).hasSize(2);
        assertThat(dao2.getInstances()).isEmpty();

        new DatabaseInitializer().init();

        assertThat(dao.getInstances()).hasSize(2);

        dao.clearCache();
        assertThat(dao.getInstances()).hasSize(1);
    }

    @Test
    public void testGetInstancesByRelateId() throws Exception {
        dao.add(8, 1, 1, 1, 1, "href2", "comment2");
        dao.add(1, 1, 1, 1, 1, "href3", "comment3");
        dao2.add(8, 1, 1, 1, 1, "href4", "comment4");

        assertThat(dao.getInstancesByRelateId(8)).hasSize(2);
        assertThat(dao.getInstancesByRelateId(1)).hasSize(1);

        new DatabaseInitializer().init();

        assertThat(dao.getInstancesByRelateId(8)).hasSize(2);
        assertThat(dao.getInstancesByRelateId(1)).hasSize(1);

        dao.clearCache();
        System.out.println(dao.getInstances().size());
        assertThat(dao.getInstancesByRelateId(8)).hasSize(1);
        assertThat(dao.getInstancesByRelateId(1)).isEmpty();
    }
}
