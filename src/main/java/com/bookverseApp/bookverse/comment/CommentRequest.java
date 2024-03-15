package com.bookverseApp.bookverse.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentRequest {
    private Long userId;

    private Long reviewId;

    @NotBlank(message = "The text cannot be empty.")
    @Size(min = 11, message = "The text must have at least 11 characters.")
    private String text;

    private Long parentCommentId;

    public CommentRequest(Long userId, Long reviewId, String text, Long parentCommentId) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.text = text;
        this.parentCommentId = parentCommentId;
    }

    public CommentRequest() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    @Override
    public String toString() {
        return "CommentRequest{" +
                "userId=" + userId +
                ", reviewId=" + reviewId +
                ", text='" + text + '\'' +
                ", parentCommentId=" + parentCommentId +
                '}';
    }
}
