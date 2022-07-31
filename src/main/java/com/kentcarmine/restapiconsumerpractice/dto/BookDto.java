package com.kentcarmine.restapiconsumerpractice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class BookDto {

    @NotNull
    @Positive
    private Long id;

    @NotNull
    @NotBlank(message = "title cannot be blank")
    private String title;

    @NotNull
    @NotBlank(message = "author cannot be blank")
    private String author;

    public BookDto() {
        this.id = null;
        this.title = null;
        this.author = null;
    }

    public BookDto(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
