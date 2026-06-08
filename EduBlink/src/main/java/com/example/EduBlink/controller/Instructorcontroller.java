package com.example.EduBlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EduBlink.bean.InstructorDto;
import com.example.EduBlink.entity.Instructor;
import com.example.EduBlink.service.InstructorService;

@RestController
@RequestMapping("/api")

public class Instructorcontroller {
	
	@Autowired
	InstructorService service;
	
//	 @PreAuthorize("hasRole('ADMIN')")
		@PostMapping("/admin/addInstructor")
		public ResponseEntity<?> addInstructor(@ModelAttribute InstructorDto request) {
			return service.addInstructor(request);
		}
	    
//	    @PreAuthorize("hasRole('ADMIN')")
		@PostMapping("/admin/updateInstructor/{id}")
		public ResponseEntity<?> updateInstructor(@PathVariable Long id, @ModelAttribute InstructorDto request) {
			return service.updateInstructor(id, request);
		}
	    
//	    @PreAuthorize("hasRole('ADMIN')")
	    @PostMapping("/admin/deleteInstructor/{id}")
	    public ResponseEntity<?> deleteInstructor(@PathVariable Long id) {
	        return service.deleteInstructor(id);
	    }
	    
	    @GetMapping("/instructors/getAllInstructor")
	    public ResponseEntity<?> getAllInstructor() {
	        return service.getAllInstructor();
	    }
	    
	    @GetMapping("/instructors/getInstructorById/{id}")
	    public ResponseEntity<?> getInstructorById(@PathVariable Long id) {
	        return service.getInstructorById(id);
	    }
	    
	    @GetMapping("/instructors/getInstructorCourseById/{id}")
	    public ResponseEntity<?> getInstructorCourseById(@PathVariable Long id){
	    	return service.getInstructorCourseById(id);
	    }
}
