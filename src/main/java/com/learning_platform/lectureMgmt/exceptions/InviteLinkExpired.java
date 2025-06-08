package com.learning_platform.lectureMgmt.exceptions;

public class InviteLinkExpired extends RuntimeException {
    public InviteLinkExpired(String message) {
        super(message);
    }
}
