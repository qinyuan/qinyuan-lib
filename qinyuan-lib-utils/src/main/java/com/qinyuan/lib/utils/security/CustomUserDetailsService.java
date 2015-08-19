package com.qinyuan.lib.utils.security;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;

/**
 * Class to provide custom authentication
 * Created by qinyuan on 15-3-5.
 */
public class CustomUserDetailsService implements UserDetailsService {
    private final static String invalidPassword = RandomStringUtils.random(300);
    private final static Collection<GrantedAuthority> emptyGrantedAuthority = new HashSet<>();
    private IUserDao userDao;

    public CustomUserDetailsService(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.qinyuan.lib.utils.security.User user = userDao.getInstanceByName(s);
        if (user == null) {
            return new User(s, invalidPassword, emptyGrantedAuthority);
        }

        Collection<GrantedAuthority> authorities = new HashSet<>();
        if (user.getRole() != null) {
            for (String role : user.getRole().split(",")) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
