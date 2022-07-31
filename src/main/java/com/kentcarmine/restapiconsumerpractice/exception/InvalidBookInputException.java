package com.kentcarmine.restapiconsumerpractice.exception;

public class InvalidBookInputException extends RuntimeException {

    private static final String DEFAULT_MSG = "Book input was invalid or empty/null";

    public InvalidBookInputException() {
        super(DEFAULT_MSG);
    }

    public InvalidBookInputException(String message) {
        super(message);
    }

    public InvalidBookInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBookInputException(Throwable cause) {
        super(cause);
    }

    protected InvalidBookInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
