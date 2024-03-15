package com.bookverseApp.bookverse.likes;

import com.bookverseApp.bookverse.exceptions.ReviewNotFoundException;
import com.bookverseApp.bookverse.exceptions.SomethingWentWrongException;
import com.bookverseApp.bookverse.exceptions.UserNotFoundException;
import com.bookverseApp.bookverse.jpa.LikedReviewsRepository;
import com.bookverseApp.bookverse.jpa.ReviewRepository;
import com.bookverseApp.bookverse.jpa.UserRepository;
import com.bookverseApp.bookverse.review.Review;
import com.bookverseApp.bookverse.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class LikeResource {

    UserRepository userRepository;
    ReviewRepository reviewRepository;
    LikedReviewsRepository likedReviewsRepository;

    public LikeResource(UserRepository userRepository, ReviewRepository reviewRepository, LikedReviewsRepository likedReviewsRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.likedReviewsRepository = likedReviewsRepository;
    }

    @PostMapping("/like")
    public ResponseEntity<Object> like(@RequestParam Long userId, @RequestParam Long reviewId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
        if (likedReviewsRepository.findByReviewIdAndUserId(reviewId, userId).isPresent())
            throw new SomethingWentWrongException("You cannot like a review that you have already liked.");
        Like like = new Like();
        like.setUser(user);
        like.setReview(review);
        review.setLikesAmount(review.getLikesAmount() + 1);
        reviewRepository.save(review);
        likedReviewsRepository.save(like);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/like")
    public ResponseEntity<Object> unlike(@RequestParam Long userId, @RequestParam Long reviewId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
        Like like = likedReviewsRepository.findByReviewIdAndUserId(reviewId, userId).orElseThrow(() -> new SomethingWentWrongException("You cannot unlike a review that you haven't liked."));
        review.setLikesAmount(review.getLikesAmount() - 1);
        reviewRepository.save(review);
        likedReviewsRepository.delete(like);
        return ResponseEntity.ok().build();
    }
}
