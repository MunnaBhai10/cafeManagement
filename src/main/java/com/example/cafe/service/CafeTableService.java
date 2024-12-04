package com.example.cafe.service;

import com.example.cafe.entity.CafeTables;
import com.example.cafe.repository.CafeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CafeTableService {

    @Autowired
    private CafeTableRepository cafeTableRepository;

    public CafeTables createCafeTable(CafeTables cafeTable) {
        return cafeTableRepository.save(cafeTable);
    }

    public List<CafeTables> getAllCafeTables() {
        return cafeTableRepository.findAll();
    }

    public CafeTables getCafeTableById(Integer tableId) {
        return cafeTableRepository.findById(tableId).orElse(null);
    }
}
