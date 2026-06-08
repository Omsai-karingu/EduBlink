package com.example.EduBlink.bean;



import java.util.List;

import com.example.EduBlink.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	
	private String status;
	private int statuscode;
	private String token;
	private String message;
	private  List<String>  role;
	private User user;
}
