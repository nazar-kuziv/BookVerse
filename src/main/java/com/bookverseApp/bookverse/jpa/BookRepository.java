package com.bookverseApp.bookverse.jpa;

import com.bookverseApp.bookverse.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

    List<Book> findByGenre(String genre);

    boolean existsByTitle(String title);

    List<Book> findByTitleContaining(String bookTitle);
}
