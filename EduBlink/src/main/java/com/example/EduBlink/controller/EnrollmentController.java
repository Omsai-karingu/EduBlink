package com.example.EduBlink.controller;
import com.example.EduBlink.bean.EnrollmentDTO;
import com.example.EduBlink.entity.Enrollment;
import com.example.EduBlink.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class EnrollmentController {

    @Autowired
    private EnrollmentService service;

    @PostMapping("/enroll")
    public Enrollment enroll(@RequestBody EnrollmentDTO request) {
        return service.enrollUser(
            request.getUserId(),
            request.getCourseId(),
            request.getStatus()
        );
    }


    @GetMapping("/user/{userId}")
    public List<Enrollment> getByUser(@PathVariable Long userId) {
        return service.getEnrollmentsByUser(userId);
    }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> getByCourse(@PathVariable Long courseId) {
        return service.getEnrollmentsByCourse(courseId);
    }
    
    @PostMapping("/confirm-enrollment")
    public Enrollment confirmEnrollment(
            @RequestParam Long userId,
            @RequestParam Long courseId
    ) {

        Enrollment enrollment =
                service.enrollUser(userId, courseId, null);

        return service.updateStatus(
                enrollment.getId(),
                "ACTIVE"
        );
    }

 // ✅ ADMIN: Get All Enrollments
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/enrollments")
    public List<EnrollmentDTO> getAllEnrollments() {
        return service.getAllEnrollment();
    }
    
    
//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/updateEnrollment/{id}")
    public Enrollment updateEnrollment(
            @PathVariable Long id,
            @RequestBody EnrollmentDTO request
    ) {
        return service.updateStatus(id, request.getStatus());
    }
    
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/deleteEnrollment/{id}")
    public String deleteEnrollment(@PathVariable Long id) {
    	service.deleteEnrollment(id);
    	return "Enrollment deleted Successfully";
    }
}
