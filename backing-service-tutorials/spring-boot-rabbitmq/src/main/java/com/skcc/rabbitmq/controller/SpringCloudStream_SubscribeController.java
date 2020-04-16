package com.skcc.rabbitmq.controller;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.rabbitmq.models.SpringCloudStream_sampleEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/stream/publish")
@Api(value = "Stream-Producer-Controller")
@EnableBinding(Sink.class)
public class SpringCloudStream_SubscribeController {
	
//	@Autowired
//	private SpringCloudStream_SubscribeService subscribeService;
	
	@ResponseBody
	@RequestMapping(value = "/simpeSubscribe", method = RequestMethod.GET)
	@ApiOperation(value="-", notes="Consumer의 기본적인 Subscribe를 위한 API.")
	@StreamListener(Sink.INPUT)
	public void simpeSubscribe(SpringCloudStream_sampleEntity msg) {
		log.info("stream/simpeSubscribe");
		
		try {
			System.out.println("Transaction contains as "+msg.getMessage());
			// subscribeService.simpeSubscribe();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
}
