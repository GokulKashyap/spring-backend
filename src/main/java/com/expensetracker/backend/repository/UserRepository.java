package com.expensetracker.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker.backend.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
     Optional<User> findByEmail(String email);
}
