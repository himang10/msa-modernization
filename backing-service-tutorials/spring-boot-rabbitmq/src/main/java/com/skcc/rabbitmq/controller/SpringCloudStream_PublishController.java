package com.skcc.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.rabbitmq.models.SpringCloudStream_sampleEntity;
import com.skcc.rabbitmq.service.SpringCloudStream_PublishService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/stream/publish")
@Api(value = "Stream-Producer-Controller")
public class SpringCloudStream_PublishController {
	
	@Autowired
	private SpringCloudStream_PublishService publishService;
	
	@ResponseBody
	@RequestMapping(value = "/simplePublish", method = RequestMethod.PUT)
	@ApiOperation(value="Message", notes="(RequestBody)Producer의 기본적인 Publish를 위한 API.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="message", value="Message", required=true, dataType="String", paramType="query", defaultValue="Hello, World!")
	})
	public ResponseEntity<String> simplePublish(@RequestBody String message) {
		log.info("stream/simplePublish - Message: "+message);
		
		try {
			publishService.simplePublish(message);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Put Data failed.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("RabbitMQ - Put Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/simplePublish2", method = RequestMethod.PUT)
	@ApiOperation(value="Message", notes="(ModelAttribute)Producer의 기본적인 Publish를 위한 API.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="message", value="Message", required=true, dataType="String", paramType="query", defaultValue="Hello, World!")
	})
	public ResponseEntity<String> simplePublish2(@ModelAttribute SpringCloudStream_sampleEntity entity) {
		log.info("stream/simplePublish2 - Message: "+entity.getMessage());
		
		try {
			publishService.simplePublish2(entity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Put Data failed.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("RabbitMQ - Put Data successed.", HttpStatus.OK);
	}
}
