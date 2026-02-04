package com.example.EduBlink.controller;
import com.example.EduBlink.bean.PaymentRequest;
import com.example.EduBlink.entity.Payment;
import com.example.EduBlink.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    PaymentService service;

	@PostMapping("/create")
	public Payment create(@RequestBody PaymentRequest request) {
		return service.createPayment(request.getUserId(), request.getCourseId(), request.getAmount());
	}

	@PostMapping("/confirm")
	public ResponseEntity<Payment> confirm(@RequestBody PaymentRequest request) {
		return ResponseEntity.ok(service.confirmPayment(request.getOrderId(), request.getPaymentId(), request.getStatus()));
	}

    @GetMapping("/user/{userId}")
    public List<Payment> getByUser(@PathVariable Long userId) {
        return service.getPaymentsByUser(userId);
    }
}
