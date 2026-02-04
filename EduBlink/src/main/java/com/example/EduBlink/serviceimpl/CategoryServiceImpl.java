package com.example.EduBlink.serviceimpl;

import com.example.EduBlink.entity.Category;
import com.example.EduBlink.Repository.CategoryRepository;
import com.example.EduBlink.bean.JwtResponse;
import com.example.EduBlink.service.CategoryService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repo;

    @Override
    public ResponseEntity<?> addCategory(Category category) {
        Category cat = new Category();
        cat.setName(category.getName());
        Category categ = repo.save(cat);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new JwtResponse("Success", 201, null, "Category Added Successfully"));
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id, Category category) {
        Optional<Category> optional = repo.findById(id);
        if (optional.isPresent()) {
            Category existing = optional.get();
            existing.setName(category.getName());
            Category updated = repo.save(existing);
            return ResponseEntity.ok(new JwtResponse("Success", 200, null, updated.toString()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new JwtResponse("Failed", 404, null, "Category not found with id: " + id));
        }
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.ok(new JwtResponse("Success", 200, null, "Category deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new JwtResponse("Failed", 404, null, "Category not found with id: " + id));
        }
    }

    @Override
    public ResponseEntity<?> getAllCategories() {
        List<Category> list = repo.findAll();
        return ResponseEntity.ok(list);
    }
    
    @Override
    public ResponseEntity<?> getById(Long id) {
        Optional<Category> optional = repo.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(new JwtResponse("Success", 200, null, optional.get().toString()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new JwtResponse("Failed", 404, null, "Category not found with id: " + id));
        }
    }
}
