package com.bookverseApp.bookverse.book;

import com.bookverseApp.bookverse.exceptions.BookExistsException;
import com.bookverseApp.bookverse.exceptions.BookNotFoundException;
import com.bookverseApp.bookverse.images.Image;
import com.bookverseApp.bookverse.jpa.BookRepository;
import jakarta.validation.Valid;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class BookResource {
    private final BookRepository bookRepository;

    public BookResource(BookRepository bookRepository, Environment environment) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public List<Book> retrieveAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/byGenre")
    public List<Book> retrieveBooksByGenre(@RequestParam String genre) {
        String decodedGenre = UriUtils.decode(genre, "UTF-8");
        List<Book> booksOfThisGenre = bookRepository.findByGenre(decodedGenre);
        if (booksOfThisGenre.isEmpty()) {
            throw new BookNotFoundException();
        }
        return booksOfThisGenre;
    }

    @GetMapping("/books/byId")
    public Book retrieveBookById(@RequestParam Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @GetMapping("/books/byTitle")
    public List<Book> retrieveBooksByTitle(@RequestParam String bookTitle) {
        String decodedBookTitle = UriUtils.decode(bookTitle, "UTF-8");
        List<Book> result = bookRepository.findByTitleContaining(decodedBookTitle);
        if (result.isEmpty()) {
            throw new BookNotFoundException();
        }
        return result;
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestPart @Valid Book book, @RequestPart MultipartFile file) {
        if (bookRepository.existsByTitle(book.getTitle())) {
            throw new BookExistsException(book.getTitle());
        }
        book.setCoverPath(Image.loadBookImage(file));
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

}
