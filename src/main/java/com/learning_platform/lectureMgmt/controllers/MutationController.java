package com.learning_platform.lectureMgmt.controllers;


import com.learning_platform.lectureMgmt.exceptions.ResourceNotFoundException;
import com.learning_platform.lectureMgmt.models.ClassroomModel;
import com.learning_platform.lectureMgmt.models.LectureModel;
import com.learning_platform.lectureMgmt.services.graphqlResolver.ClassroomMutationResolver;
import com.learning_platform.lectureMgmt.services.graphqlResolver.LectureMutationResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.List;

@Controller
public class MutationController {

    @Autowired
    LectureMutationResolverService lectureMutationResolverService;

    @Autowired
    ClassroomMutationResolver classroomMutationResolver;

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

    @MutationMapping
    public ClassroomModel createClassroom(@Argument String description){
        return classroomMutationResolver.createClassroom(description);
    }

    @MutationMapping
    public ClassroomModel addStudentsInClassRoom(@Argument List<String> studentIds,@Argument String classroomId){
        try {
            return classroomMutationResolver.addStudentsInClassroom(classroomId,studentIds);

        } catch (Exception e) {
            throw new ResourceNotFoundException(classroomId,"Classroom");
        }
    }

    @Deprecated
    @MutationMapping
    public ClassroomModel addLectureInClassRoom(@Argument String lectureId,@Argument String classroomId){
        return classroomMutationResolver.addLectureInClassRoom(lectureId,classroomId);
    }

    @MutationMapping
    public ClassroomModel addInstructor(@Argument String instructorId,@Argument String classroomId){
        return classroomMutationResolver.addInstructor(instructorId,classroomId);
    }



}
