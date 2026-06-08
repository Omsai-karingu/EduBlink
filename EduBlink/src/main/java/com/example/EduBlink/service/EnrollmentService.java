package com.example.EduBlink.service;

import com.example.EduBlink.bean.EnrollmentDTO;
import com.example.EduBlink.entity.Enrollment;

import java.util.List;

public interface EnrollmentService {
	
    List<Enrollment> getEnrollmentsByUser(Long userId);

    List<Enrollment> getEnrollmentsByCourse(Long courseId);

    Enrollment updateStatus(Long enrollmentId, String status);

	Enrollment enrollUser(Long userId, Long courseId,String status);
	
	Enrollment updateStatusByUserAndCourse(
	        Long userId,
	        Long courseId,
	        String status
	);
	
	List<EnrollmentDTO> getAllEnrollment();

	
	void deleteEnrollment(Long id);

}
