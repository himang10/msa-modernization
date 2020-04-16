package com.skcc.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.rabbitmq.models.AmazonMQ_AbstractEntity;
import com.skcc.rabbitmq.service.AmazonMQ_Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/amazonmq")
@Api(value = "AmazonMQ_Controller")
public class AmazonMQ_Controller {


	@Autowired
	private AmazonMQ_Service service;
	
	
	/*==============================================================================================================================================================================================
	 * ==============================================================================================================================================================================================
	 * Publish Section
	 * ==============================================================================================================================================================================================
	 * =============================================================================================================================================================================================*/
	
	@RequestMapping(value = "/simplePublish", method = RequestMethod.PUT)
	@ApiOperation(value="QueueName, Message", notes="Queue로 기본적인 Publish를 위한 API.")
	public ResponseEntity<String> simplePublish(@ModelAttribute AmazonMQ_AbstractEntity amazonmqEntity) { 
		log.info("simplePublish - "+"QueueName: "+amazonmqEntity.getQueueName()+", Message: "+amazonmqEntity.getMessage());
		
		try {
			service.simplePublish(amazonmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("AmazonMQ - Put Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("AmazonMQ - Put Data successed.", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/topicPublish", method = RequestMethod.PUT)
	@ApiOperation(value="TopicName, Message", notes="Topic으로 기본적인 Publish를 위한 API.")
	public ResponseEntity<String> topicPublish(@ModelAttribute AmazonMQ_AbstractEntity amazonmqEntity) { 
		log.info("topicPublish - "+"TopicName: "+amazonmqEntity.getTopicName()+", Message: "+amazonmqEntity.getMessage());
		
		try {
			service.topicPublish(amazonmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("AmazonMQ - Put Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("AmazonMQ - Put Data successed.", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/virtualTopicPublish", method = RequestMethod.PUT)
	@ApiOperation(value="TopicName, Message", notes="VirtualTopic으로 Publish를 위한 API.\nTopicName을 입력할 경우 VirtualTopic.[TopicName]으로 Topic이 생성된다.")
	public ResponseEntity<String> virtualTopicPublish(@ModelAttribute AmazonMQ_AbstractEntity amazonmqEntity) { 
		log.info("virtualTopicPublish - "+"virtualTopicName: "+amazonmqEntity.getTopicName()+", Message: "+amazonmqEntity.getMessage());
		
		try {
			service.virtualTopicPublish(amazonmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("AmazonMQ - Put Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("AmazonMQ - Put Data successed.", HttpStatus.OK);
	}
	
	
	/*==============================================================================================================================================================================================
	 * ==============================================================================================================================================================================================
	 * Consumer Section
	 * ==============================================================================================================================================================================================
	 * =============================================================================================================================================================================================*/
	
	@RequestMapping(value = "/simpleConsume", method = RequestMethod.GET)
	@ApiOperation(value="QueueName", notes="Consumer의 기본적인 Subscribe를 위한 API.")
	public ResponseEntity<String> simpleConsume(@ModelAttribute AmazonMQ_AbstractEntity amazonmqEntity) { 
		log.info("simpleConsume - "+"QueueName: "+amazonmqEntity.getQueueName());
		
		try {
			service.simpleConsume(amazonmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("AmazonMQ - Get Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("AmazonMQ - Get Data successed.", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/longConsume", method = RequestMethod.GET)
	@ApiOperation(value="QueueName", notes="Consumer가 5개의 msg를 수신할 때까지 connection을 유지하는 API.")
	public ResponseEntity<String> longConsume(@ModelAttribute AmazonMQ_AbstractEntity amazonmqEntity) { 
		log.info("longConsume - "+"QueueName: "+amazonmqEntity.getQueueName());
		
		try {
			service.longConsume(amazonmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("AmazonMQ - Get Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("AmazonMQ - Get Data successed.", HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/simpleSubscribe", method = RequestMethod.GET)
	@ApiOperation(value="TopicName", notes="Consumer가 Topic을 subscribe하기 위한 API. \n1개의 message를 subscribe할 때 까지 connection 유지한다. \n 주의  :Topic은 message가 queueing되지 않음")
	public ResponseEntity<String> simpleSubscribe(@ModelAttribute AmazonMQ_AbstractEntity amazonmqEntity) { 
		log.info("simpleSubscribe - "+"QueueName: "+amazonmqEntity.getQueueName());
		
		try {
			service.simpleSubscribe(amazonmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("AmazonMQ - Get Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("AmazonMQ - Get Data successed.", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/virtualTopicSubscribe", method = RequestMethod.GET)
	@ApiOperation(value="QueueName, TopicName", notes="Consumer가 virtualTopic을 subscribe하기 위한 API. \nQueueName과 TopicName을 입력받으면 실제 consumer로 연결되는 queue는 Consumer.[QueueName].VirtualTopic.[TopicName]이다.")
	public ResponseEntity<String> virtualTopicSubscribe(@ModelAttribute AmazonMQ_AbstractEntity amazonmqEntity) { 
		log.info("virtualTopicSubscribe - "+"QueueName: "+amazonmqEntity.getQueueName());
		
		try {
			service.virtualTopicSubscribe(amazonmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("AmazonMQ - Get Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("AmazonMQ - Get Data successed.", HttpStatus.OK);
	}
}
