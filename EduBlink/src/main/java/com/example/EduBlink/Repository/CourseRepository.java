package com.example.EduBlink.Repository;

import com.example.EduBlink.entity.Course;
import com.example.EduBlink.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByCategory(Category category);
}
