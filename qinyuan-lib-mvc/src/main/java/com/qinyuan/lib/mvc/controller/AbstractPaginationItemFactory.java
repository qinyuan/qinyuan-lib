package com.qinyuan.lib.mvc.controller;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.lang.reflect.GenericUtils;

import java.util.List;

public abstract class AbstractPaginationItemFactory<T> implements PaginationItemFactory<T> {
    @Override
    public int getCount() {
        return getListBuilder().count(GenericUtils.getRealTypeOfGenericArgument(this.getClass()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getInstances(int firstResult, int maxResults) {
        return (List) getListBuilder().limit(firstResult, maxResults).build(
                GenericUtils.getRealTypeOfGenericArgument(this.getClass()));
    }

    public List<T> getInstances() {
        return getInstances(-1, -1);
    }

    public T getFirstInstance() {
        List<T> activities = getInstances();
        return activities.size() == 0 ? null : activities.get(0);
    }

    protected HibernateListBuilder getListBuilder() {
        return new HibernateListBuilder();
    }
}
