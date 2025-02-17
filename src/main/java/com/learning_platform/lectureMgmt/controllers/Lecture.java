package com.learning_platform.lectureMgmt.controllers;

import com.learning_platform.lectureMgmt.models.LectureModel;
import com.learning_platform.lectureMgmt.services.graphqlResolver.LectureResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Lecture {

    @Autowired
    LectureResolver lectureResolver;

    @PreAuthorize("hasRole('PRIVATE')") //
    @GetMapping("/")
    public String test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("AUTHENTICATION" + authentication.toString());
        return "TEST SUCCESSFULL" + authentication.toString();
    }

    @QueryMapping
    public List<LectureModel> listLectures(){

        return lectureResolver.listLectures();
    }
    @QueryMapping
    public LectureModel getLectureById(@Argument String id){
        return lectureResolver.getLectureById(id);
    }

}
