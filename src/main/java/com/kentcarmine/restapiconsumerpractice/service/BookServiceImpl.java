package com.kentcarmine.restapiconsumerpractice.service;

import com.kentcarmine.restapiconsumerpractice.dto.BookDto;
import com.kentcarmine.restapiconsumerpractice.dto.CreateOrUpdateBookDto;
import com.kentcarmine.restapiconsumerpractice.exception.BookNotFoundException;
import com.kentcarmine.restapiconsumerpractice.exception.UnknownException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Set;

@Validated
@Service
public class BookServiceImpl implements BookService {

    private final String apiBaseUrl;
    private final String apiFindAllUrl;
    private final String apiFindAllByTitleUrl;
    private final String apiFindAllByAuthorUrl;
    private final String apiFindByIdUrl;
    private final String apiCreateUrl;
    private final String apiUpdateUrl;
    private final String apiDeleteUrl;

    public BookServiceImpl(@Value("${restapipractice.api.v1.base_url}") String apiBaseUrl,
                           @Value("${restapipractice.books.findAll_url}") String apiFindAllUrl,
                           @Value("${restapipractice.books.findAllByTitle_url}") String apiFindAllByTitleUrl,
                           @Value("${restapipractice.books.findAllByAuthor_url}") String apiFindAllByAuthorUrl,
                           @Value("${restapipractice.books.findById_url}") String apiFindByIdUrl,
                           @Value("${restapipractice.books.create_url}") String apiCreateUrl,
                           @Value("${restapipractice.books.update_url}") String apiUpdateUrl,
                           @Value("${restapipractice.books.delete_url}") String apiDeleteUrl ) {
        this.apiBaseUrl = apiBaseUrl;
        this.apiFindAllUrl = apiFindAllUrl;
        this.apiFindAllByTitleUrl = apiFindAllByTitleUrl;
        this.apiFindAllByAuthorUrl = apiFindAllByAuthorUrl;
        this.apiFindByIdUrl = apiFindByIdUrl;
        this.apiCreateUrl = apiCreateUrl;
        this.apiUpdateUrl = apiUpdateUrl;
        this.apiDeleteUrl = apiDeleteUrl;
    }

    @Override
    public Set<BookDto> getAllBooks() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Set<BookDto>> response = restTemplate.exchange(apiFindAllUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<Set<BookDto>>(){});
        return response.getBody();
    }

    @Override
    public Set<BookDto> getAllBooksByTitle(String title) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiFindAllByTitleUrl + title;
        ResponseEntity<Set<BookDto>> response = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<Set<BookDto>>(){});
        return response.getBody();
    }

    @Override
    public Set<BookDto> getAllBooksByAuthor(String author) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiFindAllByAuthorUrl + author;
        ResponseEntity<Set<BookDto>> response = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<Set<BookDto>>(){});
        return response.getBody();
    }

    @Override
    public BookDto getBookById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiFindByIdUrl + id;

        ResponseEntity<BookDto> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET,
                    null, new ParameterizedTypeReference<BookDto>(){});
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new BookNotFoundException(id);
            } else {
                throw new UnknownException();
            }
        }

        return response.getBody();
    }

    @Override
    public BookDto createNewBook(@Valid CreateOrUpdateBookDto createOrUpdateBookDto) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BookDto> response = restTemplate.postForEntity(apiCreateUrl, createOrUpdateBookDto,
                BookDto.class);
        return response.getBody();
    }

    @Override
    public BookDto updateBookWithId(Long id, @Valid CreateOrUpdateBookDto bookDto) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUpdateUrl + id;

        ResponseEntity<BookDto> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity(bookDto),
                    BookDto.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new BookNotFoundException(id);
            } else {
                throw e;
            }
        }

        return response.getBody();
    }

    @Override
    public BookDto deleteBookById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiDeleteUrl + id;

        ResponseEntity<BookDto> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, null, BookDto.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new BookNotFoundException(id);
            } else {
                throw e;
            }
        }

        return response.getBody();
    }
}
