package com.aavn.devday.booklibrary.data.model;

public class BookViewModel {
    private String title;
    private String author;
    private String description;
    private String coverUrl;
    private String source;

    public BookViewModel(String title, String author, String description, String coverUrl, String source) {
        this.title = title;
        this.author = author;
        this.description= description;
        this.coverUrl = coverUrl;
        this.source = source;
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
