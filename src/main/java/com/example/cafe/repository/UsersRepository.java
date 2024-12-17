package com.example.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cafe.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);

	Users findByEmail(String email);

}
