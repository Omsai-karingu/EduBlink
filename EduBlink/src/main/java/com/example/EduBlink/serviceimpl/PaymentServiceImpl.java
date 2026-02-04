package com.example.EduBlink.serviceimpl;

import com.example.EduBlink.Repository.*;
import com.example.EduBlink.entity.*;
import com.example.EduBlink.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Payment createPayment(Long userId, Long courseId, double amount) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Payment payment = new Payment();
        payment.setOrderId("ORD-" + System.currentTimeMillis());
        payment.setAmount(amount);
        payment.setStatus("CREATED");
        payment.setUser(user);
        payment.setCourse(course);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment confirmPayment(String orderId, String paymentId, String status) {

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setPaymentId(paymentId);
        payment.setStatus(status);

        if ("SUCCESS".equalsIgnoreCase(status)) {

            Enrollment enrollment = enrollmentRepository
                    .findByUserIdAndCourseId(
                            payment.getUser().getId(),
                            payment.getCourse().getId()   // int ✅
                    )
                    .orElseThrow(() -> new RuntimeException("Enrollment not found"));

            enrollment.setStatus("ACTIVE");
            enrollmentRepository.save(enrollment);
        }

        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPaymentsByUser(Long userId) {
        return paymentRepository.findByUserId(userId);
    }
}

