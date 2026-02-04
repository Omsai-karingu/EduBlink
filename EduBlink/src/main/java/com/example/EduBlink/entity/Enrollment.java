package com.example.EduBlink.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // ✅ ONLY Long

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference
    private Course course;

    // PENDING | ACTIVE | CANCELLED
    private String status;

    private LocalDateTime enrolledAt = LocalDateTime.now();

	public void setStatus(String status2) {
		// TODO Auto-generated method stub
		
	}
}
