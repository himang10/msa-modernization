package com.skcc.modern.pattern.initializr.springbootmoderntemplate.circuitbreaker.repository;

import com.skcc.modern.pattern.initializr.springbootmoderntemplate.circuitbreaker.model.EmployeeEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository  extends CrudRepository<EmployeeEntity, Long> {
}
