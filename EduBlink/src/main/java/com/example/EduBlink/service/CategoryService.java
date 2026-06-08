package com.example.EduBlink.service;

import com.example.EduBlink.bean.CategoryDTO;
import com.example.EduBlink.entity.Category;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface CategoryService {
	
	ResponseEntity<?> addCategory(CategoryDTO dto);

    ResponseEntity<?> updateCategory(Long id, CategoryDTO dto);

    ResponseEntity<?> deleteCategory(Long id);

    ResponseEntity<?> getAllCategories();

    ResponseEntity<?> getById(Long id);

	

	
    
}
