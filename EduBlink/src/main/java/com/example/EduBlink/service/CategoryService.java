package com.example.EduBlink.service;

import com.example.EduBlink.entity.Category;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface CategoryService {
	
	ResponseEntity<?> addCategory(Category category);

    ResponseEntity<?> updateCategory(Long id, Category category);

    ResponseEntity<?> deleteCategory(Long id);

    ResponseEntity<?> getAllCategories();

    ResponseEntity<?> getById(Long id);

	

	
    
}
