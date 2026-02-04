package com.example.EduBlink.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.EduBlink.entity.Course;
import com.example.EduBlink.service.CourseService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CourseController {

	@Autowired
	CourseService service;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/addCourse")
	public ResponseEntity<?> addCourse(@RequestBody Course request) {
		return service.addCourse(request);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/updateCourse/{id}")
	public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody Course request) {
		return service.updateCourse(id, request);
	}


	@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/deleteCourse/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        return service.deleteCourse(id);
    }
	
	@GetMapping("/getAllCourse")
    public ResponseEntity<?> getAllCourse() {
        return service.getAllCourse();
    }
	
	 @GetMapping("/getCourseById/{id}")
	    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
	        return service.getCourseById(id);
	}
}
