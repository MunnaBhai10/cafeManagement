package com.example.cafe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cafe.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    Optional<Users> findByEmail(String email);

	Users findByUserId(Long id);


}
