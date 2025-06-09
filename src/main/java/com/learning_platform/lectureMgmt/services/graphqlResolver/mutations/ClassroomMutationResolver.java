package com.learning_platform.lectureMgmt.services.graphqlResolver.mutations;

import com.learning_platform.lectureMgmt.exceptions.InviteLinkExpired;
import com.learning_platform.lectureMgmt.exceptions.ResourceNotFoundException;
import com.learning_platform.lectureMgmt.models.ClassroomModel;
import com.learning_platform.lectureMgmt.models.LectureModel;
import com.learning_platform.lectureMgmt.repos.ClassroomRepository;
import com.learning_platform.lectureMgmt.repos.LectureRepository;
import com.learning_platform.lectureMgmt.services.graphqlResolver.queries.ClassroomQueryResolver;
import com.learning_platform.lectureMgmt.utils.ClassroomUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClassroomMutationResolver {

    private final ClassroomRepository classroomRepository;
    private final LectureRepository lectureRepository;
    private final ClassroomQueryResolver classroomQueryResolver;

    public ClassroomMutationResolver(ClassroomRepository classroomRepository, LectureRepository lectureRepository,
            ClassroomQueryResolver classroomQueryResolver) {
        this.classroomRepository = classroomRepository;
        this.lectureRepository = lectureRepository;
        this.classroomQueryResolver = classroomQueryResolver;
    }

    public ClassroomModel createClassroom(String description, String classroomName, List<String> instructorIds,
            String organizationId) {
        if (instructorIds == null) {
            instructorIds = new ArrayList<>();
        }
        String classroomCode = ClassroomUtils.generateClassroomCode(organizationId);
        ClassroomModel entity = ClassroomModel.builder().description(description)
                .classroomName(classroomName)
                .instructorIds(instructorIds)
                .studentIds(new ArrayList<>())
                .createdAt(Instant.now())
                .organizationId(organizationId)
                .classroomCode(classroomCode)
                .build();
        log.info("Saving classroom >> {}", entity.getId());
        return classroomRepository.save(entity);
    }

    public ClassroomModel addStudentsInClassroom(String classroomId, List<String> studentIds) throws Exception {
        Optional<ClassroomModel> model = classroomRepository.findById(classroomId);
        if (model.isPresent()) {
            Set<String> students = model.get().getStudentIds().stream().collect(Collectors.toSet());
            studentIds.stream().forEach(students::add);
            ClassroomModel classroomModel = model.get();
            classroomModel.setStudentIds(students.stream().toList());
            return classroomRepository.save(classroomModel);
        } else {
            throw new Exception("Classroom id not found");
        }
    }

    public ClassroomModel addLectureInClassRoom(String lectureId, String classroomId) {
        Optional<ClassroomModel> optionalClassroomModel = classroomRepository.findById(classroomId);
        Optional<LectureModel> lectureModel = lectureRepository.findById(lectureId);
        if (optionalClassroomModel.isPresent() && lectureModel.isPresent()) {
            ClassroomModel classroomModel = optionalClassroomModel.get();
            List<String> lectures = classroomModel.getLectures();
            if (lectures != null) {
                lectures.stream().collect(Collectors.toSet()).add(lectureId);
            } else {
                lectures = new ArrayList<>();
                lectures.add(lectureId);
            }
            classroomModel.setLectures(lectures.stream().toList());
            return classroomRepository.save(optionalClassroomModel.get());
        } else {
            if (lectureModel.isPresent())
                throw new ResourceNotFoundException(classroomId, "classroom");
            else
                throw new ResourceNotFoundException(classroomId, "lecture");
        }
    }

    public ClassroomModel addInstructor(String instructorId, String classroomId) {
        Optional<ClassroomModel> optionalClassroomModel = classroomRepository.findById(classroomId);
        if (optionalClassroomModel.isPresent()) {
            ClassroomModel classroomModel = optionalClassroomModel.get();
            classroomModel.getInstructorIds().stream().collect(Collectors.toSet()).add(instructorId);
            return classroomRepository.save(classroomModel);
        } else {
            throw new ResourceNotFoundException(classroomId, "classroom");
        }
    }

    public String createInviteLink(String classroomId, String expiry) {
        Optional<ClassroomModel> classroomModel = classroomRepository.findById(classroomId);
        if (classroomModel.isEmpty()) {
            throw new ResourceNotFoundException(classroomId, "classroom");
        }
        // TODO: add client type and organization id to the invite link
        String inviteLink = ClassroomUtils.generateInviteLink(classroomId, expiry);
        classroomModel.get().setInviteLink(inviteLink);
        classroomModel.get().setInviteLinkExpiry(Instant.parse(expiry));
        return classroomRepository.save(classroomModel.get()).getInviteLink();
    }

    private void addStudentToClassroom(ClassroomModel classroom, String studentId) {
        Set<String> students = classroom.getStudentIds().stream().collect(Collectors.toSet());
        students.add(studentId);
        classroom.setStudentIds(students.stream().toList());
        classroomRepository.save(classroom);
    }

    public List<ClassroomModel> joinClassroom(String inviteLink, String studentId) {
        Map<String, String> decodedInviteLink = ClassroomUtils.decodeInviteLink(inviteLink);
        String classroomId = decodedInviteLink.get("classroomId");
        String expiry = decodedInviteLink.get("expiry");
        if (Instant.parse(expiry).isBefore(Instant.now())) {
            throw new InviteLinkExpired("Invite link expired");
        }
        Optional<ClassroomModel> classroomModel = classroomRepository.findById(classroomId);
        if (classroomModel.isEmpty()) {
            throw new ResourceNotFoundException(classroomId, "classroom");
        }
        ClassroomModel classroom = classroomModel.get();
        this.addStudentToClassroom(classroom, studentId);
        return classroomQueryResolver.getClassroomsByStudentIds(studentId);

    }

    public List<ClassroomModel> joinClassroomByCode(String classroomCode, String studentId, String organizationId) {
        Optional<ClassroomModel> classroomModel = classroomRepository
                .findByClassroomCodeAndOrganizationId(classroomCode, organizationId);
        if (classroomModel.isEmpty()) {
            throw new ResourceNotFoundException(classroomCode, "classroom");
        }
        ClassroomModel classroom = classroomModel.get();
        this.addStudentToClassroom(classroom, studentId);
        return classroomQueryResolver.getClassroomsByStudentIds(studentId);
    }

}
