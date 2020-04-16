package com.skcc.rabbitmq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.messaging.support.MessageBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcc.rabbitmq.models.SpringCloudStream_sampleEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBinding(Source.class)
public class SpringCloudStream_PublishServiceImpl implements SpringCloudStream_PublishService {
	
	@Autowired
	Source source;
	
	@Autowired
	protected Environment environment;
	
	@Override
	public void simplePublish(String message) throws Exception {
		log.info("simplePublish - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");

		ObjectMapper ob = new ObjectMapper();
		SpringCloudStream_sampleEntity txn = null;
		
		try {
			
			txn = ob.readValue(message, SpringCloudStream_sampleEntity.class);
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		source.output().send(MessageBuilder.withPayload(txn).setHeader("myheader", "myheaderValue").build());
		System.out.println("Success입니다.");
	//	return "success";
	}

	@Override
	public void simplePublish2(SpringCloudStream_sampleEntity entity) throws Exception {
		log.info("simplePublish2 - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");
		
		source.output().send(MessageBuilder.withPayload(entity).setHeader("myheader", "myheaderValue").build());
		System.out.println("Success입니다.");
	}
	
}
