package com.bookverseApp.bookverse.review;

import com.bookverseApp.bookverse.book.Book;
import com.bookverseApp.bookverse.comment.Comment;
import com.bookverseApp.bookverse.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_seq")
    @SequenceGenerator(name = "reviews_seq", sequenceName = "reviews_seq", allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Book book;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern="HH:mm dd/MM/yyyy")
    private LocalDateTime date;
    @Size(min = 11, message = "The review should have at least 11 characters.")
    @NotBlank(message = "The review cannot be empty.")
    @Column(length = 10000)
    private String review;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer likesAmount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer commentsAmount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer shareAmount;
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();
    @Transient
    private boolean likedByUser;

    public Review(Long id, Book book, User user, LocalDateTime date, String review, Integer likesAmount,
                  Integer commentsAmount, Integer shareAmount, List<Comment> comments) {
        super();
        this.id = id;
        this.book = book;
        this.user = user;
        this.date = date;
        this.review = review;
        this.likesAmount = likesAmount;
        this.commentsAmount = commentsAmount;
        this.shareAmount = shareAmount;
        this.comments = comments;
    }

    public Review(Book book, String review, User user) {
        super();
        this.book = book;
        this.review = review;
        this.user = user;
        this.date = LocalDateTime.now();
        this.likesAmount = 0;
        this.commentsAmount = 0;
        this.shareAmount = 0;
    }

    public Review() {
        super();
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getReview() {
        return review;
    }

    public void setId(Long login) {
        this.id = login;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getLikesAmount() {
        return likesAmount;
    }

    public void setLikesAmount(Integer likesAmount) {
        this.likesAmount = likesAmount;
    }

    public Integer getCommentsAmount() {
        return commentsAmount;
    }

    public void setCommentsAmount(Integer commentsAmount) {
        this.commentsAmount = commentsAmount;
    }

    public Integer getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(Integer saveAmount) {
        this.shareAmount = saveAmount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    @JsonProperty("user")
    public User getUserLogin() {
        User user = new User();
        user.setLogin(this.user.getLogin());
        user.setPictureCoverPath(this.user.getPictureCoverPath());
        user.setId(this.user.getId());
        return user;
    }

    @JsonProperty("comments")
    public List<Comment> getCommentsList() {
        List<Comment> result = new ArrayList<>();
        for (Comment comment : this.comments) {
            if(comment.getParentComment()==null){
                    result.add(comment);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", book=" + book +
                ", user=" + user +
                ", date=" + date +
                ", review='" + review + '\'' +
                ", likesAmount=" + likesAmount +
                ", commentsAmount=" + commentsAmount +
                ", shareAmount=" + shareAmount +
                ", comments=" + comments +
                '}';
    }
}
