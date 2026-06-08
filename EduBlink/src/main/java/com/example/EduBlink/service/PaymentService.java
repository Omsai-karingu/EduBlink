package com.example.EduBlink.service;

import com.example.EduBlink.entity.Payment;

public interface PaymentService {

    Payment createPayment(Long userId, Long courseId, double amount);

    Payment confirmPayment(
        String orderId,
        String paymentId,
        String status
    );
}
