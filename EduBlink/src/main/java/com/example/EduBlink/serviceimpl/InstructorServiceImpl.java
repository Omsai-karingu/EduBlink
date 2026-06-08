package com.example.EduBlink.serviceimpl;

import com.example.EduBlink.Repository.InstructorRepository;
import com.example.EduBlink.bean.InstructorDto;
import com.example.EduBlink.bean.JwtResponse;
import com.example.EduBlink.entity.Instructor;
import com.example.EduBlink.service.InstructorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImpl implements InstructorService {

	@Autowired
	private InstructorRepository repo;
	
	@Autowired
	StorageService storageService;

	// ✅ ADD INSTRUCTOR
	@Override
	public ResponseEntity<?> addInstructor(InstructorDto request) {

		Instructor instructor = new Instructor();
		instructor.setName(request.getName());
		instructor.setBio(request.getBio());
		
		if(request.getImage() != null && !request.getImage().isEmpty()) {
			  String fileName = storageService.saveFile(request.getImage());

		        String imageUrl = "http://localhost:8080/uploads/" + fileName;
		        instructor.setProfileImage("/uploads/"+fileName);
		}
		
		 repo.save(instructor); // 🔥 IMPORTANT

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new JwtResponse("Success", 201, null, "Instructor Added Successfully",null,null));
	}

	// ✅ UPDATE INSTRUCTOR
	@Override
	public ResponseEntity<?> updateInstructor(Long id, InstructorDto request) {

		Optional<Instructor> optionalInstructor = repo.findById(id);

		if (optionalInstructor.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new JwtResponse("Failed", 404, null, "Instructor Not Found",null,null));
		}

		Instructor instructor = optionalInstructor.get();
		instructor.setName(request.getName());
		instructor.setBio(request.getBio());
		
		if(request.getImage() != null && !request.getImage().isEmpty()) {
			  String fileName = storageService.saveFile(request.getImage());

		        String imageUrl = "http://localhost:8080/uploads/" + fileName;
		        instructor.setProfileImage("/uploads/"+fileName);
		}
		
		Instructor updatedInstructor = repo.save(instructor);

		return ResponseEntity.ok(new JwtResponse("Success", 200, null, "Instructor Updated Successfully",null,null));
	}

	// ✅ DELETE INSTRUCTOR
	@Override
	public ResponseEntity<?> deleteInstructor(Long id) {

		if (!repo.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new JwtResponse("Failed", 404, null, "Instructor Not Found",null,null));
		}

		repo.deleteById(id);

		return ResponseEntity.ok(new JwtResponse("Success", 200, null, "Instructor Deleted Successfully",null,null));
	}

	// ✅ GET ALL INSTRUCTORS
	@Override
	public ResponseEntity<?> getAllInstructor() {
		return ResponseEntity.ok(repo.findAll());
	}


	// ✅ GET INSTRUCTOR BY ID
	@Override
	public ResponseEntity<Instructor> getInstructorById(Long id) {
	    return repo.findById(id)
	            .map(instructor -> ResponseEntity.ok(instructor))
	            .orElse(ResponseEntity.notFound().build());
	}

	@Override
	public ResponseEntity<?> getInstructorCourseById(Long id) {
		// TODO Auto-generated method stub
		
		Optional<Instructor> optionalInstructor = repo.findById(id);
		
		if(optionalInstructor.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new JwtResponse(
							"Failed",
							404, null, "Instructor Not Found",null,null
							));
			
		}
		
		Instructor instructor = optionalInstructor.get();
		
		return ResponseEntity.ok(instructor);
	}

}
