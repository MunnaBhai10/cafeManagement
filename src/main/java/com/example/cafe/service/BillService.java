package com.example.cafe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cafe.repository.BillsRepository;

@Service
public class BillService {

	@Autowired
	private BillsRepository billRepository;
	
	
	
	
}
