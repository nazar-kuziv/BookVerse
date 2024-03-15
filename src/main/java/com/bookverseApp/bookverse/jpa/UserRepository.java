package com.bookverseApp.bookverse.jpa;

import com.bookverseApp.bookverse.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    @Query("SELECT u FROM users u WHERE u.login LIKE ?1%")
    List<User> findByLoginBeginWith(String userLogin);
}
