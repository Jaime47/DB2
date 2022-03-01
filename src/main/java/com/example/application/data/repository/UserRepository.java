 package com.example.application.data.repository;

import com.example.application.data.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserName(String username);
	Optional<User> findByUserNameAndPassword(String username, String Password);
}