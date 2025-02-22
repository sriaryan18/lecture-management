package com.learning_platform.lectureMgmt.services.graphqlResolver;


import com.learning_platform.lectureMgmt.models.ClassroomModel;
import com.learning_platform.lectureMgmt.repos.ClassroomRepository;
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

}
