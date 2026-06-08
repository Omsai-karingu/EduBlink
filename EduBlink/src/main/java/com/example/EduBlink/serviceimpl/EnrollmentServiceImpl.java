package com.example.EduBlink.serviceimpl;

import com.example.EduBlink.Repository.*;
import com.example.EduBlink.bean.EnrollmentDTO;
import com.example.EduBlink.entity.*;
import com.example.EduBlink.service.EnrollmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;


    // ================= ENROLL =================
    @Override
    public Enrollment enrollUser(Long userId, Long courseId, String status) {

        return enrollmentRepository
            .findByUserIdAndCourseId(userId, courseId)
            .orElseGet(() -> {

                User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

                Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

                Enrollment e = new Enrollment();

                e.setUser(user);
                e.setCourse(course);

                // Default + normalize
                String finalStatus =
                    (status != null && !status.isBlank())
                        ? status.trim().toUpperCase()
                        : "PENDING";

                e.setStatus(finalStatus);

                return enrollmentRepository.save(e);
            });
    }


    // ================= GET BY USER =================
    @Override
    public List<Enrollment> getEnrollmentsByUser(Long userId) {
        return enrollmentRepository.findByUserId(userId);
    }


    // ================= GET BY COURSE =================
    @Override
    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }


    // ================= UPDATE BY ID =================
    @Override
    public Enrollment updateStatus(Long enrollmentId, String status) {

        Enrollment enrollment =
            enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() ->
                    new RuntimeException("Enrollment not found"));
        
        
        enrollment.setStatus(normalizeStatus(status));

        return enrollmentRepository.save(enrollment);
    }


    // ================= UPDATE BY USER + COURSE =================
    @Override
    public Enrollment updateStatusByUserAndCourse(
            Long userId,
            Long courseId,
            String status
    ) {

        Enrollment enrollment =
            enrollmentRepository
                .findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() ->
                    new RuntimeException("Enrollment not found"));

        enrollment.setStatus(normalizeStatus(status));

        return enrollmentRepository.save(enrollment);
    }


    // ================= DELETE =================
    @Override
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }


    // ================= GET ALL (DTO) =================
    @Override
    public List<EnrollmentDTO> getAllEnrollment() {

        return enrollmentRepository.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }


    // ================= HELPERS =================

    // Convert Entity → DTO
    private EnrollmentDTO toDTO(Enrollment e) {

        EnrollmentDTO dto = new EnrollmentDTO();

        dto.setId(e.getId());
        dto.setUserId(e.getUser().getId());
        dto.setCourseId(e.getCourse().getId());
        dto.setStatus(e.getStatus());
        dto.setEnrolledAt(e.getEnrolledAt());
        return dto;
    }


    // Normalize + validate status
    private String normalizeStatus(String status) {

        if (status == null) {
            throw new RuntimeException("Status is required");
        }

        String normalized = status.trim().toUpperCase();

        if (!normalized.equals("ACTIVE")
                && !normalized.equals("CANCELLED")
                && !normalized.equals("PENDING")) {

            throw new RuntimeException("Invalid status: " + status);
        }

        return normalized;
    }
}