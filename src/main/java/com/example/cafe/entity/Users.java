package com.example.cafe.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.example.cafe.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Users {
@Id	
@GeneratedValue(strategy= GenerationType.IDENTITY)
private Integer userId;

//@Column(unique= true)
private String userName;

private String fullName;

//@Column(unique= true)
private String email;
private String password;

@CreationTimestamp
private LocalDate createdAt;

@Enumerated
private Role role;

public Integer getUserId() {
	return userId;
}

public void setUserId(Integer userId) {
	this.userId = userId;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
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
}
