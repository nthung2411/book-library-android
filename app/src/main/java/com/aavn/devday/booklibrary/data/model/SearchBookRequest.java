package com.aavn.devday.booklibrary.data.model;

import com.google.gson.annotations.SerializedName;

public class SearchBookRequest {
    @SerializedName("keyword")
    private String keyword;

    public SearchBookRequest(String keyword) {
        this.keyword = keyword;
    }
}
