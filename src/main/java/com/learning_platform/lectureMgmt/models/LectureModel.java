package com.learning_platform.lectureMgmt.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lecture_management")
public class LectureModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    private String classroomId;


    private Instant createdAt;


    private String instructorId;

    private String notes;


    private List<String> testIds;

    private List<String> topics;
}


