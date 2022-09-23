package com.kentcarmine.restapiconsumerpractice.controller;


import com.kentcarmine.restapiconsumerpractice.dto.BookDto;
import com.kentcarmine.restapiconsumerpractice.dto.CreateOrUpdateBookDto;
import com.kentcarmine.restapiconsumerpractice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return bookService.getBookById(id);
    }

    // List all books
    @GetMapping({"", "/"})
    public Set<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    // List all books by title
    @GetMapping("/title/{bookTitle}")
    public Set<BookDto> getAllBooksByTitle(@PathVariable String bookTitle) {
        return bookService.getAllBooksByTitle(bookTitle);
    }

    // List all books by author
    @GetMapping("/author/{bookAuthor}")
    public Set<BookDto> getAllBooksByAuthor(@PathVariable String bookAuthor) {
        return bookService.getAllBooksByAuthor(bookAuthor);
    }

    // Create book
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createNewBook(/*@Valid*/ @RequestBody CreateOrUpdateBookDto newBook) {
        return bookService.createNewBook(newBook);
    }

    // Update book
    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, /*@Valid*/ @RequestBody CreateOrUpdateBookDto updateBook) {
        return bookService.updateBookWithId(id, updateBook);
    }

    // Delete book by id
    @DeleteMapping("/{id}")
    public BookDto deleteBook(@PathVariable Long id) {
        return bookService.deleteBookById(id);
    }

}
