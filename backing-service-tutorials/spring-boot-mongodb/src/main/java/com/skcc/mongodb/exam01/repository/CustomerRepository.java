package com.skcc.mongodb.exam01.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.skcc.mongodb.exam01.entity.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
	
	
	
}