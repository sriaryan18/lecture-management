package com.learning_platform.lectureMgmt.controllers;

import com.learning_platform.lectureMgmt.models.ClassroomModel;
import com.learning_platform.lectureMgmt.models.LectureModel;
import com.learning_platform.lectureMgmt.services.graphqlResolver.LectureQueryResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryController {

    @Autowired
    LectureQueryResolverService lectureQueryResolverService;

//    @PreAuthorize("hasRole('PRIVATE')") //
//    @GetMapping("/")
//    public String test(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("AUTHENTICATION" + authentication.toString());
//        return "TEST SUCCESSFULL" + authentication.toString();
//    }

    @QueryMapping
    public List<LectureModel> listLectures(){
        return lectureQueryResolverService.listLectures();
    }
    @QueryMapping
    public LectureModel getLectureById(@Argument String id){
        return lectureQueryResolverService.getLectureById(id);
    }

    @QueryMapping
    public List<LectureModel> getLectureByTopic(@Argument  String topic){
        return lectureQueryResolverService.getLecturesByTopic(topic);
    }


}
