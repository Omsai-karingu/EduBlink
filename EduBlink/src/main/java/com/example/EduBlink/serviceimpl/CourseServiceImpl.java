package com.example.EduBlink.serviceimpl;

import com.example.EduBlink.entity.Category;
import com.example.EduBlink.entity.Course;
import com.example.EduBlink.entity.Instructor;
import com.example.EduBlink.Repository.CategoryRepository;
import com.example.EduBlink.Repository.CourseRepository;
import com.example.EduBlink.Repository.InstructorRepository;
import com.example.EduBlink.bean.JwtResponse;
import com.example.EduBlink.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepository repo;

	@Autowired
	InstructorRepository repo1;
	
	@Autowired
	CategoryRepository repo2;
	// ✅ ADD COURSE
	@Override
	public ResponseEntity<?> addCourse(Course request) {
		Category category = repo2.findById(
	            request.getCategory().getId()
	    ).orElseThrow(() -> new RuntimeException("Category not found"));

	    Instructor instructor = repo1.findById(
	            request.getInstructor().getId()
	    ).orElseThrow(() -> new RuntimeException("Instructor not found"));

	    Course course = new Course();
	    course.setCatname(request.getCatname());
	    course.setDuration(request.getDuration());
	    course.setImagepath(request.getImagepath());
	    course.setLessons(request.getLessons());
	    course.setLevel(request.getLevel());
	    course.setPrice(request.getPrice());
	    course.setStar(request.getStar());
	    course.setStudents(request.getStudents());
	    course.setTitle(request.getTitle());

	    // ✅ IMPORTANT PART
	    course.setCategory(category);
	    course.setInstructor(instructor);

	    repo.save(course);

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(new JwtResponse("Success", 201, null, "Course Added Successfully"));
	}

	// ✅ UPDATE COURSE
	@Override
	public ResponseEntity<?> updateCourse(Long id, Course request) {

		Course course = repo.findById(id).orElse(null);

		if (course == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new JwtResponse("Failed", 404, null, "Course Not Found"));
		}

		course.setCatname(request.getCatname());
		course.setDuration(request.getDuration());
		course.setImagepath(request.getImagepath());
		course.setInstructor(request.getInstructor());
		course.setLessons(request.getLessons());
		course.setLevel(request.getLevel());
		course.setPrice(request.getPrice());
		course.setStar(request.getStar());
		course.setStudents(request.getStudents());
		course.setTitle(request.getTitle());

		Course updated = repo.save(course);

		return ResponseEntity.ok(new JwtResponse("Success", 200, null, "Course Updated Successfully"));
	}

	// ✅ DELETE COURSE
	@Override
	public ResponseEntity<?> deleteCourse(Long id) {

		Course course = repo.findById(id).orElse(null);

		if (course == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new JwtResponse("Failed", 404, null, "Course Not Found"));
		}

		repo.delete(course);

		return ResponseEntity.ok(new JwtResponse("Success", 200, null, "Course Deleted Successfully"));
	}

	// ✅ GET ALL COURSES
	@Override
	public ResponseEntity<?> getAllCourse() {
		return ResponseEntity.ok(repo.findAll());
	}


	// ✅ GET INSTRUCTOR BY ID
	@Override
	public ResponseEntity<Course> getCourseById(Long id) {
	    return repo.findById(id)
	            .map(course -> ResponseEntity.ok(course))
	            .orElse(ResponseEntity.notFound().build());
	}
}
