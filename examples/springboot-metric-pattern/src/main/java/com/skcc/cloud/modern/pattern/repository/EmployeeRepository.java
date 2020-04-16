package com.skcc.cloud.modern.pattern.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skcc.cloud.modern.pattern.model.EmployeeEntity;

@Repository
public interface EmployeeRepository  extends CrudRepository<EmployeeEntity, Long> {
}
