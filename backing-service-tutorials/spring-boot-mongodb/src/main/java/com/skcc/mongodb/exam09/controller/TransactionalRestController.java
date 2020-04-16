package com.skcc.mongodb.exam09.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.mongodb.exam09.services.MongoTransactionalService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/exam9")
public class TransactionalRestController {
	@Autowired
	private MongoTransactionalService service;

	@ApiOperation(value = "Transaction - insert 실행후 바로 저장되지 않고 실행 완료 후 데이터 입력, 에러시 롤백.")
	@GetMapping("/whenPerformMongoTransaction_thenSuccess")
	public ResponseEntity<String> whenPerformMongoTransaction_thenSuccess() {
		service.setup();
		service.whenPerformMongoTransaction_thenSuccess();
		return new ResponseEntity<>("", HttpStatus.OK);
	}

	@ApiOperation(value = "Cannot run 'listCollections' in a multi-document transaction.")
	@GetMapping("/whenListCollectionDuringMongoTransaction_thenException")
	public ResponseEntity<String> whenListCollectionDuringMongoTransaction_thenException() {
		service.setup();
		service.whenListCollectionDuringMongoTransaction_thenException();
		return new ResponseEntity<>("", HttpStatus.OK);
	}

	@ApiOperation(value = "데이터 입력 후 로직 에러 발생으로 롤백 처리")
	@GetMapping("/save_and_rollback")
	public ResponseEntity<String> save_and_rollback() {
		service.setup();
		service.save_and_rollback();

		return new ResponseEntity<>("", HttpStatus.OK);
	}

}