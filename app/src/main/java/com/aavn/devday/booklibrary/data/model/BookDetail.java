package com.aavn.devday.booklibrary.data.model;

import com.google.gson.annotations.SerializedName;

public class BookDetail {
    @SerializedName("description")
    private String description;

    @SerializedName("coverUrl")
    private String coverUrl;

    @SerializedName("source")
    private String source;

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
}
