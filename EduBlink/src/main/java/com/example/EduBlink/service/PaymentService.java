package com.example.EduBlink.service;

import com.example.EduBlink.entity.Payment;

import java.util.List;

public interface PaymentService {

    Payment createPayment(Long userId, Long courseId, double amount);

    Payment confirmPayment(String orderId, String paymentId, String status);

    List<Payment> getPaymentsByUser(Long userId);
}
