package com.learning_platform.lectureMgmt.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Lecture {

    @PreAuthorize("hasRole('PRIVATE')") //
    @GetMapping("/")
    public String test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("AUTHENTICATION" + authentication.toString());
        return "TEST SUCCESSFULL" + authentication.toString();
    }
}
