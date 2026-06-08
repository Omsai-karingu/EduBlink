package com.example.EduBlink.serviceimpl;

import com.example.EduBlink.Repository.CategoryRepository;
import com.example.EduBlink.bean.CategoryDTO;
import com.example.EduBlink.bean.JwtResponse;
import com.example.EduBlink.entity.Category;
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
    public ResponseEntity<?> addCategory(CategoryDTO dto) {

        Category cat = new Category();
        cat.setName(dto.getName());
        
        Category saved = repo.save(cat);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id, CategoryDTO dto) {

        Optional<Category> optional = repo.findById(id);

        if (optional.isPresent()) {

            Category existing = optional.get();
            existing.setName(dto.getName());

            Category updated = repo.save(existing);

            return ResponseEntity.ok(updated);

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category not found with id: " + id);
        }
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {

        if (repo.existsById(id)) {

            repo.deleteById(id);

            return ResponseEntity.ok("Category deleted successfully");

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category not found with id: " + id);
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

            return ResponseEntity.ok(optional.get());

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category not found with id: " + id);
        }
    }
}
