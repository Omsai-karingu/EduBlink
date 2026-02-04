package com.example.EduBlink.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // ✅ ONLY Long

    private String orderId;
    private String paymentId;
    private double amount;

    // CREATED | SUCCESS | FAILED
    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "id",
            columnDefinition = "BIGINT"
        )
    private Course course;
}
