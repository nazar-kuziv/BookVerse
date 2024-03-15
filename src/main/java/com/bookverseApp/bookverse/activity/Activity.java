package com.bookverseApp.bookverse.activity;

import com.bookverseApp.bookverse.book.Book;
import com.bookverseApp.bookverse.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activities_seq")
    @SequenceGenerator(name = "activities_seq", sequenceName = "activities_seq", allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;

    private Long minutesOfReading;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateOfReading;

    public Activity(Long id, User user, Book book, Long minutesOfReading, LocalDateTime dateOfReading) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.minutesOfReading = minutesOfReading;
        this.dateOfReading = dateOfReading;
    }

    public Activity(User user, Book book, Long minutesOfReading) {
        this.user = user;
        this.book = book;
        this.minutesOfReading = minutesOfReading;
        this.dateOfReading = LocalDateTime.now();
    }

    public Activity() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getMinutesOfReading() {
        return minutesOfReading;
    }

    public void setMinutesOfReading(Long minutesOfReading) {
        this.minutesOfReading = minutesOfReading;
    }

    public LocalDateTime getDateOfReading() {
        return dateOfReading;
    }

    public void setDateOfReading(LocalDateTime dateOfReading) {
        this.dateOfReading = dateOfReading;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", user=" + user +
                ", book=" + book +
                ", minutesOfReading=" + minutesOfReading +
                ", dateOfReading=" + dateOfReading +
                '}';
    }

    @JsonProperty("book")
    public Book getBookJson() {
        Book book = new Book();
        book.setId(this.book.getId());
        book.setCoverPath(this.book.getCoverPath());
        book.setTitle(this.book.getTitle());
        return book;
    }
}
