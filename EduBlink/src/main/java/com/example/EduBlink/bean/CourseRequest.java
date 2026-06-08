package com.example.EduBlink.bean;

import org.springframework.web.multipart.MultipartFile;



import lombok.Data;

@Data
public class CourseRequest {
	

	private String level;
	private String duration;
	private String title;
	public String catname;
	private Long price;
	private String lessons;
	private String students;
	public Long categoryId;
	public Long instructorId;
	private MultipartFile image;
}
