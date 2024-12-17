package com.example.cafe.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.cafe.entity.MenuItem;
import com.example.cafe.repository.MenuItemRepository;

@Service
public class MenuItemService {

	@Autowired
	private MenuItemRepository menuItemRepository;
	
	
	String uploadPath = System.getProperty("user_dir")+ "/src/uploads/";
	
	
	    public MenuItem addMenuItem(MenuItem menuItem) {
	        return menuItemRepository.save(menuItem);
	    }

	  
	    public MenuItem getMenuItemById(Integer itemId) {
	        return menuItemRepository.findById(itemId)
	                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + itemId));
	    }

	    
	    public List<MenuItem> getAllMenuItems() {
	        return menuItemRepository.findAll();
	    }

	 
	    public MenuItem updateMenuItem(Integer itemId, MenuItem menuItem) {
	        
	    	MenuItem existingItem = getMenuItemById(itemId);
	        
	    	existingItem.setItemName(menuItem.getItemName());
	        existingItem.setItemPrice(menuItem.getItemPrice());
	        return menuItemRepository.save(existingItem);
	    }

	 
	    public void deleteMenuItem(Integer itemId) {
	        MenuItem existingItem = getMenuItemById(itemId);
	        menuItemRepository.delete(existingItem);
	    }
	    
	    public void uploadImage(MultipartFile file) throws IOException {
	        if (!file.isEmpty()) {
	            // Ensure upload directory exists
	            Path uploadDir = Paths.get(uploadPath);
	            if (!Files.exists(uploadDir)) {
	                Files.createDirectories(uploadDir);
	            }

	            // Sanitize and generate a unique file name
	            String originalName = file.getOriginalFilename();
	            String sanitizedFilename = originalName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
	            Path filePath = uploadDir.resolve(sanitizedFilename);

	            // Write file to the upload path
	            Files.write(filePath, file.getBytes());

	            System.out.println("File uploaded to: " + filePath);
	        } else {
	            throw new IllegalArgumentException("File is empty!");
	        }
	    }

	
	
}
