package com.aavn.devday.booklibrary.data.model;

import com.google.gson.annotations.SerializedName;

public class CommentRequest {
    @SerializedName("comment")
    private String comment;
    @SerializedName("parentId")
    private Long parentId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
