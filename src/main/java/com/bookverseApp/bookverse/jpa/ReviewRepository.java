package com.bookverseApp.bookverse.jpa;

import com.bookverseApp.bookverse.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM reviews r WHERE r.id <> 1")
    List<Review> findAll();

    @Query("SELECT r FROM reviews r WHERE r.id = 1")
    Review getDiscussion();
}
