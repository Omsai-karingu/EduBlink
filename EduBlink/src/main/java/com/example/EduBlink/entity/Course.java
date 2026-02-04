package com.example.EduBlink.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String catname;
    private String imagepath;
    private String level;
    private String duration;
    private String title;
    private String star;
    private double price;
    private String lessons;
    private String students;

    // ================= CATEGORY =================
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "category_id")
    private Category category;

    // ================= INSTRUCTOR =================
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    @JsonIgnoreProperties("courses")
    private Instructor instructor;

    // ================= ENROLLMENTS (IMPORTANT) =================
    @OneToMany(
        mappedBy = "course",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Enrollment> enrollments;

}
