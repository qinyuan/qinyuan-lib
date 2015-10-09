package com.qinyuan.lib.mvc.controller;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.Ranking;

public class RankingPaginationItemFactory<T extends Ranking> extends AbstractPaginationItemFactory<T> {
    @Override
    protected HibernateListBuilder getListBuilder() {
        return super.getListBuilder().addOrder("ranking", true);
    }
}
