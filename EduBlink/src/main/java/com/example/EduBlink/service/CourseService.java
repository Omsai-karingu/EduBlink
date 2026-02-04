package com.example.EduBlink.service;

import org.springframework.http.ResponseEntity;

import com.example.EduBlink.entity.Course;

public interface CourseService {

	ResponseEntity<?> addCourse(Course request);

	ResponseEntity<?> updateCourse(Long id, Course request);

	ResponseEntity<?> deleteCourse(Long id);

	ResponseEntity<?> getAllCourse();

	ResponseEntity<Course> getCourseById(Long id);

	
}
