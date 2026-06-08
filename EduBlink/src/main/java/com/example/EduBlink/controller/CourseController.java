package com.example.EduBlink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EduBlink.Repository.CourseRepository;
import com.example.EduBlink.bean.CourseRequest;
import com.example.EduBlink.entity.Course;
import com.example.EduBlink.service.CourseService;

@RestController
@RequestMapping("/api")

public class CourseController {

	@Autowired
	CourseService service;

	@Autowired
	CourseRepository repo;

//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/addCourse")
	public ResponseEntity<?> addCourse(@ModelAttribute CourseRequest request) {
	    return service.addCourse(request);
	}
//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/updateCourse/{id}")
	public ResponseEntity<?> updateCourse(@PathVariable Long id, @ModelAttribute CourseRequest request) {
		return service.updateCourse(id, request);
	}

//	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/admin/deleteCourse/{id}")
	public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
		return service.deleteCourse(id);
	}

	@GetMapping("/getAllCourse")
	public ResponseEntity<?> getAllCourse(
			@RequestParam(defaultValue = "0") int pageNumber, // start from 0
			@RequestParam(defaultValue = "5") int pageSize // 10 per page
	) {
		return service.getAllCourse(pageNumber, pageSize);
	}
	
	@GetMapping("/admin/getAllCourse")
	public ResponseEntity<?> getAllCourseForAdmin() {
	    return ResponseEntity.ok(repo.findAll());
	}

	@GetMapping("/getCourseById/{id}")
	public ResponseEntity<?> getCourseById(@PathVariable Long id) {
		return service.getCourseById(id);
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<List<Course>> getByCategory(@PathVariable Long id) {

		return service.getCoursesByCategory(id);
	}

}
