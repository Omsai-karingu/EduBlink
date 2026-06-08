package com.example.EduBlink.controller;

import com.example.EduBlink.service.EnrollmentService;
import com.razorpay.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")

public class PaymentController {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private final EnrollmentService enrollmentService;

    public PaymentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // ================= CREATE ORDER =================
    @PostMapping("/create-order")
    public Map<String, Object> createOrder(@RequestParam int amount) {

        try {

            RazorpayClient client =
                    new RazorpayClient(keyId, keySecret);

            JSONObject options = new JSONObject();

            options.put("amount", amount * 100);
            options.put("currency", "INR");
            options.put("receipt", "txn_" + System.currentTimeMillis());

            Order order = client.orders.create(options);

            Map<String, Object> response = new HashMap<>();

            response.put("id", order.get("id"));
            response.put("amount", order.get("amount"));
            response.put("currency", order.get("currency"));

            return response;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    // ================= VERIFY + ENROLL =================
    @PostMapping("/verify")
    public String verifyAndEnroll(@RequestBody Map<String, String> data)
            throws Exception {

        String orderId = data.get("razorpay_order_id");
        String paymentId = data.get("razorpay_payment_id");
        String signature = data.get("razorpay_signature");

        Long userId = Long.parseLong(data.get("userId"));
        Long courseId = Long.parseLong(data.get("courseId"));

        JSONObject options = new JSONObject();

        options.put("razorpay_order_id", orderId);
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature", signature);

        boolean valid =
                Utils.verifyPaymentSignature(
                        options,
                        keySecret
                );

        if (!valid) {
            return "Payment verification failed";
        }

        // ✅ SAVE ENROLLMENT
        enrollmentService.enrollUser(userId, courseId, signature);
        enrollmentService.updateStatusByUserAndCourse(
                userId, courseId, "ACTIVE"
        );

        return "Payment Verified & Enrolled";
    }
}
