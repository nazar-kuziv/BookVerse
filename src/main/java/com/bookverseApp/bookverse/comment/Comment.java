package com.bookverseApp.bookverse.comment;

import com.bookverseApp.bookverse.review.Review;
import com.bookverseApp.bookverse.user.User;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq")
    @SequenceGenerator(name = "comments_seq", sequenceName = "comments_seq", allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id")
    @JsonIgnore
    private Review review;

    @NotBlank(message = "The text cannot be empty.")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    @JsonBackReference
    @JsonIgnore
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    private List<Comment> childComments = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    public Comment() {
        this.date = LocalDateTime.now();
    }

    public Comment(Long id, User user, Review review, String text, Comment parentComment, List<Comment> childComments) {
        super();
        this.id = id;
        this.user = user;
        this.review = review;
        this.text = text;
        this.parentComment = parentComment;
        this.childComments = childComments;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public List<Comment> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<Comment> childComments) {
        this.childComments = childComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    @JsonProperty("userLogin")
    public String getUserLoginJson() {
        return user.getLogin();
    }

    @JsonProperty("userPictureCoverPath")
    public String getUserPictureCoverPath() {
        return user.getPictureCoverPath();
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", user=" + user + ", review=" + review + ", text='" + text + '\'' + ", parentComment=" + parentComment + ", childComments=" + childComments + '}';
    }
}
