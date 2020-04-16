package com.skcc.modern.pattern.example.springbootcircuitbreakerpattern.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.skcc.modern.pattern.example.springbootcircuitbreakerpattern.exception.ResourceNotFoundException;
import com.skcc.modern.pattern.example.springbootcircuitbreakerpattern.model.EmployeeEntity;
import com.skcc.modern.pattern.example.springbootcircuitbreakerpattern.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repository;

	public List<EmployeeEntity> getAllEmployees() {
		List<EmployeeEntity> result = (List<EmployeeEntity>) repository.findAll();

		if (result.size() > 0) {
			return result;
		} else {
			return new ArrayList<EmployeeEntity>();
		}
	}

	public EmployeeEntity getEmployeeById(Long id) throws ResourceNotFoundException {
		Optional<EmployeeEntity> employee = repository.findById(id);

		if (employee.isPresent()) {
			return employee.get();
		} else {
			throw new ResourceNotFoundException("No employee record exist for given id");
		}
	}

	public EmployeeEntity createOrUpdateEmployee(EmployeeEntity entity) {
		if (entity.getId() == null) {
			entity = repository.save(entity);

			return entity;
		} else {
			Optional<EmployeeEntity> employee = repository.findById(entity.getId());

			if (employee.isPresent()) {
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

	public void deleteEmployeeById(Long id) throws ResourceNotFoundException {
		Optional<EmployeeEntity> employee = repository.findById(id);

		if (employee.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("No employee record exist for given id");
		}
	}

	public String sendGiftEmployeeById(Long id) {
		try {
			int sleep = id.intValue() * 1000;
			System.out.println("Sleep time(s) : " + sleep);
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "send-employees";
	}
	
	public String retrySendGiftEmployeeById(Long id) {
		try {
			int sleep = (int)(Math.random() * 10 * 1000);
			System.out.println("Sleep time(s) : " + sleep);
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "send-employees";
    }

    public Supplier<String> sendGiftSupplier(final Long id) {
		return () -> this.sendGiftEmployeeById(id);
	}

	public Supplier<String> retrySendGiftSupplier(final Long id) {
		return () -> this.retrySendGiftEmployeeById(id);
	}
}