package com.aavn.devday.booklibrary.data.model;

import java.util.ArrayList;
import java.util.List;

public class BookViewModel {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String coverUrl;
    private String source;
    private Long bookDetailId;
    private List<BookComment> bookComments;
    private int averageRating;

    public BookViewModel(Long id, String title, String author, String description, String coverUrl, String source, Long bookDetailId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description= description;
        this.coverUrl = coverUrl;
        this.source = source;
        this.bookDetailId = bookDetailId;
        this.bookComments = new ArrayList<>();
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public List<BookComment> getBookComments() {
        return bookComments;
    }

    public void setBookComments(List<BookComment> bookComments) {
        this.bookComments = bookComments;
    }

    public Long getBookDetailId() {
        return bookDetailId;
    }

    public void setBookDetailId(Long bookDetailId) {
        this.bookDetailId = bookDetailId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
