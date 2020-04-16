package com.skcc.mongodb.exam02.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.skcc.mongodb.exam01.entity.Customer;

public interface QueryRepository extends MongoRepository<Customer, String> {
	
	@Query("{ 'name' : ?0 }")
	List<Customer> findByName(String name);
	
	// { age: { $gt: 25, $lte: 50 } }
	@Query("{ 'age' : { $gt: ?0, $lt: ?1 } }")
	List<Customer> findCustomersByAgeBetween(int ageGT, int ageLT);
	
	@Query("{ 'age' : { $lt: ?0 } }")
	List<Customer> findCustomersByAgeLT(int ageLT);
	
	@Query("{ 'age' : { $gt: ?0 } }")
	List<Customer> findCustomersByAgeGT(int ageGT);

	@Query( value = "{ 'age' : { $gt: ?0 } }", sort ="{ 'age' : -1 }")
	List<Customer> sortFindCustomersByAgeGT(int ageGT);

	@Query(value = "{ 'age' : { $gt: ?0 } }", count = true)
	Long countFindCustomersByAgeGT(int ageGT);

	@Query(value = "{ 'age' : { $gt: ?0 } }", exists = true)
	Boolean existsFindCustomersByAgeGT(int ageGT);

//	@Query("{ 'name' : { $regex: '.*4'}  }")
	@Query("{ 'name' : { $regex: '?0'}  }")
	List<Customer> findCustomersByNameRegex(String nameRegex);
	
	@Query(value = "{'name': {$regex: ?0, $options: 'i'}}", count = true)
	Long countFindCustomersByNameRegex(String nameRegex);
}