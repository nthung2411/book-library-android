package com.aavn.devday.booklibrary.data.model;

import com.google.gson.annotations.SerializedName;

public class BookComment {
    @SerializedName("content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
