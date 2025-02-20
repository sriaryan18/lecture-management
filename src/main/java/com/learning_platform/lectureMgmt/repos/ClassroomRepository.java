package com.learning_platform.lectureMgmt.repos;

import com.learning_platform.lectureMgmt.models.ClassroomModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<ClassroomModel,String> {
}
