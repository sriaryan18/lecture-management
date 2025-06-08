package com.learning_platform.lectureMgmt.controllers;

import com.learning_platform.lectureMgmt.models.ClassroomModel;
import com.learning_platform.lectureMgmt.models.LectureModel;
import com.learning_platform.lectureMgmt.models.StudentNotesModel;
import com.learning_platform.lectureMgmt.services.graphqlResolver.queries.ClassroomQueryResolver;
import com.learning_platform.lectureMgmt.services.graphqlResolver.queries.LectureQueryResolverService;
import com.learning_platform.lectureMgmt.services.graphqlResolver.queries.StudentNotesQueryResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryController {

    @Autowired
    LectureQueryResolverService lectureQueryResolverService;

    @Autowired
    ClassroomQueryResolver classroomQueryResolver;

    @Autowired
    StudentNotesQueryResolver studentNotesQueryResolver;

    @QueryMapping
    public List<LectureModel> listLectures() {
        return lectureQueryResolverService.listLectures();
    }

    @QueryMapping
    public LectureModel getLectureById(@Argument String id) {
        return lectureQueryResolverService.getLectureById(id);
    }

    @QueryMapping
    public List<LectureModel> getLectureByTopic(@Argument String topic) {
        return lectureQueryResolverService.getLecturesByTopic(topic);
    }

    @QueryMapping
    public List<ClassroomModel> getAllClassrooms() {
        return classroomQueryResolver.getAllClassrooms();
    }

    @QueryMapping
    public ClassroomModel getClassroomById(@Argument String classroomId) {
        return classroomQueryResolver.getClassroomById(classroomId);
    }

    @QueryMapping
    public List<ClassroomModel> getClassroomsByInstructorId(@Argument String instructorID) {
        return classroomQueryResolver.getClassroomsByInstructorId(instructorID);
    }

    @QueryMapping
    @PreAuthorize("@auth.isSelf(#studentId)")
    public List<ClassroomModel> getClassroomsByStudentIds(@Argument String studentId) {
        return classroomQueryResolver.getClassroomsByStudentIds(studentId);
    }

    @QueryMapping
    @PreAuthorize("@auth.isSelf(#studentId)")
    public StudentNotesModel getStudentNotes(@Argument String lectureId, @Argument String studentId, @Argument String classroomId) {
        return studentNotesQueryResolver.getStudentNotes(lectureId, studentId, classroomId);
    }

    @QueryMapping
    public String getInviteLink(@Argument String classroomId) {
        return classroomQueryResolver.getInviteLink(classroomId);
    }

}
