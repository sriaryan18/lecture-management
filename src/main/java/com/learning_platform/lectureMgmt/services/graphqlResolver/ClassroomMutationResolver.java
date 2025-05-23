package com.learning_platform.lectureMgmt.services.graphqlResolver;


import com.learning_platform.lectureMgmt.exceptions.ResourceNotFoundException;
import com.learning_platform.lectureMgmt.models.ClassroomModel;
import com.learning_platform.lectureMgmt.models.LectureModel;
import com.learning_platform.lectureMgmt.repos.ClassroomRepository;
import com.learning_platform.lectureMgmt.repos.LectureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClassroomMutationResolver {

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    LectureRepository lectureRepository;

    public ClassroomModel createClassroom(String description){
        ClassroomModel entity = ClassroomModel.builder().description(description)
                .instructorIds(new ArrayList<>())
                .studentIds(new ArrayList<>())
                .createdAt(Instant.now())
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
        }else {
            throw new Exception("Classroom id not found");
        }
    }



    public ClassroomModel addLectureInClassRoom(String lectureId,String classroomId){
        Optional<ClassroomModel> optionalClassroomModel = classroomRepository.findById(classroomId);
        Optional<LectureModel> lectureModel = lectureRepository.findById(lectureId);
        if(optionalClassroomModel.isPresent() && lectureModel.isPresent()){
            ClassroomModel classroomModel = optionalClassroomModel.get();
            List<String> lectures =classroomModel.getLectures();
            if(lectures != null){
                lectures.stream().collect(Collectors.toSet()).add(lectureId);
            }else{
                lectures = new ArrayList<>();
                lectures.add(lectureId);
            }
            classroomModel.setLectures(lectures.stream().toList());
            return classroomRepository.save(optionalClassroomModel.get());
        }else{
            if(lectureModel.isPresent())
                throw new ResourceNotFoundException(classroomId,"classroom");
            else
                throw new ResourceNotFoundException(classroomId,"lecture");
        }
    }

    public ClassroomModel addInstructor(String instructorId,String classroomId){
        Optional<ClassroomModel> optionalClassroomModel = classroomRepository.findById(classroomId);
        if(optionalClassroomModel.isPresent()){
            ClassroomModel classroomModel = optionalClassroomModel.get();
            classroomModel.getInstructorIds().stream().collect(Collectors.toSet()).add(instructorId);
            return classroomRepository.save(classroomModel);
        }else {
            throw new ResourceNotFoundException(classroomId,"classroom");
        }
    }


}
