package com.skcc.mongodb.exam01.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
	@Id
	private String id;

	private String name;
	private String email;
	private String pwd;
	
	private int age;
	// getters and setters
}