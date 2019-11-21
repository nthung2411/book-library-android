package com.aavn.devday.booklibrary.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookDetail {
    @SerializedName("description")
    private String description;

    @SerializedName("coverUrl")
    private String coverUrl;

    @SerializedName("source")
    private String source;

    @SerializedName("comments")
    private List<BookComment> comments;

    @SerializedName("ratings")
    private List<BookRating> ratings;

    public BookDetail(String description) {
        this.description = description;
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

    public List<BookComment> getComments() {
        return comments;
    }

    public void setComments(List<BookComment> comments) {
        this.comments = comments;
    }

    public List<BookRating> getRatings() {
        return ratings;
    }

    public void setRatings(List<BookRating> ratings) {
        this.ratings = ratings;
    }
}
