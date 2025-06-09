package com.learning_platform.lectureMgmt.controllers;

import com.learning_platform.lectureMgmt.exceptions.ResourceNotFoundException;
import com.learning_platform.lectureMgmt.models.ClassroomModel;
import com.learning_platform.lectureMgmt.models.InviteLink;
import com.learning_platform.lectureMgmt.models.LectureModel;
import com.learning_platform.lectureMgmt.models.StudentNotesModel;
import com.learning_platform.lectureMgmt.services.graphqlResolver.mutations.ClassroomMutationResolver;
import com.learning_platform.lectureMgmt.services.graphqlResolver.mutations.LectureMutationResolverService;
import com.learning_platform.lectureMgmt.services.graphqlResolver.mutations.StudentNotesMutationResolver;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.Instant;
import java.util.List;

@Controller
@CrossOrigin(origins = "*")
public class MutationController {

    private final LectureMutationResolverService lectureMutationResolverService;
    private final ClassroomMutationResolver classroomMutationResolver;
    private final StudentNotesMutationResolver studentNotesMutationResolver;

    public MutationController(
            LectureMutationResolverService lectureMutationResolverService,
            ClassroomMutationResolver classroomMutationResolver,
            StudentNotesMutationResolver studentNotesMutationResolver) {
        this.lectureMutationResolverService = lectureMutationResolverService;
        this.classroomMutationResolver = classroomMutationResolver;
        this.studentNotesMutationResolver = studentNotesMutationResolver;
    }

    @MutationMapping
    public LectureModel createLecture(
            @Argument String instructorId,
            @Argument String classroomId,
            @Argument List<String> topics,
            @Argument String notes,
            @Argument List<String> testIds,
            @Argument String lectureName,
            @Argument String lectureDescription) {
        LectureModel lectureModel = LectureModel.builder()
                .createdAt(Instant.now())
                .classroomId(classroomId)
                .instructorId(instructorId)
                .topics(topics)
                .notes(notes)
                .lectureName(lectureName)
                .lectureDescription(lectureDescription)
                .build();

        return lectureMutationResolverService.createLecture(lectureModel);
    }

    @MutationMapping
    public LectureModel updateTopics(@Argument String lectureId,
            @Argument List<String> topics) {
        return lectureMutationResolverService.updateTopics(lectureId, topics);
    }

    @MutationMapping
    @PreAuthorize("@auth.isSameOrganization(#organizationId)")
    public ClassroomModel createClassroom(@Argument String description, @Argument String classroomName,
            @Argument List<String> instructorIds, @Argument String organizationId) {
        return classroomMutationResolver.createClassroom(description, classroomName, instructorIds, organizationId);
    }

    @MutationMapping
    public ClassroomModel addStudentsInClassRoom(@Argument List<String> studentIds, @Argument String classroomId) {
        try {
            return classroomMutationResolver.addStudentsInClassroom(classroomId, studentIds);

        } catch (Exception e) {
            throw new ResourceNotFoundException(classroomId, "Classroom");
        }
    }

    @Deprecated
    @MutationMapping
    public ClassroomModel addLectureInClassRoom(@Argument String lectureId, @Argument String classroomId) {
        return classroomMutationResolver.addLectureInClassRoom(lectureId, classroomId);
    }

    @MutationMapping
    public ClassroomModel addInstructor(@Argument String instructorId, @Argument String classroomId) {
        return classroomMutationResolver.addInstructor(instructorId, classroomId);
    }

    @MutationMapping
    @PreAuthorize("@auth.self(#studentId)")
    public StudentNotesModel updateStudentNotes(@Argument String lectureId, @Argument String studentId,
            @Argument String classroomId, @Argument String notes) {
        return studentNotesMutationResolver.updateStudentNotes(lectureId, studentId, classroomId, notes);
    }

    @MutationMapping
    public InviteLink createInviteLink(@Argument String classroomId, @Argument String expiry) {
        String inviteString = classroomMutationResolver.createInviteLink(classroomId, expiry);
        return new InviteLink(inviteString, expiry);
    }

    @MutationMapping
    @PreAuthorize("@auth.isSelf(#studentId)")
    public List<ClassroomModel> joinClassroom(@Argument String inviteLink, @Argument String studentId) {
        return classroomMutationResolver.joinClassroom(inviteLink, studentId);
    }

    @MutationMapping
    @PreAuthorize("@auth.isSameOrganization(#organizationId)")
    public List<ClassroomModel> joinClassroomByCode(@Argument String classroomCode, @Argument String studentId, @Argument String organizationId) {
        return classroomMutationResolver.joinClassroomByCode(classroomCode, studentId, organizationId);
    }

}
