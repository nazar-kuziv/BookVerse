package com.bookverseApp.bookverse.user;

import com.bookverseApp.bookverse.exceptions.SomethingWentWrongException;
import com.bookverseApp.bookverse.exceptions.UserNotFoundException;
import com.bookverseApp.bookverse.jpa.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class FriendsResource {

    private final UserRepository userRepository;

    public FriendsResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/friends")
    public List<User> retrieveFriends(@RequestParam Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.getFriends();
    }

    @PostMapping("/addFriend")
    public ResponseEntity<Object> addFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        User friend = userRepository.findById(friendId).orElseThrow(UserNotFoundException::new);
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        userRepository.save(user);
        userRepository.save(friend);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/areFriends")
    public boolean areFriends(@RequestParam Long userId, @RequestParam Long friendId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        User friend = userRepository.findById(friendId).orElseThrow(UserNotFoundException::new);
        return user.getFriends().contains(friend);
    }

    @DeleteMapping("/deleteFriend")
    private ResponseEntity<Object> deleteFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        User friend = userRepository.findById(friendId).orElseThrow(UserNotFoundException::new);
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        userRepository.save(user);
        userRepository.save(friend);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getFriendsPropositions")
    public List<User> getFriendsPropositions(@RequestParam Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<User> users = userRepository.findAll();
        users.removeAll(user.getFriends());
        users.remove(user);
        Collections.shuffle(users);
        if (users.isEmpty()) throw new SomethingWentWrongException("No propositions");
        return users.subList(0, Math.min(users.size(), 3));
    }
}
