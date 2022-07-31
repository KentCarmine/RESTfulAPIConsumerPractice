package com.kentcarmine.restapiconsumerpractice.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final String DEFAULT_MSG = "Unable to connect to backing API";

    public ResourceNotFoundException() {
        super(DEFAULT_MSG);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
