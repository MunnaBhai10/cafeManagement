package com.example.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cafe.entity.CafeTables;
import com.example.cafe.enums.TableStatus;

public interface CafeTableRepository extends JpaRepository<CafeTables, Integer> {

	long countByStatus(TableStatus status);


}
