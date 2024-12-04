package com.example.cafe.service;

import com.example.cafe.entity.Users;
import com.example.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users createUser(Users user) {
        return userRepository.save(user);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Users findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
