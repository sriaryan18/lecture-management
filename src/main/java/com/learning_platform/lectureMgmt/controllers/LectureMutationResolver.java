package com.learning_platform.lectureMgmt.controllers;


import com.learning_platform.lectureMgmt.models.LectureModel;
import com.learning_platform.lectureMgmt.repos.LectureRepository;
import com.learning_platform.lectureMgmt.services.graphqlResolver.LectureMutationResolverService;
import com.learning_platform.lectureMgmt.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Controller
public class LectureMutationResolver {

    @Autowired
    LectureMutationResolverService lectureMutationResolverService;

    @MutationMapping
    public LectureModel createLecture(
            @Argument String instructorId,
            @Argument String classroomId,
            @Argument List<String> topics,
            @Argument String notes,
            @Argument List<String> testIds
    ) {
        LectureModel lectureModel = LectureModel.builder()
                .createdAt( Instant.now())
                .classroomId(classroomId)
                .instructorId(instructorId)
                .topics(topics)
                .notes(notes)
                .build();


        return lectureMutationResolverService.createLecture(lectureModel);
    }


    @MutationMapping
    public LectureModel updateTopics(@Argument String lectureId ,
                                     @Argument  List<String> topics){
        return lectureMutationResolverService.updateTopics(lectureId,topics);
    }


}
