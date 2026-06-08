package com.example.EduBlink.bean;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class EnrollmentDTO {
		
	 private Long id;
	 private Long userId;
	 private Long courseId;
	 private String status;
	 @CreationTimestamp
	    @Column(nullable = false, updatable = false)
	    private LocalDateTime enrolledAt;
}
