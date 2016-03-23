package com.qinyuan.lib.image;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test ImageMapDao
 * Created by qinyuan on 15-6-27.
 */
public class ImageMapDaoTest extends DatabaseTestCase {
    private ImageMapDao dao = new ImageMapDao("testType");
    private ImageMapDao dao2 = new ImageMapDao("testType2");

    @Test
    public void testAdd() throws Exception {
        assertThat(dao.getInstances()).hasSize(1);
        dao.add(1, 1, 1, 1, 1, "href", "comment");
        assertThat(dao.getInstances()).hasSize(2);
    }

    @Test
    public void testGetInstance() {
        assertThat(dao2.getInstance(1)).isNull();

        dao2.add(8, 9, 10, 11, 12, "testHref", "testComment");
        assertThat(dao.getInstance(2)).isNull();

        dao.add(8, 9, 10, 11, 12, "testHref", "testComment");
        ImageMap map = dao.getInstance(3);
        assertThat(map.getId()).isEqualTo(3);
        assertThat(map.getRelateId()).isEqualTo(8);
        assertThat(map.getxStart()).isEqualTo(9);
        assertThat(map.getyStart()).isEqualTo(10);
        assertThat(map.getxEnd()).isEqualTo(11);
        assertThat(map.getyEnd()).isEqualTo(12);
        assertThat(map.getHref()).isEqualTo("testHref");
        assertThat(map.getComment()).isEqualTo("testComment");

        assertThat(dao.getInstance(4)).isNull();
    }

    @Test
    public void testUpdate() {
        ImageMap map = dao.getInstance(1);
        assertThat(map.getHref()).isEqualTo("href1");

        dao2.update(1, "new_href", "new_comment");
        assertThat(dao.getInstance(1).getHref()).isEqualTo("href1");

        dao.update(1, "new_href", "new_comment");
        assertThat(dao.getInstance(1).getHref()).isEqualTo("new_href");
    }

    @Test
    public void testDelete() {
        dao2.delete(1);
        assertThat(dao.getInstances()).isEqualTo(1);

        dao.delete(1);
        assertThat(dao.getInstances()).isEmpty();
    }

    @Test
    public void testDeleteByRelateId() {
        dao.add(8, 8, 9, 10, 11, "href2", "comment2");
        dao.add(2, 8, 9, 10, 11, "href3", "comment3");
        dao2.add(8, 8, 9, 10, 11, "href4", "comment4");

        assertThat(dao.getInstances()).hasSize(3);
        assertThat(dao2.getInstances()).hasSize(1);

        dao.deleteByRelateId(8);
        assertThat(dao.getInstances()).hasSize(1);
        assertThat(dao2.getInstances()).hasSize(1);
    }

    @Test
    public void testGetInstancesByRelateId() {
        assertThat(dao.getInstancesByRelateId(8)).hasSize(1);

        dao.add(8, 8, 9, 10, 11, "href2", "comment2");
        assertThat(dao.getInstancesByRelateId(8)).hasSize(2);

        dao.add(2, 8, 9, 10, 11, "href3", "comment3");
        assertThat(dao.getInstancesByRelateId(8)).hasSize(2);

        dao2.add(8, 8, 9, 10, 11, "href4", "comment4");
        assertThat(dao.getInstancesByRelateId(8)).hasSize(2);
    }

    @Test
    public void testGetInstances() {
        assertThat(dao.getInstances()).hasSize(1);

        dao.add(8, 8, 9, 10, 11, "href2", "comment2");
        assertThat(dao.getInstances()).hasSize(2);

        dao.add(2, 8, 9, 10, 11, "href3", "comment3");
        assertThat(dao.getInstances()).hasSize(3);

        dao2.add(8, 8, 9, 10, 11, "href4", "comment4");
        assertThat(dao.getInstances()).hasSize(3);
    }

    @Test
    public void testGetInstancesAndGroupByRelateId() {
        dao.add(8, 8, 9, 10, 11, "href2", "comment2");
        dao.add(2, 8, 9, 10, 11, "href3", "comment3");
        dao2.add(8, 8, 9, 10, 11, "href4", "comment4");

        Map<Integer, List<ImageMap>> map = dao.getInstancesAndGroupByRelateId();
        assertThat(map).hasSize(2);
        assertThat(map.get(2)).hasSize(1);
        assertThat(map.get(8)).hasSize(2);
    }
}
