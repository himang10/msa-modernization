package com.skcc.mongodb.exam01.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerVo {

	private String id;
	private String name;
	private String email;
	private String pwd;
	private int age;
	// getters and setters
}