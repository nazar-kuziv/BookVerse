package com.bookverseApp.bookverse.jpa;

import com.bookverseApp.bookverse.review.Review;
import com.bookverseApp.bookverse.savedReviews.SavedReviews;
import com.bookverseApp.bookverse.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedReviewsRepository extends JpaRepository<SavedReviews, Long> {
    List<SavedReviews> findByUser(User user);
    Optional<SavedReviews> findByUserAndReview(User user, Review review);
}
