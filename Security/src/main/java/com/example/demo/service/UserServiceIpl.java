package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceIpl implements UserService{
	@Autowired UserRepository repository;
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return repository.save(user);
	}
	@Override
	public boolean CheckUsername(String username) {
		User userCheck = repository.findByUsername(username);
		if(userCheck!=null) {
			return false;
		}
		return true;
	}
}
