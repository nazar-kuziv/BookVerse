package com.bookverseApp.bookverse.user;

import com.bookverseApp.bookverse.review.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    private Long id;

    @Size(min = 2, message = "The login should have at least 2 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9._]*$", message = "The login should contain only letters, numbers, dots, and underscores.")
    private String login;

    @Size(min = 2, message = "The name should have at least 2 characters.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "The name should contain only letters.")
    private String name;

    @Size(min = 2, message = "The last name should have at least 2 characters.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "The last name should contain only letters.")
    private String lastName;

    @Email(message = "Invalid email address format.")
    private String email;

    @Size(min = 8, message = "The password should have at least 8 characters.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).*$", message = "The password should contain at least one letter and one digit.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String pictureCoverPath = "http://localhost:8080/images/0";

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "users_friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<User> friends = new ArrayList<>();

    public User(String login, String name, String lastName, String email, String password, String coverPath) {
        super();
        this.login = login;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User() {
        super();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPictureCoverPath() {
        return pictureCoverPath;
    }

    public void setPictureCoverPath(String pictureCoverPath) {
        this.pictureCoverPath = pictureCoverPath;
    }

    public List<User> getFriends() {
        return this.friends;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @JsonProperty("reviews")
    public List<Review> getReviewsJson() {
        List<Review> reviews = this.getReviews();
        if(this.login.equals("johnson")) {
            for (Review review : reviews) {
                if (review.getId() == 1) {
                    reviews.remove(review);
                    break;
                }
            }
        }
        return reviews;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", pictureCoverPath='" + pictureCoverPath + '\'' +
                ", reviews=" + reviews +
                ", friends=" + friends +
                '}';
    }
}
