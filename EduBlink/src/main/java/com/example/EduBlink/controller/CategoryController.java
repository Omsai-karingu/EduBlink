package com.example.EduBlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.EduBlink.entity.Category;
import com.example.EduBlink.service.CategoryService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService service;

    // ✅ Only admin can add category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/addCategory")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        return service.addCategory(category);
    }

    // ✅ Only admin can update category using POST
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/updateCategory/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return service.updateCategory(id, category);
    }

    // ✅ Only admin can delete category using POST
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return service.deleteCategory(id);
    }

    // ✅ Get all categories (any authenticated user)
    @GetMapping("/getAllCategories")
    public ResponseEntity<?> getAllCategories() {
        return service.getAllCategories();
    }

    // ✅ Get category by ID (any authenticated user)
    @GetMapping("/getCategory/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
