package com.example.EduBlink.controller;
import com.example.EduBlink.bean.EnrollmentRequest;
import com.example.EduBlink.entity.Enrollment;
import com.example.EduBlink.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class EnrollmentController {

    @Autowired
    private EnrollmentService service;

    @PostMapping("/enroll")
    public Enrollment enroll(@RequestBody EnrollmentRequest request) {
        return service.enrollUser(
            request.getUserId(),
            request.getCourseId()
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
}
