package com.kentcarmine.restapiconsumerpractice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateOrUpdateBookDto {

    @NotNull
    @NotBlank(message = "title cannot be blank")
    private String title;

    @NotNull
    @NotBlank(message = "author cannot be blank")
    private String author;

    public CreateOrUpdateBookDto() {
        this.title = null;
        this.author = null;
    }

    public CreateOrUpdateBookDto(String title, String author) {
        this.title = title;
        this.author = author;
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
        return "CreateBookDto{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
