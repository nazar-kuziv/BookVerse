package com.bookverseApp.bookverse.savedReviews;

import com.bookverseApp.bookverse.review.Review;
import com.bookverseApp.bookverse.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity(name = "saved_reviews")
public class SavedReviews {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "saved_reviews_seq")
    @SequenceGenerator(name = "saved_reviews_seq", sequenceName = "saved_reviews_seq", allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id")
    private Review review;

    public SavedReviews() {
    }

    public SavedReviews(Long id, User user, Review review) {
        this.id = id;
        this.user = user;
        this.review = review;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
