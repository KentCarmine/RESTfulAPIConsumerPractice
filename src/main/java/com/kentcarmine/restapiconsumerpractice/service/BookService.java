package com.kentcarmine.restapiconsumerpractice.service;

import com.kentcarmine.restapiconsumerpractice.dto.BookDto;
import com.kentcarmine.restapiconsumerpractice.dto.CreateOrUpdateBookDto;

import javax.validation.Valid;
import java.util.Set;

public interface BookService {

    Set<BookDto> getAllBooks();

    Set<BookDto> getAllBooksByTitle(String title);

    Set<BookDto> getAllBooksByAuthor(String author);

    BookDto getBookById(Long id);

    BookDto createNewBook(@Valid CreateOrUpdateBookDto createOrUpdateBookDto);

    BookDto deleteBookById(Long id);

    BookDto updateBookWithId(Long id, @Valid CreateOrUpdateBookDto bookDto);
}
