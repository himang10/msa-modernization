package com.skcc.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.rabbitmq.models.RabbitMQ_AbstractEntity;
import com.skcc.rabbitmq.service.RabbitMQ_ProducerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/publish")
@Api(value = "Producer-Controller")
public class RabbitMQ_ProducerController {
	
	@Autowired
	private RabbitMQ_ProducerService producerService;
	
	@ResponseBody
	@RequestMapping(value = "/simplePublish", method = RequestMethod.PUT)
	@ApiOperation(value="QueueName, Message", notes="Producer의 기본적인 Publish를 위한 API.")
	public ResponseEntity<String> simplePublish(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("simplePublish - "+"QueueName: "+rabbitmqEntity.getQueueName()+", Message: "+rabbitmqEntity.getMessage());
		
		try {
			producerService.simplePublish(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Put Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Put Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/simplePublish/hello", method = RequestMethod.PUT)
	@ApiOperation(value="Null", notes="Producer에서 'Hello' Queue로 'World!' Message를 Publish하는 API.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="rabbitmqCount", value="입력횟수", required=false, dataType="int", paramType="query", defaultValue=""),
		@ApiImplicitParam(name="ExchangeName", value="Exchange명", required=false, dataType="String", paramType="query", defaultValue=""),
		@ApiImplicitParam(name="RoutingKey", value="Routing Key", required=false, dataType="String", paramType="query", defaultValue=""),
		@ApiImplicitParam(name="queueName", value="Queue명", required=true, dataType="String", paramType="query", defaultValue="Hello"),
		@ApiImplicitParam(name="message", value="Message", required=true, dataType="String", paramType="query", defaultValue="Hello, World!"),
		@ApiImplicitParam(name="insertData", value="추가입력 DATA", required=false, dataType="Map<String, Object>", paramType="query", defaultValue="")
	})
	public ResponseEntity<String> simplePublish_hello(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("simplePublish - "+"QueueName: "+rabbitmqEntity.getQueueName()+", Message: "+rabbitmqEntity.getMessage());
		
		try {
			producerService.simplePublish(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Put Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Put Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/changingQueuePublish", method = RequestMethod.PUT)
	@ApiOperation(value="QueueName, Count", notes="Queue Name을 변경하며 Publish하는 API.")
	public ResponseEntity<String> changingQueuePublish(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("changingQueuePublish - "+"QueueName: "+rabbitmqEntity.getQueueName()+", Count: "+rabbitmqEntity.getRabbitmqCount());
		
		try {
			producerService.changingQueuePublish(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Put Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Put Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/durableQueuePublish", method = RequestMethod.PUT)
	@ApiOperation(value="QueueName, Message", notes="RabbitMQ가 다운되어도 지워지지 않는 Queue, Message를 Publish하는 API.")
	public ResponseEntity<String> durableQueuePublish(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("durableQueuePublish - "+"QueueName: "+rabbitmqEntity.getQueueName()+", Message: "+rabbitmqEntity.getMessage());
		
		try {
			producerService.durableQueuePublish(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Put Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Put Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/fanoutPublish", method = RequestMethod.PUT)
	@ApiOperation(value="ExchangeName, Message", notes="Fanout(Broadcast) Publish하는 API.")
	public ResponseEntity<String> fanoutPublish(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("fanoutPublish - "+"ExchangeName: "+rabbitmqEntity.getExchangeName()+", Message: "+rabbitmqEntity.getMessage());
		
		try {
			producerService.fanoutPublish(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Put Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Put Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/directPublish", method = RequestMethod.PUT)
	@ApiOperation(value="ExchangeName, RoutingKey, Message", notes="Direct(Unicast) Publish하는 API.")
	public ResponseEntity<String> directPublish(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("directPublish - "+"ExchangeName: "+rabbitmqEntity.getExchangeName()+", RoutingKey: "+rabbitmqEntity.getRoutingKey()+", Message: "+rabbitmqEntity.getMessage());
		
		try {
			producerService.directPublish(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Put Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Put Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/topicPublish", method = RequestMethod.PUT)
	@ApiOperation(value="ExchangeName, RoutingKey, Message", notes="Topic(Multicast) Publish하는 API.")
	public ResponseEntity<String> topicPublish(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("topicPublish - "+"ExchangeName: "+rabbitmqEntity.getExchangeName()+", RoutingKey: "+rabbitmqEntity.getRoutingKey()+", Message: "+rabbitmqEntity.getMessage());
		
		try {
			producerService.topicPublish(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Put Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Put Data successed.", HttpStatus.OK);
	}
	
}
