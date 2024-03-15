package com.bookverseApp.bookverse.comment;

import com.bookverseApp.bookverse.exceptions.ReviewNotFoundException;
import com.bookverseApp.bookverse.exceptions.UserNotFoundException;
import com.bookverseApp.bookverse.jpa.CommentRepository;
import com.bookverseApp.bookverse.jpa.ReviewRepository;
import com.bookverseApp.bookverse.jpa.UserRepository;
import com.bookverseApp.bookverse.review.Review;
import com.bookverseApp.bookverse.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class CommentResource {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;

    public CommentResource(UserRepository UserRepository, ReviewRepository ReviewRepository, CommentRepository CommentRepository) {
        this.userRepository = UserRepository;
        this.reviewRepository = ReviewRepository;
        this.commentRepository = CommentRepository;
    }

    @PostMapping("/addComment")
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequest commentRequest) {
        Comment commentToSave = new Comment();
        User user = userRepository.findById(commentRequest.getUserId()).orElseThrow(UserNotFoundException::new);
        commentToSave.setUser(user);
        commentToSave.setText(commentRequest.getText());
        Review review = reviewRepository.findById(commentRequest.getReviewId()).orElseThrow(() -> new ReviewNotFoundException(commentRequest.getReviewId()));
        commentToSave.setReview(review);
        if (commentRequest.getParentCommentId() != null) {
            Optional<Comment> previousComment = commentRepository.findById(commentRequest.getParentCommentId());
            previousComment.ifPresent(commentToSave::setParentComment);
        }
        commentRepository.save(commentToSave);
        return ResponseEntity.ok(commentToSave);
    }
}
