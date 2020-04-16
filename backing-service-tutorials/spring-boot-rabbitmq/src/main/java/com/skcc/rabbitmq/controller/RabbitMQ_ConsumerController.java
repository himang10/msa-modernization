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
import com.skcc.rabbitmq.service.RabbitMQ_ConsumerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/subscribe")
@Api(value = "Consumer-Controller")
public class RabbitMQ_ConsumerController {
	
	@Autowired
	private RabbitMQ_ConsumerService consumerService;
	
	@ResponseBody
	@RequestMapping(value = "/simpeSubscribe", method = RequestMethod.GET)
	@ApiOperation(value="QueueName", notes="Consumer의 기본적인 Subscribe를 위한 API.")
	public ResponseEntity<String> simpeSubscribe(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("simplSubscribe - "+"QueueName: "+rabbitmqEntity.getQueueName());
		
		try {
			consumerService.simpleSubscribe(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Get Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Get Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/simpeSubscribe/hello", method = RequestMethod.GET)
	@ApiOperation(value="Null", notes="Producer에서 'Hello' Queue로 'World!' Message를 Publish하는 API.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="rabbitmqCount", value="입력횟수", required=false, dataType="int", paramType="query", defaultValue=""),
		@ApiImplicitParam(name="ExchangeName", value="Exchange명", required=false, dataType="String", paramType="query", defaultValue=""),
		@ApiImplicitParam(name="RoutingKey", value="Routing Key", required=false, dataType="String", paramType="query", defaultValue=""),
		@ApiImplicitParam(name="queueName", value="Queue명", required=true, dataType="String", paramType="query", defaultValue="Hello"),
		@ApiImplicitParam(name="message", value="Message", required=false, dataType="String", paramType="query", defaultValue=""),
		@ApiImplicitParam(name="insertData", value="추가입력 DATA", required=false, dataType="Map<String, Object>", paramType="query", defaultValue="")
	})
	public ResponseEntity<String> simpleSubscribe_hello(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("simpleSubscribe_hello - "+"QueueName: "+rabbitmqEntity.getQueueName());
		
		try {
			consumerService.simpleSubscribe(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Get Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Get Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/durableQueueSubscribe", method = RequestMethod.GET)
	@ApiOperation(value="QueueName", notes="durable한 Queue를 Subscribe하는 API.")
	public ResponseEntity<String> durableQueueSubscribe(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("simplSubscribe - "+"QueueName: "+rabbitmqEntity.getQueueName());
		
		try {
			consumerService.durableQueueSubscribe(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Get Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Get Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/ackSubscribe", method = RequestMethod.GET)
	@ApiOperation(value="QueueName", notes="basicQos를 설정하여 Ack를 보내야만 새로운 Queue를 Subscribe하는 API.")
	public ResponseEntity<String> ackSubscribe(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("simplSubscribe - "+"QueueName: "+rabbitmqEntity.getQueueName());
		
		try {
			consumerService.ackSubscribe(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Get Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Get Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/fanoutSubscribe", method = RequestMethod.GET)
	@ApiOperation(value="ExchangeName", notes="Fanout(Broadcast)로 보내진 Message Subscribe하는 API.")
	public ResponseEntity<String> fanoutSubscribe(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("fanoutSubscribe - "+"ExchangeName: "+rabbitmqEntity.getExchangeName());
		
		try {
			consumerService.fanoutSubscribe(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Get Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Get Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/directSubscribe", method = RequestMethod.GET)
	@ApiOperation(value="ExchangeName, RoutingKey", notes="Direct(Broadcast)로 보내진 Message Subscribe하는 API.")
	public ResponseEntity<String> directSubscribe(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("directSubscribe - "+"ExchangeName: "+rabbitmqEntity.getExchangeName()+", RoutingKey: "+rabbitmqEntity.getRoutingKey());
		
		try {
			consumerService.directSubscribe(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Get Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Get Data successed.", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/topicSubscribe", method = RequestMethod.GET)
	@ApiOperation(value="ExchangeName, RoutingKey", notes="Topic(Multicast)로 보내진 Message Subscribe하는 API.")
	public ResponseEntity<String> topicSubscribe(@ModelAttribute RabbitMQ_AbstractEntity rabbitmqEntity) { 
		log.info("topicSubscribe - "+"ExchangeName: "+rabbitmqEntity.getExchangeName()+", RoutingKey: "+rabbitmqEntity.getRoutingKey());
		
		try {
			consumerService.topicSubscribe(rabbitmqEntity);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>("RabbitMQ - Get Data failed.", HttpStatus.BAD_REQUEST);
		}
	    
		return new ResponseEntity<>("RabbitMQ - Get Data successed.", HttpStatus.OK);
	}
}
