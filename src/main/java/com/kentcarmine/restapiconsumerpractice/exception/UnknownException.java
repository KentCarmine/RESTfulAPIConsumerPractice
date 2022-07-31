package com.kentcarmine.restapiconsumerpractice.exception;

public class UnknownException extends RuntimeException {
    private static final String DEFAULT_MSG = "An unknown error occurred.";

    public UnknownException() {
        super();
    }

    public UnknownException(String message) {
        super(message);
    }
}
