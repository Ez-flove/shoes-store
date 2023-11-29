package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.models.User;
import com.example.demo.service.UserServiceIpl;


@Controller
public class AccountController {
	@Autowired private UserServiceIpl userService;
	@Autowired PasswordEncoder passwordEncoder;
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/signup")
	public String getsignup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	@PostMapping("/signup")
	public String postsignup(@ModelAttribute("user") User user,Model model){
		if(!userService.CheckUsername(user.getUsername())) {
			model.addAttribute("error","tai khoan da ton tai");
			return "signup";
		}
		user.setPasswordBase((user.getPassword()));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		userService.saveUser(user);
		return "redirect:/login";
	}

}
