package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/test")
public class TestApi {
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired UserRepository userRepository;
	@PostMapping
	public ResponseEntity<String> Add() throws Exception{
		try {
		User user = User.builder().username("chien").password(passwordEncoder.encode("1")).build();
		userRepository.save(user);
		return ResponseEntity.ok("Them thanh cong");
		}catch (Exception e) {
			throw new Exception("khong dang ki duoc");
		}
	}
	@GetMapping()
	public List<User> users(){
		return userRepository.findAll();
	}
}
