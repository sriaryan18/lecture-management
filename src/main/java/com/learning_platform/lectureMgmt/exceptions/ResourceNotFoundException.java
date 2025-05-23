
package com.learning_platform.lectureMgmt.exceptions;

public class ResourceNotFoundException extends  RuntimeException{
    public ResourceNotFoundException(String classroomId, String resourceName){
        super( resourceName.toUpperCase() +  " with id : " + classroomId + " not found");
    }


}
