package com.aavn.devday.booklibrary.data.model;

import com.google.gson.annotations.SerializedName;

public class BookRating {

    @SerializedName("value")
    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
