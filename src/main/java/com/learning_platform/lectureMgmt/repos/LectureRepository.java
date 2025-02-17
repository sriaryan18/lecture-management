package com.learning_platform.lectureMgmt.repos;

import com.learning_platform.lectureMgmt.models.LectureModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<LectureModel,String> {

    Optional<LectureModel> findById(String id);
    Optional<List<LectureModel>> findByInstructorId(String instructorId);
}
