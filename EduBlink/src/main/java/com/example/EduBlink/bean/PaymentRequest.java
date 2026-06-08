package com.example.EduBlink.bean;

import lombok.Data;

@Data
public class PaymentRequest {

    private Long userId;
    private Long courseId;
    private double amount;

    // For verify
    private String orderId;
    private String razorpayPaymentId;
}
