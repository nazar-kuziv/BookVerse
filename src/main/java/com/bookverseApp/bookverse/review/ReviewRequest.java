package com.bookverseApp.bookverse.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ReviewRequest {
    @Size(min = 2, message = "The book title should have at least 11 characters.")
    @NotBlank(message = "The book title cannot be empty.")
    private String bookName;
    @Size(min = 11, message = "The review should have at least 11 characters.")
    @NotBlank(message = "The review cannot be empty.")
    private String review;

    public ReviewRequest(String bookName, String review) {
        super();
        this.bookName = bookName;
        this.review = review;
    }

    public ReviewRequest() {
        super();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}
