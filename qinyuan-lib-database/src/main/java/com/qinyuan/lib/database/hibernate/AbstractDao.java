package com.qinyuan.lib.database.hibernate;

import com.qinyuan.lib.lang.reflect.GenericUtils;

import java.util.List;

public class AbstractDao<T> {
    protected final Class getPersistClass() {
        return GenericUtils.getRealTypeOfGenericArgument(this.getClass());
    }

    public List<T> getInstances() {
        return getInstances(-1, -1);
    }

    public int count() {
        return getListBuilder().count(getPersistClass());
    }

    @SuppressWarnings("unchecked")
    public List<T> getInstances(int firstResult, int maxResults) {
        return getListBuilder().limit(firstResult, maxResults).build(getPersistClass());
    }

    @SuppressWarnings("unchecked")
    public T getFirstInstance() {
        return (T) getListBuilder().getFirstItem(getPersistClass());
    }

    @SuppressWarnings("unchecked")
    public T getInstance(Integer id) {
        return (T) HibernateUtils.get(getPersistClass(), id);
    }

    public void delete(Integer id) {
        HibernateDeleter.deleteById(getPersistClass(), id);
    }

    protected HibernateListBuilder getListBuilder() {
        return new HibernateListBuilder();
    }
}
