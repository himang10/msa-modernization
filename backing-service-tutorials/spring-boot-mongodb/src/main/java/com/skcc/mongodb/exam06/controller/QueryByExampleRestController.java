package com.skcc.mongodb.exam06.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.mongodb.exam06.entity.Person;
import com.skcc.mongodb.exam06.services.UserRepositoryIntegrationService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/exam6")
public class QueryByExampleRestController {
	@Autowired
	private UserRepositoryIntegrationService userRepositoryIntegrationService;
	
	@Autowired MongoOperations operations;
	
	@ApiOperation(value = "초기 데이터를 입력 합니다. - \"contacts\" collection 이 생성 됩니다.")
	@GetMapping("/initData")
	public ResponseEntity<String> initData() {
		try {
			userRepositoryIntegrationService.setUp();
			
			return new ResponseEntity<>("setup success!", HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>("init data fail! \n" + e.getMessage(), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "매칭되는 단어 검색")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "lastname", value = "", dataType = "string", defaultValue = "White")
    })
	@GetMapping("/countBySimpleExample")
	public ResponseEntity<Long> countBySimpleExample(@RequestParam(value = "lastname") String lastname) {
		return new ResponseEntity<>(userRepositoryIntegrationService.countBySimpleExample(lastname), HttpStatus.OK);
	}

	@ApiOperation(value = "firstname, lastname 를 제외하고 age 매칭되는 결과 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "age", value = "", dataType = "int", defaultValue = "17")
	})
	@GetMapping("/ignorePropertiesAndMatchByAge")
	public ResponseEntity<Person> ignorePropertiesAndMatchByAge(@RequestParam(value = "age") int age) {
		return new ResponseEntity<>(userRepositoryIntegrationService.ignorePropertiesAndMatchByAge(age), HttpStatus.OK);
	}

	@ApiOperation(value = "firstname 부분 매칭 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "substring", value = "", dataType = "string", defaultValue = "er")
	})
	@GetMapping("/substringMatching")
	public ResponseEntity<Iterator<Person>> substringMatching(@RequestParam(value = "substring") String substring) {
		return new ResponseEntity<>(userRepositoryIntegrationService.substringMatching(substring), HttpStatus.OK);
	}

	@ApiOperation(value = "firstname 정규식 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "regex", value = "", dataType = "string", defaultValue = "(Skyl|Walt)er")
	})
	@GetMapping("/regexMatching")
	public ResponseEntity<Iterator<Person>> regexMatching(@RequestParam(value = "regex") String regex) {
		return new ResponseEntity<>(userRepositoryIntegrationService.regexMatching(regex), HttpStatus.OK);
	}

	@ApiOperation(value = "first 로 시작하고, last와 매칭되는(대소문자 구분안함) 결과 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "first", value = "", dataType = "string", defaultValue = "Walter"),
		@ApiImplicitParam(name = "last", value = "", dataType = "string", defaultValue = "WHITE")
	})
	@GetMapping("/matchStartingStringsIgnoreCase")
	public ResponseEntity<Iterator<Person>> matchStartingStringsIgnoreCase(@RequestParam(value = "first") String first, @RequestParam(value = "last") String last) {
		return new ResponseEntity<>(userRepositoryIntegrationService.matchStartingStringsIgnoreCase(first, last), HttpStatus.OK);
	}

}