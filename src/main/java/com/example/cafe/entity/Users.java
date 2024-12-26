package com.example.cafe.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.example.cafe.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Users {
@Id	
@GeneratedValue(strategy= GenerationType.IDENTITY)
private Long userId;

private String fullName;

private String username;

private String email;


private String password;

@CreationTimestamp
private LocalDate createdAt;

@Enumerated(EnumType.STRING)
private Role role;

@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Orders> orders = new ArrayList<>();



public Long getUserId() {
	return userId;
}

public void setUserId(Long userId) {
	this.userId = userId;
}


public LocalDate getCreatedAt() {
	return createdAt;
}

public void setCreatedAt(LocalDate createdAt) {
	this.createdAt = createdAt;
}
public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}



public String getFullName() {
	return fullName;
}

public void setFullName(String fullName) {
	this.fullName = fullName;
}

public Role getRole() {
	return role;
}

public void setRole(Role role) {
	this.role = role;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}
}
