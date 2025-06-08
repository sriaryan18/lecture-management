package com.learning_platform.lectureMgmt.services.graphqlResolver.queries;

import com.learning_platform.lectureMgmt.exceptions.ResourceNotFoundException;
import com.learning_platform.lectureMgmt.models.ClassroomModel;
import com.learning_platform.lectureMgmt.repos.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomQueryResolver {

    @Autowired
    ClassroomRepository classroomRepository;

    public List<ClassroomModel> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public ClassroomModel getClassroomById(String classroomId) {
        return classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException(classroomId, "classroom"));
    }

    public List<ClassroomModel> getClassroomsByInstructorId(String instrcutorId) {
        return classroomRepository
                .findClassroomsByInstructorId(instrcutorId)
                .orElseThrow(() -> new ResourceNotFoundException(instrcutorId, "instrcutorId"));
    }

    public List<ClassroomModel> getClassroomsByStudentIds(String studentId) {
        return classroomRepository
                .findClassroomByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(studentId, "studentId"));
    }

    public String getInviteLink(String classroomId) {
        return classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException(classroomId, "classroomId"))
                .getInviteLink();
    }

}
