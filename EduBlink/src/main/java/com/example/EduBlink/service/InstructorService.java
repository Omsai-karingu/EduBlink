package com.example.EduBlink.service;

import com.example.EduBlink.entity.Instructor;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface InstructorService {

	ResponseEntity<?> updateInstructor(Long id, Instructor request);

	ResponseEntity<?> addInstructor(Instructor request);

	ResponseEntity<?> deleteInstructor(Long id);

	ResponseEntity<?> getAllInstructor();

	ResponseEntity<?> getInstructorById(Long id);

   
}
