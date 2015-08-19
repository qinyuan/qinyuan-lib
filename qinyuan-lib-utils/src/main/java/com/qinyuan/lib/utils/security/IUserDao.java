package com.qinyuan.lib.utils.security;

/**
 * Interface of user dao
 * Created by qinyuan on 15-4-22.
 */
public interface IUserDao {
    User getInstanceByName(String username);

    Integer getIdByName(String username);
}
