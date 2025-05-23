package com.learning_platform.lectureMgmt.models;


import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "classroom")
public class ClassroomModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @Column(columnDefinition = "text[] DEFAULT '{}'")
    private List<String> instructorIds = new ArrayList<>();

    @Column(columnDefinition = "text[] DEFAULT '{}'")
    private List<String> studentIds = new ArrayList<>();

    private String description;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode attendance;

    // TODO: handle this correctly
//    @OneToMany(mappedBy = "classroomId",cascade = CascadeType.ALL , orphanRemoval = true)
    private List<String> lectures = new ArrayList<>();

    private String clientType;
    private Instant createdAt;
}
