package com.learning_platform.lectureMgmt.models;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Builder;

@Data
@Entity
@Table(name = "student_notes")
@Builder
public class StudentNotesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String classroomId;

    private String studentId;

    private String lectureId;

    private String notes;

    private Instant createdAt;

    private Instant updatedAt;
}
