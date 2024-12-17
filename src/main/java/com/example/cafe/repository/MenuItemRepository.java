package com.example.cafe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cafe.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
	
	Optional<MenuItem> findById(Integer menuItemId);

}
