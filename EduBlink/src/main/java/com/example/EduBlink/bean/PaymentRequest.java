package com.example.EduBlink.bean;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long userId;
    private Long courseId;
    private double amount;
    
    private String orderId;
    private String paymentId;
    private String status;
}
