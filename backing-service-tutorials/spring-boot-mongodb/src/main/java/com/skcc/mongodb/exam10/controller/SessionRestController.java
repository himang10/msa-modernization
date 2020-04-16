package com.skcc.mongodb.exam10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.mongodb.exam10.services.MongoSessionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/exam10")
public class SessionRestController {
	@Autowired
	private MongoSessionService service;

	@ApiOperation(value = "withSession")
	@GetMapping("/withSession")
	public ResponseEntity<String> whenPerformMongoTransaction_thenSuccess() {
		service.withSession();
		return new ResponseEntity<>("", HttpStatus.OK);
	}
}