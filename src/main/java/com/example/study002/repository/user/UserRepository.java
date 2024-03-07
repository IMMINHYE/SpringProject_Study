package com.example.study002.repository.user;

import com.example.study002.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
  boolean existsByUsername(String username);
}
