package com.kentcarmine.restapiconsumerpractice.exception;

public class BookNotFoundException extends ResourceNotFoundException {

    private static final String DEFAULT_MESSAGE = "Book was not found";

    private final Long notFoundId;

    public BookNotFoundException(Long notFoundId) {
        super("Book with id = " + notFoundId + " was not found");
        this.notFoundId = notFoundId;
    }


}
