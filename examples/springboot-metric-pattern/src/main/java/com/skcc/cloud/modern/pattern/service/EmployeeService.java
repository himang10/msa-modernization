package com.skcc.cloud.modern.pattern.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import com.skcc.cloud.modern.pattern.exception.RecordNotFoundException;
import com.skcc.cloud.modern.pattern.model.EmployeeEntity;
import com.skcc.cloud.modern.pattern.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository repository;

	private final MeterRegistry meterRegistry;
	private Counter callAllEmployees;
	private Counter callEmployeeById;
	private Counter callCreateOrUpdateEmployee;
	private Counter callDeleteEmployee;

	public EmployeeService(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
		initMetrics();
	}

	private void initMetrics() {
		this.callAllEmployees = Counter.builder("employee_call_allemployees_total")
										.tags("tagkey", "tagvalue")
										.description("Total getAllEmployees service count")
										.register(meterRegistry);
		this.callEmployeeById = Counter.builder("employee_call_employees_id_total")
										.description("Total getEmployeeById service count")
										.register(meterRegistry);
		this.callCreateOrUpdateEmployee = Counter.builder("employee_call_createorupdate_total")
										.description("Total createOrUpdateEmployee service count")
										.register(meterRegistry);
		this.callDeleteEmployee = Counter.builder("employee_call_deletebyid_total")
										.description("Total deleteByEmployee service count")
										.register(meterRegistry);
	}

	public List<EmployeeEntity> getAllEmployees() {
		callAllEmployees.increment();
		List<EmployeeEntity> result = (List<EmployeeEntity>) repository.findAll();
		
		if(result.size() > 0) {
			return result;
		} else {
			return new ArrayList<EmployeeEntity>();
		}
	}
	
	public EmployeeEntity getEmployeeById(Long id) throws RecordNotFoundException {
		callEmployeeById.increment();
		Optional<EmployeeEntity> employee = repository.findById(id);
		
		if(employee.isPresent()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}
	
	public EmployeeEntity createOrUpdateEmployee(EmployeeEntity entity) {
		callCreateOrUpdateEmployee.increment();
		if(entity.getId()  == null) 
		{
			entity = repository.save(entity);
			
			return entity;
		}  else {
			Optional<EmployeeEntity> employee = repository.findById(entity.getId());
			
			if(employee.isPresent()) {
				EmployeeEntity newEntity = employee.get();
				newEntity.setEmail(entity.getEmail());
				newEntity.setFirstName(entity.getFirstName());
				newEntity.setLastName(entity.getLastName());
				newEntity = repository.save(newEntity);
				
				return newEntity;
			} else {
				entity = repository.save(entity);
				return entity;
			}
		}
	} 
	
	public void deleteEmployeeById(Long id) throws RecordNotFoundException {
		callDeleteEmployee.increment();
		Optional<EmployeeEntity> employee = repository.findById(id);
		
		if(employee.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	} 
}