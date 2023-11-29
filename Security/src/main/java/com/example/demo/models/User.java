package com.example.demo.models;



import jakarta.persistence.*;


import lombok.*;

import java.util.List;
import java.util.Objects;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String fullname;
	private String username;
	private String email;
	private String password;
	private String passwordBase;
	private String phone;
	private String address;
	private String role;
	@OneToOne(mappedBy = "user")
	@PrimaryKeyJoinColumn
	private Cart cart;
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", fullname='" + fullname + '\'' +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", role='" + role + '\'' +
				"}";
	}
}
