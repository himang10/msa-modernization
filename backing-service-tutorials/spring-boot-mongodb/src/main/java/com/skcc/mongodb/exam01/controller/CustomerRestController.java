package com.skcc.mongodb.exam01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.mongodb.exam01.service.CustomerService;
import com.skcc.mongodb.exam01.vo.CustomerVo;

@RestController
@RequestMapping(value = "/exam1")
public class CustomerRestController {
	@Autowired
	private CustomerService service;

	@GetMapping("/customer/{id}")
	public ResponseEntity<CustomerVo> getCustomer(@PathVariable String id) {
		return new ResponseEntity<>(service.getCustomerById(id), HttpStatus.OK);
	}

	@GetMapping("/customer")
	public ResponseEntity<List<CustomerVo>> getCustomerList() {
		return new ResponseEntity<>(service.getCustomerList(), HttpStatus.OK);
	}

	@PostMapping("/customer")
	public ResponseEntity<String> saveCustomer(@RequestBody CustomerVo customerVo) {
		service.saveCustomer(customerVo);
		return new ResponseEntity<>("New customer successfully saved", HttpStatus.OK);
	}

	@PutMapping("/customer")
	public ResponseEntity<String> updateCustomer(@RequestBody CustomerVo customerVo) {
		service.updateCustomer(customerVo);
		return new ResponseEntity<>("Customer successfully updated", HttpStatus.OK);
	}

	@DeleteMapping("/customer")
	public ResponseEntity<String> deleteCustomer(@RequestBody CustomerVo customerVo) {
		service.deleteCustomer(customerVo);
		return new ResponseEntity<>("Customer successfully deleted", HttpStatus.OK);
	}
	
	@PostMapping("/customers")
	public ResponseEntity<String> saveCustomers(@RequestBody List<CustomerVo> customersVo) {
		service.transactional(customersVo);
		return new ResponseEntity<>("New customers successfully saved", HttpStatus.OK);
	}
	
}