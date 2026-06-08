package com.example.EduBlink.bean;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CategoryDTO {
	private String name;
	private MultipartFile image;
}
