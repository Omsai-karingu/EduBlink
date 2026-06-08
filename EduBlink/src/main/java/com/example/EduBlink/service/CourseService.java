package com.example.EduBlink.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.EduBlink.bean.CourseRequest;
import com.example.EduBlink.entity.Course;

public interface CourseService {

	ResponseEntity<?> addCourse(CourseRequest request);

	ResponseEntity<?> updateCourse(Long id, CourseRequest request);

	ResponseEntity<?> deleteCourse(Long id);

	ResponseEntity<Course> getCourseById(Long id);
	
	ResponseEntity<List<Course>> getCoursesByCategory(Long categoryId);

	ResponseEntity<?> getAllCourse(int pageNumber, int pageSize);

	ResponseEntity<?> getAllCourseForAdmin();


	
}
