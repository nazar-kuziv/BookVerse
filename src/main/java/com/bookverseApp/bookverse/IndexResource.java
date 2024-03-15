package com.bookverseApp.bookverse;

import com.bookverseApp.bookverse.jpa.LibraryRepository;
import com.bookverseApp.bookverse.jpa.LikedReviewsRepository;
import com.bookverseApp.bookverse.jpa.ReviewRepository;
import com.bookverseApp.bookverse.library.Library;
import com.bookverseApp.bookverse.likes.Like;
import com.bookverseApp.bookverse.review.Review;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class IndexResource {

    private final ReviewRepository reviewRepository;
    private final LibraryRepository libraryRepository;
    private final LikedReviewsRepository likedReviewsRepository;

    public IndexResource(ReviewRepository reviewRepository, LibraryRepository libraryRepository, LikedReviewsRepository likedReviewsRepository) {
        this.reviewRepository = reviewRepository;
        this.libraryRepository = libraryRepository;
        this.likedReviewsRepository = likedReviewsRepository;
    }

    @GetMapping("/")
    public List<Review> index(@RequestParam Long userId) {
        List<Review> reviews = reviewRepository.findAll();
        Collections.shuffle(reviews);
        List<Library> userLibrary = libraryRepository.findByUserId(userId);
        if (userLibrary.isEmpty()) {
            return new ArrayList<>(reviews.subList(0, Math.min(reviews.size(), 20)));
        }
        for (Library library : userLibrary) {
            for(int i = 0; i < reviews.size(); i++) {
                Optional<Like> like = likedReviewsRepository.findByReviewIdAndUserId(reviews.get(i).getId(), userId);
                reviews.get(i).setLikedByUser(like.isPresent());
                if (reviews.get(i).getBook().getId().equals(library.getBook().getId())) {
                    reviews.remove(i);
                }
            }
        }
        return new ArrayList<>(reviews.subList(0, Math.min(reviews.size(), 20)));
    }


    @GetMapping("/getBestBookForUser")
    public List<Library> getBestBookForUser(@RequestParam Long userId) {
        List<Library> userLibrary = libraryRepository.findByUserId(userId);
        userLibrary.sort(Comparator.comparingInt(Library::getReadingProgress));
        return new ArrayList<>(userLibrary.subList(0, Math.min(userLibrary.size(), 4)));
    }

}
