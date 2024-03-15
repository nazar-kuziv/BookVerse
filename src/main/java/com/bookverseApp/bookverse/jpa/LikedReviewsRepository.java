package com.bookverseApp.bookverse.jpa;

import com.bookverseApp.bookverse.likes.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikedReviewsRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByReviewIdAndUserId(Long id, Long userId);
}
