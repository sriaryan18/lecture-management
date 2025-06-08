package com.learning_platform.lectureMgmt.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.ArrayList;
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


    private String lectureName;

    private String lectureDescription;

    private String classroomId;

    @Builder.Default
    private Instant createdAt = Instant.now();

    private String instructorId;

    @Builder.Default
    private String notes = "";

    @Builder.Default
    private List<String> testIds = new ArrayList<>();

    private List<String> topics;
}
