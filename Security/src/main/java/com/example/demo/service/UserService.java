package com.example.demo.service;

import com.example.demo.models.User;

public interface UserService {
	User saveUser(User user);
	boolean CheckUsername(String username);
}
