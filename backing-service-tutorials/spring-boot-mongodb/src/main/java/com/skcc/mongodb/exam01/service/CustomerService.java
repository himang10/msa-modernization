package com.skcc.mongodb.exam01.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skcc.mongodb.exam01.entity.Customer;
import com.skcc.mongodb.exam01.repository.CustomerRepository;
import com.skcc.mongodb.exam01.vo.CustomerVo;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository repository;
	
	public List<CustomerVo> getCustomerList() {
		return repository.findAll().stream().map(c -> {
			CustomerVo vo = new CustomerVo();
			vo.setId(c.getId());
			vo.setName(c.getName());
			vo.setEmail(c.getEmail());
			return vo;
		}).collect(Collectors.toList());
	}

	public CustomerVo getCustomerById(String id) {
		return repository.findById(id).map(c -> {
			CustomerVo vo = new CustomerVo();
			vo.setId(c.getId());
			vo.setName(c.getName());
			vo.setEmail(c.getEmail());
			return vo;
		}).orElse(null);
	}

	public void saveCustomer(CustomerVo vo) {
		Customer c = new Customer();
		c.setName(vo.getName());
		c.setEmail(vo.getEmail());
		c.setPwd(vo.getPwd());
		repository.save(c);
	}

	public void updateCustomer(CustomerVo vo) {
		Customer c = new Customer();
		c.setId(vo.getId());
		c.setName(vo.getName());
		c.setEmail(vo.getEmail());
		c.setPwd(vo.getPwd());
		repository.save(c);
	}

	public void deleteCustomer(CustomerVo vo) {
		Customer c = new Customer();
		c.setId(vo.getId());
		repository.delete(c);
	}
	
	@Transactional
	public void transactional(List<CustomerVo> customers) {
		for (CustomerVo vo : customers) {
			Customer c = new Customer();
			c.setName(vo.getName());
			c.setEmail(vo.getEmail());
			c.setPwd(vo.getPwd());
			repository.save(c);
		}
	}
}
