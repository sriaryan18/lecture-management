package com.learning_platform.lectureMgmt.exceptions;

public class NoNotesFound extends RuntimeException{
    public NoNotesFound(String lectureId, String studentId){
        super("No notes found for lecture: " + lectureId + " and student: " + studentId);
    }
}
