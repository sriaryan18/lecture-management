package com.learning_platform.lectureMgmt.repos;

import com.learning_platform.lectureMgmt.models.StudentNotesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentNotesRepository extends JpaRepository<StudentNotesModel,String> {

    Optional<StudentNotesModel> findByLectureIdAndStudentId(String lectureId, String studentId);

    Optional<StudentNotesModel> findByLectureIdAndClassroomId(String lectureId, String classroomId);

    Optional<StudentNotesModel> findByStudentIdAndClassroomId(String studentId, String classroomId);

    Optional<StudentNotesModel> findByLectureIdAndStudentIdAndClassroomId(String lectureId, String studentId, String classroomId);

    Optional<StudentNotesModel> findByStudentIdAndClassroomIdAndLectureId(String studentId, String classroomId, String lectureId);
}
