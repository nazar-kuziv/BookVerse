package com.bookverseApp.bookverse;

import com.bookverseApp.bookverse.exceptions.UserNotFoundException;
import com.bookverseApp.bookverse.jpa.UserRepository;
import com.bookverseApp.bookverse.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BookverseApplication implements CommandLineRunner {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookverseApplication.class, args);
    }

    @Override
    public void run(String... args) {
        User user = userRepository.findByLogin("llutsefer1").orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode("qwertyqwerty1"));
        userRepository.save(user);
    }

    public BookverseApplication(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
}
