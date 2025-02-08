package com.learning_platform.lectureMgmt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Lecture {

    @GetMapping("/")
    public String test(){
        return "TEST SUCCESSFULL";
    }
}
