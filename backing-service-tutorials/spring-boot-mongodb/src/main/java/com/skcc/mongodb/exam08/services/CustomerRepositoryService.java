/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.skcc.mongodb.exam08.services;


import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.skcc.mongodb.exam08.entity.Customer;
import com.skcc.mongodb.exam08.repository.CustomerQuerydslRepository;
import com.skcc.mongodb.exam08.entity.QCustomer;


/**
 * @author Christoph Strobl
 */
@Service
public class CustomerRepositoryService {

	@Autowired CustomerQuerydslRepository repository;
	@Autowired MongoOperations operations;

	Customer dave, oliver, carter;

	public void setUp() {

		repository.deleteAll();

		dave = repository.save(new Customer("Dave", "Matthews"));
		oliver = repository.save(new Customer("Oliver August", "Matthews"));
		carter = repository.save(new Customer("Carter", "Beauford"));
	}

	public Iterator<Customer> findAllByPredicate() {
		Iterator<Customer> iterator = repository.findAll(QCustomer.customer.lastname.eq("Matthews")).iterator();
		return iterator; 
	}

}
