package com.example.EduBlink.serviceimpl;

import com.example.EduBlink.Repository.*;
import com.example.EduBlink.entity.*;
import com.example.EduBlink.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentServiceImpl
        implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private EnrollmentRepository enrollRepo;

    @Override
    public Payment createPayment(
            Long userId,
            Long courseId,
            double amount) {

        User user = userRepo.findById(userId)
            .orElseThrow(() ->
                new RuntimeException("User not found"));

        Course course = courseRepo.findById(courseId)
            .orElseThrow(() ->
                new RuntimeException("Course not found"));

        Payment p = new Payment();

        p.setOrderId("ORD-" + System.currentTimeMillis());
        p.setAmount(amount);
        p.setStatus("CREATED");
        p.setUser(user);
        p.setCourse(course);

        return paymentRepo.save(p);
    }

    @Override
    public Payment confirmPayment(
            String orderId,
            String paymentId,
            String status) {

        Payment p = paymentRepo
            .findByOrderId(orderId)
            .orElseThrow(() ->
                new RuntimeException("Payment not found"));

        p.setPaymentId(paymentId);
        p.setStatus(status);

        if ("SUCCESS".equals(status)) {

            Enrollment e =
                enrollRepo
                    .findByUserIdAndCourseId(
                        p.getUser().getId(),
                        p.getCourse().getId()
                    )
                    .orElseGet(() -> {

                        Enrollment en =
                            new Enrollment();

                        en.setUser(p.getUser());
                        en.setCourse(p.getCourse());
                        en.setStatus("ACTIVE");

                        return enrollRepo.save(en);
                    });

            e.setStatus("ACTIVE");
        }

        return paymentRepo.save(p);
    }
}
