package com.skcc.mongodb.exam02.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.mongodb.exam01.vo.CustomerVo;
import com.skcc.mongodb.exam02.service.QueryExamService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/exam2")
public class QueryRestController {

	@Autowired
	private QueryExamService queryExamService;
	
	
	/////////////////////
	
	/**
	 * age 구간 조회
	 * 
	 * @param gt
	 * @param lt
	 * @return
	 */
	@GetMapping("/customers/age")
	public ResponseEntity<List<CustomerVo>> findCustomersByAgeBetween(@RequestParam(value = "gt") Integer gt, @RequestParam(value = "lt") Integer lt) {
		return new ResponseEntity<>(queryExamService.findCustomersByAgeBetween(gt, lt), HttpStatus.OK);
	}
	
	
	/**
	 * age 보다 큰 결과 (sort)
	 * 
	 * @param nameRegex
	 * @return
	 */
	@ApiOperation(value = "age 보다 큰 결과 (sort)")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "age", value = "age", required = true, dataType = "int", defaultValue = "50"),
	})
	@GetMapping("/customers/name/count_sort")
	public ResponseEntity<List<CustomerVo>> sortFindCustomersByAgeGT(@RequestParam(value = "age") Integer age) {
		return new ResponseEntity<>(queryExamService.sortFindCustomersByAgeGT(age), HttpStatus.OK);
	}

	/**
	 * name 정규식 조회
	 * 
	 * @param nameRegex
	 * @return
	 */
	@ApiOperation(value = "정규식 이름 조회")
	@GetMapping("/customers/name/case1")
	public ResponseEntity<List<CustomerVo>> findCustomersByNameRegex(@RequestParam(value = "nameRegex") String nameRegex) {
		return new ResponseEntity<>(queryExamService.findCustomersByNameRegex(nameRegex), HttpStatus.OK);
	}

	/**
	 * age 보다 큰 결과 수
	 * 
	 * @param nameRegex
	 * @return
	 */
	@ApiOperation(value = "age 보다 큰 결과 수")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "age", value = "age", required = true, dataType = "int", defaultValue = "50"),
    })
	@GetMapping("/customers/name/count")
	public ResponseEntity<Long> countFindCustomersByAgeGT(@RequestParam(value = "age") Integer age) {
		return new ResponseEntity<>(queryExamService.countFindCustomersByAgeGT(age), HttpStatus.OK);
	}

	/**
	 * age 보다 큰 결과 exist
	 * 
	 * @param nameRegex
	 * @return
	 */
	@ApiOperation(value = "age 보다 큰 결과 exist")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "age", value = "age", required = true, dataType = "int", defaultValue = "50"),
	})
	@GetMapping("/customers/name/exists")
	public ResponseEntity<Boolean> existsFindCustomersByAgeGT(@RequestParam(value = "age") Integer age) {
		return new ResponseEntity<>(queryExamService.existsFindCustomersByAgeGT(age), HttpStatus.OK);
	}
	
	/**
	 * name 으로 조회
	 * @param name
	 * @return
	 */
	@GetMapping("/customers/name/case2")
	public ResponseEntity<List<CustomerVo>> getCustomerByName(@RequestParam(value = "name") String name) {
		return new ResponseEntity<>(queryExamService.getCustomerByName(name), HttpStatus.OK);
	}
	
	@GetMapping("/customers/sort")
	public ResponseEntity<List<CustomerVo>> findCustomersByAgeSort(@RequestParam(value = "direction") String direction) {
		Direction DIRECTION = Sort.Direction.DESC;
		if("desc".equalsIgnoreCase(direction)) {
			DIRECTION = Sort.Direction.DESC;
		} else if("asc".equalsIgnoreCase(direction)) {
			DIRECTION = Sort.Direction.ASC;
		} else {
			DIRECTION = Sort.Direction.DESC;
		}
		return new ResponseEntity<>(queryExamService.findCustomersByAgeSort(DIRECTION), HttpStatus.OK);
	}
	
}