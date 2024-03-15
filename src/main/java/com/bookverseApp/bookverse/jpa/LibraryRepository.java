package com.bookverseApp.bookverse.jpa;

import com.bookverseApp.bookverse.library.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    List<Library> findByUserId(Long userId);

    Optional<Library> findByUserIdAndBookTitle(Long userId, String title);

    Optional<Library> findByUserIdAndBookId(Long userId, Long bookId);
}
