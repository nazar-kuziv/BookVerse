package com.bookverseApp.bookverse.discussion;

import com.bookverseApp.bookverse.jpa.ReviewRepository;
import com.bookverseApp.bookverse.review.Review;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class DiscussionResource {
    private final ReviewRepository reviewRepository;

    public DiscussionResource(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/discussion")
    public Review getDiscussion() {
        return reviewRepository.getDiscussion();
    }
}
