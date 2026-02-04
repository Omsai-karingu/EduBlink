package com.example.EduBlink.serviceimpl;

import com.example.EduBlink.Repository.InstructorRepository;
import com.example.EduBlink.bean.JwtResponse;
import com.example.EduBlink.entity.Instructor;
import com.example.EduBlink.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImpl implements InstructorService {

	@Autowired
	private InstructorRepository repo;

	// ✅ ADD INSTRUCTOR
	@Override
	public ResponseEntity<?> addInstructor(Instructor request) {

		Instructor instructor = new Instructor();
		instructor.setName(request.getName());
		instructor.setBio(request.getBio());
		instructor.setProfileImage(request.getProfileImage());
		
		Instructor savedInstructor = repo.save(instructor); // 🔥 IMPORTANT

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new JwtResponse("Success", 201, null, "Instructor Added Successfully"));
	}

	// ✅ UPDATE INSTRUCTOR
	@Override
	public ResponseEntity<?> updateInstructor(Long id, Instructor request) {

		Optional<Instructor> optionalInstructor = repo.findById(id);

		if (optionalInstructor.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new JwtResponse("Failed", 404, null, "Instructor Not Found"));
		}

		Instructor instructor = optionalInstructor.get();
		instructor.setName(request.getName());
		instructor.setBio(request.getBio());
		instructor.setProfileImage(request.getProfileImage());
		
		Instructor updatedInstructor = repo.save(instructor);

		return ResponseEntity.ok(new JwtResponse("Success", 200, null, "Instructor Updated Successfully"));
	}

	// ✅ DELETE INSTRUCTOR
	@Override
	public ResponseEntity<?> deleteInstructor(Long id) {

		if (!repo.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new JwtResponse("Failed", 404, null, "Instructor Not Found"));
		}

		repo.deleteById(id);

		return ResponseEntity.ok(new JwtResponse("Success", 200, null, "Instructor Deleted Successfully"));
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

}
