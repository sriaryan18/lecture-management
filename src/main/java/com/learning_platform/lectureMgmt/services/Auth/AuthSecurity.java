package com.learning_platform.lectureMgmt.services.Auth;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Component("auth")
@Slf4j
public class AuthSecurity {

    public boolean isSelf(String studentId) {
        log.info("Checking if user is self: {}", studentId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return false;

        Object principal = authentication.getPrincipal();
        if (principal instanceof Map<?, ?> map) {
            log.info("User map: {}", map);
            return studentId.equals(map.get("username")); // or whatever claim you set
        }

        return false;
    }
}
