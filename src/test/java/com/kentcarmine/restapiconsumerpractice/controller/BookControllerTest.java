package com.kentcarmine.restapiconsumerpractice.controller;

import com.kentcarmine.restapiconsumerpractice.controller.errorhandling.CustomRestExceptionHandler;
import com.kentcarmine.restapiconsumerpractice.dto.BookDto;
import com.kentcarmine.restapiconsumerpractice.dto.CreateOrUpdateBookDto;
import com.kentcarmine.restapiconsumerpractice.exception.BookNotFoundException;
import com.kentcarmine.restapiconsumerpractice.exception.UnknownException;
import com.kentcarmine.restapiconsumerpractice.helper.JsonConverterHelper;
import com.kentcarmine.restapiconsumerpractice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ConstraintViolationException;
import java.awt.print.Book;
import java.net.ConnectException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("test")
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    BookService bookService;

    @InjectMocks
    BookController bookController;

    BookDto bookDto1;
    BookDto bookDto2;
    Set<BookDto> bookDtoSet;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        bookDto1 = new BookDto(1L, "Title 1", "Author 1");
        bookDto2 = new BookDto(2L, "Title 2", "Author 2");
        bookDtoSet = new HashSet<>();
        bookDtoSet.add(bookDto1);
        bookDtoSet.add(bookDto2);

        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(new CustomRestExceptionHandler())
                .build();
    }

    @Test
    void getBookById_existingId() throws Exception {
        when(bookService.getBookById(any())).thenReturn(bookDto1);
        mockMvc.perform(get("/proxy/api/v1/books/1"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).getBookById(any());
    }

    @Test
    void getBookById_noSuchId() throws Exception {
        when(bookService.getBookById(any())).thenThrow(new BookNotFoundException(1L));

        mockMvc.perform(get("/proxy/api/v1/books/1"))
                .andExpect(status().isNotFound());
        verify(bookService, times(1)).getBookById(any());
    }

    @Test
    void getAllBooks_success() throws Exception {
        when(bookService.getAllBooks()).thenReturn(bookDtoSet);

        mockMvc.perform(get("/proxy/api/v1/books/"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getAllBooks_generalError() throws Exception {
        when(bookService.getAllBooks()).thenThrow(new UnknownException());

        mockMvc.perform(get("/proxy/api/v1/books/"))
                .andExpect(status().isInternalServerError());

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getAllBooksByTitle_existingTitle() throws Exception {
        when(bookService.getAllBooksByTitle(any())).thenReturn(Set.of(bookDto1));

        mockMvc.perform(get("/proxy/api/v1/books/title/" + bookDto1.getTitle()))
                .andExpect(status().isOk());

        verify(bookService, times(1)).getAllBooksByTitle(anyString());
    }

    @Test
    void getAllBooksByTitle_noSuchTitle() throws Exception {
        when(bookService.getAllBooksByTitle(any())).thenReturn(Set.of());

        mockMvc.perform(get("/proxy/api/v1/books/title/argleblargle"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).getAllBooksByTitle(anyString());
    }

    @Test
    void getAllBooksByTitle_generalError() throws Exception {
        when(bookService.getAllBooksByTitle(any())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/proxy/api/v1/books/title/" + bookDto1.getTitle()))
                .andExpect(status().isInternalServerError());

        verify(bookService, times(1)).getAllBooksByTitle(anyString());
    }

    @Test
    void getAllBooksByAuthor_existingAuthor() throws Exception {
        when(bookService.getAllBooksByAuthor(any())).thenReturn(Set.of(bookDto1));

        mockMvc.perform(get("/proxy/api/v1/books/author/" + bookDto1.getAuthor()))
                .andExpect(status().isOk());

        verify(bookService, times(1)).getAllBooksByAuthor(anyString());
    }

    @Test
    void getAllBooksByAuthor_noSuchAuthor() throws Exception {
        when(bookService.getAllBooksByAuthor(any())).thenReturn(Set.of());

        mockMvc.perform(get("/proxy/api/v1/books/author/fargle"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).getAllBooksByAuthor(anyString());
    }

    @Test
    void getAllBooksByAuthor_generalError() throws Exception {
        when(bookService.getAllBooksByAuthor(any())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/proxy/api/v1/books/author/" + bookDto1.getAuthor()))
                .andExpect(status().isInternalServerError());

        verify(bookService, times(1)).getAllBooksByAuthor(anyString());
    }

    @Test
    void createNewBook_valid() throws Exception {
        when(bookService.createNewBook(any())).thenReturn(new BookDto(100L, "Test Title 3", "Test Author 3"));

        CreateOrUpdateBookDto newBook = new CreateOrUpdateBookDto("Test Title 3", "Test Author 3");

        mockMvc.perform(post("/proxy/api/v1/books/new")
                .content(JsonConverterHelper.asJsonString(newBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(bookService, times(1)).createNewBook(any());
    }

    @Test
    void createNewBook_nullBook() throws Exception {
        CreateOrUpdateBookDto newBook = null;

        mockMvc.perform(post("/proxy/api/v1/books/new")
                .content(JsonConverterHelper.asJsonString(newBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(bookService, times(0)).createNewBook(any());
    }

    @Test
    void createNewBook_invalidBook() throws Exception {
        when(bookService.createNewBook(any()))
                .thenThrow(new ConstraintViolationException("test constraint violation ex", Set.of()));
        CreateOrUpdateBookDto newBook = new CreateOrUpdateBookDto(null, "");

        mockMvc.perform(post("/proxy/api/v1/books/new")
                .content(JsonConverterHelper.asJsonString(newBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(bookService, times(1)).createNewBook(any());
    }

    @Test
    void createNewBook_generalError() throws Exception {
        when(bookService.createNewBook(any())).thenThrow(new RuntimeException());

        CreateOrUpdateBookDto newBook = new CreateOrUpdateBookDto("Test Title 3", "Test Author 3");

        mockMvc.perform(post("/proxy/api/v1/books/new")
                .content(JsonConverterHelper.asJsonString(newBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(bookService, times(1)).createNewBook(any());
    }

    @Test
    void updateBook_validInput() throws Exception {
        CreateOrUpdateBookDto newBook = new CreateOrUpdateBookDto("Test Title 3", "Test Author 3");
        BookDto updatedBookDto = new BookDto(bookDto1.getId(), newBook.getTitle(), newBook.getAuthor());

        when(bookService.updateBookWithId(anyLong(), any())).thenReturn(updatedBookDto);

        MvcResult result =  mockMvc.perform(put("/proxy/api/v1/books/1")
                .content(JsonConverterHelper.asJsonString(newBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        verify(bookService, times(1)).updateBookWithId(anyLong(), any());
    }

    @Test
    void updateBook_invalidId() throws Exception {
        when(bookService.updateBookWithId(anyLong(), any())).thenThrow(new BookNotFoundException(1337L));

        CreateOrUpdateBookDto newBook = new CreateOrUpdateBookDto("Test Title 3", "Test Author 3");

        MvcResult result =  mockMvc.perform(put("/proxy/api/v1/books/1337")
                .content(JsonConverterHelper.asJsonString(newBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        verify(bookService, times(1)).updateBookWithId(anyLong(), any());
    }

    @Test
    void updateBook_nullBook() throws Exception {
        CreateOrUpdateBookDto newBook = null;

        MvcResult result =  mockMvc.perform(put("/proxy/api/v1/books/1")
                .content(JsonConverterHelper.asJsonString(newBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("", result.getResponse().getContentAsString());

        verify(bookService, times(0)).updateBookWithId(anyLong(), any());
    }

    @Test
    void updateBook_invalidBook() throws Exception {
        when(bookService.updateBookWithId(anyLong(), any()))
                .thenThrow(new ConstraintViolationException("test constraint violation ex", Set.of()));
        CreateOrUpdateBookDto newBook = new CreateOrUpdateBookDto("", null);

        MvcResult result =  mockMvc.perform(put("/proxy/api/v1/books/1")
                .content(JsonConverterHelper.asJsonString(newBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        verify(bookService, times(1)).updateBookWithId(anyLong(), any());
    }

    @Test
    void updateBook_generalError() throws Exception {
        when(bookService.updateBookWithId(anyLong(), any())).thenThrow(new RuntimeException());

        CreateOrUpdateBookDto newBook = new CreateOrUpdateBookDto("Test Title 3", "Test Author 3");

        mockMvc.perform(put("/proxy/api/v1/books/1")
                .content(JsonConverterHelper.asJsonString(newBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(bookService, times(1)).updateBookWithId(anyLong(), any());
    }

    @Test
    void deleteBook_validId() throws Exception {
        when(bookService.deleteBookById(anyLong())).thenReturn(bookDto1);

        mockMvc.perform(delete("/proxy/api/v1/books/1"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBookById(anyLong());
    }

    @Test
    void deleteBook_invalidId() throws Exception {
        when(bookService.deleteBookById(anyLong())).thenThrow(new BookNotFoundException(1337L));

        mockMvc.perform(delete("/proxy/api/v1/books/1337"))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).deleteBookById(anyLong());
    }

    @Test
    void deleteBook_generalError() throws Exception {
        when(bookService.deleteBookById(anyLong())).thenThrow(new RuntimeException());

        mockMvc.perform(delete("/proxy/api/v1/books/1"))
                .andExpect(status().isInternalServerError());

        verify(bookService, times(1)).deleteBookById(anyLong());
    }
}