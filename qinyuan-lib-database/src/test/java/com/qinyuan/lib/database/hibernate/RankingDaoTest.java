package com.qinyuan.lib.database.hibernate;

import com.qinyuan.lib.database.RankingImpl;
import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test RankingDao
 * Created by qinyuan on 15-3-21.
 */
public class RankingDaoTest extends DatabaseTestCase {
    private RankingDao dao = new RankingDao();

    @Test
    public void testGetMaxRanking() {
        assertThat(dao.getMaxRanking(RankingImpl.class)).isEqualTo(4);
    }

    @Test
    public void testGetFirstInstance() {
        RankingImpl first = dao.getFirstInstance(RankingImpl.class);
        assertThat(first.getId()).isEqualTo(4);
    }

    @Test
    public void testGetPrevious() {
        RankingImpl previous = dao.getPrevious(getRanking(1));
        assertThat(previous.getId()).isEqualTo(2);
        assertThat(dao.getPrevious(getRanking(4))).isNull();
    }

    @Test
    public void testGetInstanceByRankIndex() {
        RankingImpl instance = dao.getInstanceByRankIndex(RankingImpl.class, 3);
        assertThat(instance.getId()).isEqualTo(2);

        instance = dao.getInstanceByRankIndex(RankingImpl.class, 1);
        assertThat(instance.getId()).isEqualTo(4);

        instance = dao.getInstanceByRankIndex(RankingImpl.class, 5);
        assertThat(instance).isNull();
    }

    @Test
    public void testGetRankIndex() {
        assertThat(dao.getRankIndex(getRanking(4))).isEqualTo(1);
        assertThat(dao.getRankIndex(getRanking(3))).isEqualTo(2);
        assertThat(dao.getRankIndex(getRanking(2))).isEqualTo(3);
        assertThat(dao.getRankIndex(getRanking(1))).isEqualTo(4);
    }

    @Test
    public void testRankTo() {
        dao.rankTo(RankingImpl.class, 4, 1);
        assertThat(getRanking(4).getRanking()).isEqualTo(1);
        assertThat(dao.getRankIndex(getRanking(4))).isEqualTo(1);

        dao.rankTo(RankingImpl.class, 4, 2);
        assertThat(getRanking(4).getRanking()).isEqualTo(2);
        assertThat(dao.getRankIndex(getRanking(4))).isEqualTo(2);
        assertThat(getRanking(3).getRanking()).isEqualTo(1);

        dao.rankTo(RankingImpl.class, 4, 4);
        assertThat(getRanking(4).getRanking()).isEqualTo(4);
        assertThat(dao.getRankIndex(getRanking(4))).isEqualTo(4);
        assertThat(getRanking(3).getRanking()).isEqualTo(1);
        assertThat(getRanking(2).getRanking()).isEqualTo(2);
        assertThat(getRanking(1).getRanking()).isEqualTo(3);

        dao.rankTo(RankingImpl.class, 4, 14);
        assertThat(getRanking(4).getRanking()).isEqualTo(4);

        dao.rankTo(RankingImpl.class, 4, 3);
        assertThat(getRanking(4).getRanking()).isEqualTo(3);
        assertThat(getRanking(3).getRanking()).isEqualTo(1);
        assertThat(getRanking(2).getRanking()).isEqualTo(2);
        assertThat(getRanking(1).getRanking()).isEqualTo(4);


        dao.rankTo(RankingImpl.class, 4, 14);
        assertThat(getRanking(4).getRanking()).isEqualTo(4);
        assertThat(getRanking(1).getRanking()).isEqualTo(3);
    }

    private RankingImpl getRanking(int id) {
        return HibernateUtils.get(RankingImpl.class, id);
    }
}
