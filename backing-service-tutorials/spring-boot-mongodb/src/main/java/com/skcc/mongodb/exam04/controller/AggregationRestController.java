package com.skcc.mongodb.exam04.controller;

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

import com.skcc.mongodb.exam04.entity.BookTitle;
import com.skcc.mongodb.exam04.entity.OrdersPerCustomer;
import com.skcc.mongodb.exam04.services.AggregationService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/exam4")
public class AggregationRestController {
	@Autowired
	private AggregationService aggregationService;
	
	//{ $group : { _id : $customerId, total : { $sum : 1 } } }
	@ApiOperation(value = "{ $group : { _id : $customerId, total : { $sum : 1 } } }")
	@GetMapping("/totalOrdersPerCustomer")
	public ResponseEntity<List<OrdersPerCustomer>> totalOrdersPerCustomer(@RequestParam(value = "direction") String direction) {
		Direction DIRECTION = Sort.Direction.DESC;
		if("desc".equalsIgnoreCase(direction)) {
			DIRECTION = Sort.Direction.DESC;
		} else if("asc".equalsIgnoreCase(direction)) {
			DIRECTION = Sort.Direction.ASC;
		} else {
			DIRECTION = Sort.Direction.DESC;
		}
		
		Sort sort = Sort.by(DIRECTION, "customerId");
		
		return new ResponseEntity<>(aggregationService.totalOrdersPerCustomer(sort), HttpStatus.OK);
	}
	
	@ApiOperation(value = "pipeline = { \"{ $match : { customerId : ?0 } }\", \"{ $count : total }\" }")
	@GetMapping("/totalOrdersForCustomer")
	public ResponseEntity<Long> totalOrdersForCustomer(@RequestParam(value = "customerId") String customerId) {
		
		return new ResponseEntity<>(aggregationService.totalOrdersForCustomer(customerId), HttpStatus.OK);
	}

	@ApiOperation(value = "Book Title 목록 조회 - 오름차순 정렬")
	@GetMapping("/shouldRetrieveOrderedBookTitles")
	public ResponseEntity<List<BookTitle>> shouldRetrieveOrderedBookTitles() {
		
		return new ResponseEntity<>(aggregationService.shouldRetrieveOrderedBookTitles(), HttpStatus.OK);
	}
}