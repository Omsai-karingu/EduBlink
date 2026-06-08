package com.example.EduBlink.service;

import com.example.EduBlink.bean.InstructorDto;
import com.example.EduBlink.entity.Instructor;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface InstructorService {

	ResponseEntity<?> updateInstructor(Long id, InstructorDto request);

	ResponseEntity<?> addInstructor(InstructorDto request);

	ResponseEntity<?> deleteInstructor(Long id);

	ResponseEntity<?> getAllInstructor();

	ResponseEntity<?> getInstructorById(Long id);

	ResponseEntity<?> getInstructorCourseById(Long id);

   
}
