package com.learning_platform.lectureMgmt.services.Auth;

import org.springframework.stereotype.Component;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserPrincipal;

@Component("auth")
public class AuthSecurity {

    public boolean isSelf(String studentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return false;

        Object principal = authentication.getPrincipal();
        if (principal instanceof Map<?, ?> map) {
            return studentId.equals(map.get("username")); // or whatever claim you set
        }

        return false;
    }
}
