package com.learning_platform.lectureMgmt.repos;

import com.learning_platform.lectureMgmt.models.ClassroomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<ClassroomModel,String> {


    @Query(value = "SELECT * FROM classroom cr WHERE :instructor_id = ANY(cr.instructor_ids)", nativeQuery = true)
    Optional<ClassroomModel> findClassroomsByInstructorId(@Param("instructor_ids") String instructorId);

    @Query(value = "SELECT * FROM classroom cr where :studentId = ANY(cr.student_ids)",nativeQuery = true)
    Optional<List<ClassroomModel>> findClassroomByStudentId(@Param("studentId") String studentId);

}
