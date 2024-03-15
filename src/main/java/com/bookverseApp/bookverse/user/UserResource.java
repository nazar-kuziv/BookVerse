package com.bookverseApp.bookverse.user;


import com.bookverseApp.bookverse.exceptions.UnavailableLoginException;
import com.bookverseApp.bookverse.exceptions.UserIncorrectPasswordException;
import com.bookverseApp.bookverse.exceptions.UserNotFoundException;
import com.bookverseApp.bookverse.images.Image;
import com.bookverseApp.bookverse.jpa.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserResource {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResource(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        userRepository.findByLogin(user.getLogin()).ifPresent(userWithSameLogin -> {
            throw new UnavailableLoginException();
        });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@Valid @RequestBody User requestUser) {
        User user = userRepository.findByLogin(requestUser.getLogin()).orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(requestUser.getPassword(), user.getPassword()))
            throw new UserIncorrectPasswordException();
        return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }

    @PutMapping("/changeProfilePicture")
    public ResponseEntity<User> changeProfilePicture(@RequestPart String userLogin, @RequestPart MultipartFile file) {
        User user = userRepository.findByLogin(userLogin).orElseThrow(UserNotFoundException::new);
        String coverPath = Image.loadUserImage(file, user.getId());
        user.setPictureCoverPath(coverPath);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/deleteProfilePicture")
    public ResponseEntity<User> deleteProfilePicture(@RequestPart String userLogin) {
        System.out.println(userLogin);
        User user = userRepository.findByLogin(userLogin).orElseThrow(UserNotFoundException::new);
        user.setPictureCoverPath("http://localhost:8080/images/0");
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/changeLogin")
    public ResponseEntity<User> changeLogin(@RequestPart String userLogin, @RequestPart String newLogin) {
        User user = userRepository.findByLogin(userLogin).orElseThrow(UserNotFoundException::new);
        userRepository.findByLogin(newLogin).ifPresent(userWithSameLogin -> {
            throw new UnavailableLoginException();
        });
        user.setLogin(newLogin);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestPart String userLogin, @RequestPart String oldPassword,
                                                 @RequestPart @Size(min = 8, message = "The password should have at least 8 characters.")
                                                 @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).*$", message = "The password should contain at least one letter and one digit.") String newPassword) {
        User user = userRepository.findByLogin(userLogin).orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(oldPassword, user.getPassword()))
            throw new UserIncorrectPasswordException();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/changeName")
    public ResponseEntity<User> changeName(@RequestPart String userLogin,
                                           @RequestPart @Size(min = 2, message = "The name should have at least 2 characters.")
                                           @Pattern(regexp = "^[a-zA-Z]+$", message = "The name should contain only letters.") String newName,
                                           @RequestPart @Size(min = 2, message = "The last name should have at least 2 characters.")
                                           @Pattern(regexp = "^[a-zA-Z]+$", message = "The last name should contain only letters.") String newLastName) {
        User user = userRepository.findByLogin(userLogin).orElseThrow(UserNotFoundException::new);
        user.setName(newName);
        user.setLastName(newLastName);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/changeEmail")
    public ResponseEntity<Object> changeEmail(@RequestPart String userLogin, @RequestPart @Email String newEmail) {
        User user = userRepository.findByLogin(userLogin).orElseThrow(UserNotFoundException::new);
        user.setEmail(newEmail);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public List<User> retrieveUser(@RequestPart String userLogin) {
        List<User> users = userRepository.findByLoginBeginWith(userLogin);
        if (users.isEmpty()) throw new UserNotFoundException();
        return users;
    }

    @GetMapping("/usersAll")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }
}
