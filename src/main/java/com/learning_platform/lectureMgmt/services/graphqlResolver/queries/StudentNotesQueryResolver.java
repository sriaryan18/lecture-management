package com.learning_platform.lectureMgmt.services.graphqlResolver.queries;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learning_platform.lectureMgmt.exceptions.NoNotesFound;
import com.learning_platform.lectureMgmt.exceptions.ResourceNotFoundException;
import com.learning_platform.lectureMgmt.models.StudentNotesModel;
import com.learning_platform.lectureMgmt.repos.StudentNotesRepository;

@Service
public class StudentNotesQueryResolver {

    private final StudentNotesRepository studentNotesRepository;

    StudentNotesQueryResolver(StudentNotesRepository studentNotesRepository){
        this.studentNotesRepository = studentNotesRepository;
    }

    public StudentNotesModel getStudentNotes(String lectureId, String studentId, String classroomId){
        Optional<StudentNotesModel> studentNotesModel = studentNotesRepository.findByLectureIdAndStudentIdAndClassroomId(lectureId, studentId, classroomId);
        if(studentNotesModel.isPresent()){
            return studentNotesModel.get();
        }else{
                throw new NoNotesFound(lectureId, studentId);
        }
    }
}
