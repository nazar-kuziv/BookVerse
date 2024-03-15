package com.bookverseApp.bookverse.library;

import com.bookverseApp.bookverse.book.Book;
import com.bookverseApp.bookverse.exceptions.BookNotFoundException;
import com.bookverseApp.bookverse.exceptions.SomethingWentWrongException;
import com.bookverseApp.bookverse.exceptions.UserNotFoundException;
import com.bookverseApp.bookverse.jpa.BookRepository;
import com.bookverseApp.bookverse.jpa.LibraryRepository;
import com.bookverseApp.bookverse.jpa.UserRepository;
import com.bookverseApp.bookverse.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LibraryResource {

    LibraryRepository libraryRepository;
    BookRepository bookRepository;
    UserRepository userRepository;

    public LibraryResource(LibraryRepository libraryRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/users/library")
    public List<Library> retrieveLibrary(@RequestParam Long userId) {
        return libraryRepository.findByUserId(userId);
    }

    @PostMapping("/users/library")
    public ResponseEntity<Object> addToLibrary(@RequestParam Long userId, @RequestParam Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        libraryRepository.findByUserIdAndBookId(userId, bookId).ifPresent((library) -> {
            throw new SomethingWentWrongException("The book is already in your library.");
        });
        Library newLibrary = new Library(user, book, 0);
        libraryRepository.save(newLibrary);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/library")
    public ResponseEntity<Object> readAgain(@RequestParam Long userId, @RequestParam Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Library saveLibrary = libraryRepository.findByUserIdAndBookId(userId, bookId).orElseThrow(() -> new SomethingWentWrongException("You can't read a book that is not in your library."));
        saveLibrary.setNumberOfPagesRead(0);
        libraryRepository.save(saveLibrary);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/library")
    public ResponseEntity<Object> deleteFromLibrary(@RequestParam Long userId, @RequestParam Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Library saveLibrary = libraryRepository.findByUserIdAndBookId(userId, bookId).orElseThrow(() -> new SomethingWentWrongException("You can't delete a book that is not in your library."));
        libraryRepository.delete(saveLibrary);
        return ResponseEntity.ok().build();
    }
}
