package com.example.EduBlink.serviceimpl;

import com.example.EduBlink.Repository.CategoryRepository;
import com.example.EduBlink.Repository.CourseRepository;
import com.example.EduBlink.Repository.InstructorRepository;
import com.example.EduBlink.bean.CourseRequest;
import com.example.EduBlink.bean.JwtResponse;
import com.example.EduBlink.entity.Category;
import com.example.EduBlink.entity.Course;
import com.example.EduBlink.entity.Instructor;
import com.example.EduBlink.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepository repo;

	@Autowired
	InstructorRepository repo1;
	
	@Autowired
	CategoryRepository repo2;
	
	
	@Autowired
	StorageService storageService;
	// ✅ ADD COURSE
	@Override
	public ResponseEntity<?> addCourse(CourseRequest request) {
		Category category = repo2.findById(
	            request.getCategoryId()
	    ).orElseThrow(() -> new RuntimeException("Category not found"));

	    Instructor instructor = repo1.findById(
	            request.getInstructorId()
	    ).orElseThrow(() -> new RuntimeException("Instructor not found"));

	    Course course = new Course();
	    if (request.getImage() != null && !request.getImage().isEmpty()) {

	        String fileName = storageService.saveFile(request.getImage());

	        String imageUrl = "http://localhost:8080/uploads/" + fileName;

	        course.setImagePath(imageUrl);
	    }

	    course.setDuration(request.getDuration());
	   
	    course.setLessons(request.getLessons());
	    course.setLevel(request.getLevel());
	    course.setPrice(request.getPrice());
	   
	    course.setStudents(request.getStudents());
	    course.setTitle(request.getTitle());

	    // ✅ IMPORTANT PART
	    course.setCategory(category);
	    course.setInstructor(instructor);

	    repo.save(course);

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(new JwtResponse("Success", 201, null, "Course Added Successfully",null,null));
	}

	// ✅ UPDATE COURSE
	@Override
	public ResponseEntity<?> updateCourse(Long id, CourseRequest request) {

	    Course course = repo.findById(id).orElse(null);

	    if (course == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	            .body(new JwtResponse("Failed", 404, null, "Course Not Found", null, null));
	    }

	    // ===== BASIC FIELDS =====
	    course.setTitle(request.getTitle());
	    course.setPrice(request.getPrice());
	    course.setLevel(request.getLevel());
	    course.setDuration(request.getDuration());
	    
	    course.setLessons(request.getLessons());
	    
	 
	    course.setStudents(request.getStudents());
	    
	    System.out.println("Title: " + request.getTitle());
	    System.out.println("Price: " + request.getPrice());
	    System.out.println("Level: " + request.getLevel());

	    // ===== CATEGORY =====
	    if (request.getCategoryId() != null) {
	    	 Category category = repo2.findById(request.getCategoryId())
	                 .orElseThrow(() -> new RuntimeException("Category not found"));
	        course.setCategory(category);
	    }

	    // ===== INSTRUCTOR =====
	    if (request.getInstructorId() != null) {
	    	 Instructor instructor = repo1.findById(request.getInstructorId())
	                 .orElseThrow(() -> new RuntimeException("Instructor not found"));
	    	 
	        course.setInstructor(instructor);
	    }
	    
	    if(request.getImage() != null && !request.getImage().isEmpty()) {
	    	 String fileName = storageService.saveFile(request.getImage());
		        course.setImagePath("/uploads/"+fileName);
	    }
	    repo.save(course);

	    return ResponseEntity.ok(
	        new JwtResponse("Success", 200, null, "Course Updated Successfully", null, null)
	    );
	}
	// ✅ DELETE COURSE
	@Override
	public ResponseEntity<?> deleteCourse(Long id) {

		Course course = repo.findById(id).orElse(null);

		if (course == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new JwtResponse("Failed", 404, null, "Course Not Found",null,null));
		}

		repo.delete(course);

		return ResponseEntity.ok(new JwtResponse("Success", 200, null, "Course Deleted Successfully",null,null));
	}

	// ✅ GET ALL COURSES
	@Override
	public ResponseEntity<?> getAllCourse(int pageNumber, int pageSize) {

	    Pageable pageable = PageRequest.of(pageNumber, pageSize);

	    Page<Course> page = repo.findAll(pageable); // ✅ USE pageable

	    HashMap<String, Object> response = new HashMap<String ,Object>();

	    response.put("courses", page.getContent());      // data
	    response.put("currentPage", page.getNumber());   // page index
	    response.put("totalItems", page.getTotalElements());
	    response.put("totalPages", page.getTotalPages());
	    response.put("pageSize", page.getSize());

	    return ResponseEntity.ok(response);
	}
	
	@Override
    public ResponseEntity<?> getAllCourseForAdmin() {

        List<Course> list = repo.findAll();

        return ResponseEntity.ok(list);
    }
	

	// ✅ GET INSTRUCTOR BY ID
	@Override
	public ResponseEntity<Course> getCourseById(Long id) {
	    return repo.findById(id)
	            .map(course -> ResponseEntity.ok(course))
	            .orElse(ResponseEntity.notFound().build());
	}

	@Override
	public ResponseEntity<List<Course>> getCoursesByCategory(Long categoryId) {
		List<Course> courses = repo.findByCategoryId(categoryId);

	    return ResponseEntity.ok(courses);
	}
}
