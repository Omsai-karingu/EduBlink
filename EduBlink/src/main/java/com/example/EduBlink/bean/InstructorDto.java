package com.example.EduBlink.bean;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class InstructorDto {
	 private String name;
	    private String bio;
	    private MultipartFile image;
}
