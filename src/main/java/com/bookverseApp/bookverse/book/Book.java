package com.bookverseApp.bookverse.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_seq")
    @SequenceGenerator(name = "books_seq", sequenceName = "books_seq", allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Author field cannot be empty.")
    @Size(min = 3, message = "The author field should have at least 3 characters")
    private String author;

    @NotBlank(message = "Title field cannot be empty.")
    @Size(min = 3, message = "The title field must have at least 3 characters.")
    private String title;

    @NotBlank(message = "Genre field cannot be empty.")
    @Size(min = 3, message = "The genre field must have at least 3 characters.")
    private String genre;

    @NotBlank(message = "Description field cannot be empty.")
    @Size(min = 11, max = 10000, message = "The description field must have at least 11 characters and a maximum of 10000.")
    @Column(length = 10000)
    private String description;

    private String coverPath;

    @NotNull(message = "The number of pages cannot be null.")
    @Min(value = 0, message = "The number of pages must be at least 0.")
    private Integer pagesAmount;

    public Book() {
        super();
    }

    public Book(Integer id, String author, String title, String genre, String description, String coverPath, Integer pagesAmount) {
        super();
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.coverPath = coverPath;
        this.pagesAmount = pagesAmount;
    }

    public Book(String author, String title, String genre, String description, String coverPath) {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.coverPath = coverPath;
    }

    public Book(String author, String title, String genre, String description, Integer pagesAmount) {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.pagesAmount = pagesAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPagesAmount() {
        return pagesAmount;
    }

    public void setPagesAmount(Integer pagesAmount) {
        this.pagesAmount = pagesAmount;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Integer getReadingProgress(Integer numberOfPagesRead) {
        return (int) Math.floor((numberOfPagesRead.doubleValue() / pagesAmount) * 100);
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", author='" + author + '\'' + ", title='" + title + '\'' + ", genre='" + genre + '\'' + ", description='" + description + '\'' + ", coverPath='" + coverPath + '\'' + ", pagesAmount=" + pagesAmount + '}';
    }
}
