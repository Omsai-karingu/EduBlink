package com.example.EduBlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EduBlink.entity.Instructor;
import com.example.EduBlink.service.InstructorService;

@RestController
@RequestMapping("/api/instructors")
@CrossOrigin("*")
public class Instructorcontroller {
	
	@Autowired
	InstructorService service;
	
	 @PreAuthorize("hasRole('ADMIN')")
		@PostMapping("/admin/addInstructor")
		public ResponseEntity<?> addInstructor(@RequestBody Instructor request) {
			return service.addInstructor(request);
		}
	    
	    @PreAuthorize("hasRole('ADMIN')")
		@PostMapping("/admin/updateInstructor/{id}")
		public ResponseEntity<?> updateInstructor(@PathVariable Long id, @RequestBody Instructor request) {
			return service.updateInstructor(id, request);
		}
	    
	    @PreAuthorize("hasRole('ADMIN')")
	    @PostMapping("/admin/deleteInstructor/{id}")
	    public ResponseEntity<?> deleteInstructor(@PathVariable Long id) {
	        return service.deleteInstructor(id);
	    }
	    
	    @GetMapping("/getAllInstructor")
	    public ResponseEntity<?> getAllInstructor() {
	        return service.getAllInstructor();
	    }
	    
	    @GetMapping("/getInstructorById/{id}")
	    public ResponseEntity<?> getInstructorById(@PathVariable Long id) {
	        return service.getInstructorById(id);
	    }
}
