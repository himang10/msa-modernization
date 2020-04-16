package com.skcc.mongodb.exam08.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.mongodb.exam08.entity.Customer;
import com.skcc.mongodb.exam08.services.CustomerRepositoryService;

@RestController
@RequestMapping(value = "/exam8")
public class QueryDSLRestController {

	@Autowired
	MongoOperations operations;

	@Autowired
	private CustomerRepositoryService customerRepositoryService;
	
	@GetMapping("/findAllByPredicate")
	public ResponseEntity<Iterator<Customer>> getCustomerList() {
		customerRepositoryService.setUp();
		return new ResponseEntity<>(customerRepositoryService.findAllByPredicate(), HttpStatus.OK);
	}

	

}