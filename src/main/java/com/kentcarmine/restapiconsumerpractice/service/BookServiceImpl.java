package com.kentcarmine.restapiconsumerpractice.service;

import com.kentcarmine.restapiconsumerpractice.dto.BookDto;
import com.kentcarmine.restapiconsumerpractice.dto.CreateOrUpdateBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BookServiceImpl implements BookService {




    @Override
    public Set<BookDto> getAllBooks() {
        // TODO: Fill in
        return null;
    }

    @Override
    public Set<BookDto> getAllBooksByTitle(String title) {
        // TODO: Fill in
        return null;
    }

    @Override
    public Set<BookDto> getAllBooksByAuthor(String author) {
        // TODO: Fill in
        return null;
    }

    @Override
    public BookDto getBookById(Long id) {
        // TODO: Fill in
        return null;
    }

    @Override
    public BookDto createNewBook(CreateOrUpdateBookDto createOrUpdateBookDto) {
        // TODO: Fill in
        return null;
    }

    @Override
    public BookDto updateBookWithId(Long id, CreateOrUpdateBookDto bookDto) {
        // TODO: Fill in
        return null;
    }

    @Override
    public BookDto deleteBookById(Long id) {
        // TODO: Fill in
        return null;
    }

    @Override
    public boolean isBookWithIdExists(Long id) {
        // TODO: Fill in
        return false;
    }
}
