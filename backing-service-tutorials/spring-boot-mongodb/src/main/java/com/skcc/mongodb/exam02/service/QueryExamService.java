package com.skcc.mongodb.exam02.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.skcc.mongodb.exam01.entity.Customer;
import com.skcc.mongodb.exam01.vo.CustomerVo;
import com.skcc.mongodb.exam02.repository.QueryRepository;

@Service
public class QueryExamService {
	@Autowired
	private QueryRepository repository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public List<CustomerVo> findCustomersByAgeBetween(int ageGT, int ageLT) {
		return repository.findCustomersByAgeBetween(ageGT, ageLT).stream().map(c -> {
			CustomerVo vo = new CustomerVo();
			vo.setId(c.getId());
			vo.setName(c.getName());
			vo.setEmail(c.getEmail());
			vo.setAge(c.getAge());
			return vo;
		}).collect(Collectors.toList());
	}

	public List<CustomerVo> findCustomersByAgeLT(int ageLT) {
		return repository.findCustomersByAgeLT(ageLT).stream().map(c -> {
			CustomerVo vo = new CustomerVo();
			vo.setId(c.getId());
			vo.setName(c.getName());
			vo.setEmail(c.getEmail());
			vo.setAge(c.getAge());
			return vo;
		}).collect(Collectors.toList());
	}

	public List<CustomerVo> findCustomersByAgeGT(int ageGT) {
		return repository.findCustomersByAgeGT(ageGT).stream().map(c -> {
			CustomerVo vo = new CustomerVo();
			vo.setId(c.getId());
			vo.setName(c.getName());
			vo.setEmail(c.getEmail());
			vo.setAge(c.getAge());
			return vo;
		}).collect(Collectors.toList());
	}

	public List<CustomerVo> sortFindCustomersByAgeGT(int ageGT) {
		return repository.sortFindCustomersByAgeGT(ageGT).stream().map(c -> {
			CustomerVo vo = new CustomerVo();
			vo.setId(c.getId());
			vo.setName(c.getName());
			vo.setEmail(c.getEmail());
			vo.setAge(c.getAge());
			return vo;
		}).collect(Collectors.toList());
	}

	public List<CustomerVo> findCustomersByAgeSort(Direction DIRECTION) {
//		Criteria find = Criteria.where("name").is("Random");
		Query query = new Query()
//				.addCriteria(find)
				.with(Sort.by(DIRECTION, "age"));
		
		return mongoTemplate.find(query, Customer.class).stream().map(c -> {
			CustomerVo vo = new CustomerVo();
			vo.setId(c.getId());
			vo.setName(c.getName());
			vo.setEmail(c.getEmail());
			vo.setAge(c.getAge());
			return vo;
		}).collect(Collectors.toList());
	}
	
	public List<CustomerVo> findCustomersByNameRegex(String name) {
		return repository.findCustomersByNameRegex(name).stream().map(c -> {
			CustomerVo vo = new CustomerVo();
			vo.setId(c.getId());
			vo.setName(c.getName());
			vo.setEmail(c.getEmail());
			vo.setAge(c.getAge());
			return vo;
		}).collect(Collectors.toList());
	}

	public Long countFindCustomersByNameRegex(String name) {
		return repository.countFindCustomersByNameRegex(name);
	}

	public Long countFindCustomersByAgeGT(int ageGT) {
		return repository.countFindCustomersByAgeGT(ageGT);
	}
	
	public Boolean existsFindCustomersByAgeGT(int ageGT) {
		return repository.existsFindCustomersByAgeGT(ageGT);
	}
	
	public List<CustomerVo> getCustomerByName(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		
		return mongoTemplate.find(query, Customer.class).stream().map(c -> {
			CustomerVo vo = new CustomerVo();
			vo.setId(c.getId());
			vo.setName(c.getName());
			vo.setEmail(c.getEmail());
			vo.setAge(c.getAge());
			return vo;
		}).collect(Collectors.toList());
	}


}
