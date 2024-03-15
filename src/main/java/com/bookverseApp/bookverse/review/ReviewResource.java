package com.bookverseApp.bookverse.review;


import com.bookverseApp.bookverse.book.Book;
import com.bookverseApp.bookverse.exceptions.BookNotFoundException;
import com.bookverseApp.bookverse.exceptions.ReviewNotFoundException;
import com.bookverseApp.bookverse.exceptions.UserNotFoundException;
import com.bookverseApp.bookverse.jpa.BookRepository;
import com.bookverseApp.bookverse.jpa.ReviewRepository;
import com.bookverseApp.bookverse.jpa.UserRepository;
import com.bookverseApp.bookverse.user.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ReviewResource {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public ReviewResource(UserRepository userRepository, ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/reviews")
    public List<Review> retrieveAllReviews() {
        return reviewRepository.findAll();
    }

    @GetMapping("/reviews/{reviewId}")
    public Review getReviewById(@PathVariable Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isEmpty()) throw new ReviewNotFoundException(reviewId);
        return review.get();
    }

    @PostMapping("/addReview")
    public ResponseEntity<Review> createReview(@RequestParam Long userId, @Valid @RequestBody ReviewRequest reviewRequest) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findByTitle(reviewRequest.getBookName()).orElseThrow(BookNotFoundException::new);
        Review savedReview = reviewRepository.save(new Review(book, reviewRequest.getReview(), user));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    @DeleteMapping("/deleteReview")
    public ResponseEntity<Object> deleteReview(@RequestParam Long userId, @RequestParam Long reviewId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
        if (!review.getUser().getId().equals(userId)) throw new ReviewNotFoundException(reviewId);
        reviewRepository.deleteById(reviewId);
        return ResponseEntity.ok().build();
    }
}
