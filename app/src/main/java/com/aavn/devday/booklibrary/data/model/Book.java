package com.aavn.devday.booklibrary.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Book {
    @SerializedName("name")
    private String title;

    @SerializedName("author")
    private String author;

    @SerializedName("details")
    private List<BookDetail> details;

    public Book(String title, String author, List<BookDetail> details) {
        this.title = title;
        this.author = author;
        this.details = details;
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

    public List<BookDetail> getDetails() {
        return details;
    }

    public void setDetails(List<BookDetail> details) {
        this.details = details;
    }
}