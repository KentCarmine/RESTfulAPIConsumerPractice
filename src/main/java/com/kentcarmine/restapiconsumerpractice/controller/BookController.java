package com.kentcarmine.restapiconsumerpractice.controller;


import com.kentcarmine.restapiconsumerpractice.dto.BookDto;
import com.kentcarmine.restapiconsumerpractice.dto.CreateOrUpdateBookDto;
import com.kentcarmine.restapiconsumerpractice.exception.BookNotFoundException;
import com.kentcarmine.restapiconsumerpractice.exception.InvalidBookInputException;
import com.kentcarmine.restapiconsumerpractice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/proxy/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // List one book by id
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        // TODO: Fill in
        return null;
    }

    // List all books
    @GetMapping({"", "/"})
    public Set<BookDto> getAllBooks() {
        // TODO: Fill in
        return null;
    }

    // List all books by title
    @GetMapping("/title/{bookTitle}")
    public Set<BookDto> getAllBooksByTitle(@PathVariable String bookTitle) {
        // TODO: Fill in
        return null;
    }

    // List all books by author
    @GetMapping("/author/{bookAuthor}")
    public Set<BookDto> getAllBooksByAuthor(@PathVariable String bookAuthor) {
        // TODO: Fill in
        return null;
    }

    // Create book
    @PostMapping("/new")
    public BookDto createNewBook(@RequestBody CreateOrUpdateBookDto newBook) {
        // TODO: Fill in
        return null;
    }

    // Update book
    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody(required = false) CreateOrUpdateBookDto updateBook) {
        // TODO: Fill in
        return null;
    }

    // Delete book by id
    @DeleteMapping("/{id}")
    public BookDto deleteBook(@PathVariable Long id) {
        // TODO: Fill in
        return null;
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException bnfe) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bnfe.getMessage());
    }

    @ExceptionHandler(InvalidBookInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidBookInputException(InvalidBookInputException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
