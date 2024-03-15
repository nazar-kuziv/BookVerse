package com.bookverseApp.bookverse.savedReviews;


import com.bookverseApp.bookverse.exceptions.ReviewNotFoundException;
import com.bookverseApp.bookverse.exceptions.SomethingWentWrongException;
import com.bookverseApp.bookverse.exceptions.UserNotFoundException;
import com.bookverseApp.bookverse.jpa.ReviewRepository;
import com.bookverseApp.bookverse.jpa.SavedReviewsRepository;
import com.bookverseApp.bookverse.jpa.UserRepository;
import com.bookverseApp.bookverse.review.Review;
import com.bookverseApp.bookverse.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SavedReviewsResource {

    SavedReviewsRepository savedReviewsRepository;
    UserRepository userRepository;
    ReviewRepository reviewRepository;

    public SavedReviewsResource(SavedReviewsRepository savedReviewsRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.savedReviewsRepository = savedReviewsRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/saveReview")
    public ResponseEntity<Object> saveReview(@RequestParam Long userId, @RequestParam Long reviewId) {
        SavedReviews savedReviews = new SavedReviews();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
        savedReviews.setUser(user);
        savedReviews.setReview(review);
        savedReviewsRepository.save(savedReviews);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/savedReview")
    public List<SavedReviews> getSavedReviews(@RequestParam Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return savedReviewsRepository.findByUser(user);
    }

    @DeleteMapping("/deleteSavedReview")
    public ResponseEntity<Object> deleteSavedReview(@RequestParam Long userId, @RequestParam Long reviewId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
        SavedReviews savedReviews = savedReviewsRepository.findByUserAndReview(user, review).orElseThrow(() -> new SomethingWentWrongException("Review not found"));
        savedReviewsRepository.delete(savedReviews);
        return ResponseEntity.ok().build();
    }

}
