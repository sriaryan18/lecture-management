package com.learning_platform.lectureMgmt.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NotesCreatedDto {
    private String lectureId;
    private String content;
    private String instructorId;
}
