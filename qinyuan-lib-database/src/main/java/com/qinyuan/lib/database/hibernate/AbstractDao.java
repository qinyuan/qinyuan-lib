package com.qinyuan.lib.database.hibernate;

import com.qinyuan.lib.lang.reflect.GenericUtils;

import java.util.List;

public class AbstractDao<T> {
    protected Class getPersistClass() {
        return GenericUtils.getRealTypeOfGenericArgument(this.getClass());
    }

    @SuppressWarnings("unchecked")
    public List<T> getInstances() {
        return new HibernateListBuilder().build(getPersistClass());
    }

    @SuppressWarnings("unchecked")
    public int count() {
        return new HibernateListBuilder().count(getPersistClass());
    }

    @SuppressWarnings("unchecked")
    public List<T> getInstances(int firstResult, int maxResults) {
        return new HibernateListBuilder().limit(firstResult, maxResults).build(getPersistClass());
    }

    @SuppressWarnings("unchecked")
    public T getFirstInstance() {
        return (T) new HibernateListBuilder().getFirstItem(getPersistClass());
    }

    @SuppressWarnings("unchecked")
    public T getInstance(Integer id) {
        return (T) HibernateUtils.get(getPersistClass(), id);
    }

    public void delete(Integer id) {
        HibernateDeleter.deleteById(getPersistClass(), id);
    }
}
