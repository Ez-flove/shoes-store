package com.example.demo.controller;

import java.security.Principal;
import java.util.List;


import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShiperRepository;
import com.example.demo.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ShiperRepository shiperRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	SizeRepository sizeRepository;
	@GetMapping({"/","/customer"})
	public String home(Principal principal,HttpSession session,Model model) {
		if(principal!=null) {
			String username = principal.getName();
			session.setAttribute("username1", username);
		}
		List<Product> products = productRepository.findAll();
		List<Category> categories = categoryRepository.findAll();
		session.setAttribute("categories",categories);
		model.addAttribute("products",products);
		session.setAttribute("sizes",sizeRepository.findAll());
		return "home";
	}
	@GetMapping("/admin")
	public String admin(Principal principal,HttpSession session) {
		if(principal!=null) {
			String username = principal.getName();
			session.setAttribute("username1", username);
		}
		return "admin";
	}
	@GetMapping("/shipper")
	public String shipper(Principal principal,HttpSession session) {
		if(principal!=null) {
			String username = principal.getName();
			session.setAttribute("username1", username);
		}
		return "homeShipper";
	}
}
