package com.learning_platform.lectureMgmt.services.graphqlResolver.mutations;

import java.time.Instant;
import java.util.Optional;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Component;

import com.learning_platform.lectureMgmt.models.StudentNotesModel;
import com.learning_platform.lectureMgmt.repos.StudentNotesRepository;

@Component
public class StudentNotesMutationResolver {

    private final StudentNotesRepository studentNotesRepository;

    StudentNotesMutationResolver(StudentNotesRepository studentNotesRepository) {
        this.studentNotesRepository = studentNotesRepository;
    }

    public StudentNotesModel updateStudentNotes(@Argument String lectureId, @Argument String studentId,
            @Argument String classroomId, @Argument String notes) {
        Optional<StudentNotesModel> studentNotesModel = studentNotesRepository
                .findByLectureIdAndStudentIdAndClassroomId(lectureId, studentId, classroomId);
        if (studentNotesModel.isPresent()) {
            StudentNotesModel updatedStudentNotesModel = studentNotesModel.get();
            updatedStudentNotesModel.setNotes(notes);
            updatedStudentNotesModel.setUpdatedAt(Instant.now());
            
            return studentNotesRepository.save(updatedStudentNotesModel);
        } else {
            StudentNotesModel newStudentNotesModel = StudentNotesModel.builder()
                    .lectureId(lectureId)
                    .studentId(studentId)
                    .notes(notes)
                    .classroomId(classroomId)
                    .updatedAt(Instant.now())
                    .createdAt(Instant.now())
                    .build();
            return studentNotesRepository.save(newStudentNotesModel);
        }

    }
}
