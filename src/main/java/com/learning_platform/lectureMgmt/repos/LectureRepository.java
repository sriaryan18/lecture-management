package com.learning_platform.lectureMgmt.repos;

import com.learning_platform.lectureMgmt.models.LectureModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<LectureModel,String> {

    Optional<LectureModel> findById(String id);
    Optional<List<LectureModel>> findByInstructorId(String instructorId);

    @Query(value = "SELECT * FROM lecture_management lm WHERE array_to_string(lm.topics,',') like %:topic%", nativeQuery = true)
    List<LectureModel> findByTopic(@Param("topic") String topic);

//List<LectureModel> findByTopicsContaining(String topic);


}
