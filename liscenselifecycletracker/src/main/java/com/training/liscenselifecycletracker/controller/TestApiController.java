package com.training.liscenselifecycletracker.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/test")
public class TestApiController {

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	public String ManagementAccess() {
		return "Management Board.";
	}

	@GetMapping("/admin")
	public String adminAccess() {
		return "Admin Board.";
	}
	
	@GetMapping("/tech")
	public String techAccess() {
		return "tech Board.";
	}
}

