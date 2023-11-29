package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
	List<User> findAllByRole(String role);
}
