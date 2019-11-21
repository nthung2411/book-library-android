package com.aavn.devday.booklibrary.data.model;

import com.google.gson.annotations.SerializedName;

public class BookComment {
    @SerializedName("user")
    private User user;

    @SerializedName("content")
    private String content;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
