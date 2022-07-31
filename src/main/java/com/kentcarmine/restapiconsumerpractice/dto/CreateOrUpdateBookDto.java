package com.kentcarmine.restapiconsumerpractice.dto;

public class CreateOrUpdateBookDto {

    private String title;
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
        return "CreateOrUpdateBookDto{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
