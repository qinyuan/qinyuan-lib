package com.qinyuan.lib.mvc.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class SecurityUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(SecurityUtils.class);

    public static String getUsername() {
        UserDetails userDetails = getUserDetails();
        return userDetails == null ? null : userDetails.getUsername();
    }

    public static UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object userDetails = authentication.getPrincipal();
        if (userDetails == null || userDetails.equals("anonymousUser")) {
            return null;
        } else if (userDetails instanceof UserDetails) {
            return (UserDetails) userDetails;
        } else {
            LOGGER.info("userDetails is String, whose value is {}", userDetails);
            return null;
        }
    }

    public static List<String> getAuthorities() {
        List<String> authorities = new ArrayList<>();
        UserDetails userDetails = getUserDetails();
        if (userDetails == null) {
            return authorities;
        }

        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }
        return authorities;
    }

    public static boolean hasAuthority(String roleName) {
        return getAuthorities().contains(roleName);
    }

    public static void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public static void login(HttpServletRequest request, AuthenticationManager authenticationManager, String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
    }
}
