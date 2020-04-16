package com.zdb.kafka.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Configuration
public class KafkaSender {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value(value = "${spring.kafka.topic-name}")
	private String topic;

	@Bean
	public Map<String, Object> producerConfig() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		//StringSerializer -> 직렬화 필요성
		props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 100000000);
		props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
		return props;
	}
	
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfig());
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
	
	@Bean
	public Map<String, Object> producerCustomPatitionConfig() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 100000000);
		props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
		return props;
	}
	
	@Bean
	public ProducerFactory<String, String> producerPatitionFactory() {
		return new DefaultKafkaProducerFactory<>(producerCustomPatitionConfig());
	}

	@Bean
	public KafkaTemplate<String, String> kafkaPatitionTemplate() {
		return new KafkaTemplate<>(producerPatitionFactory());
	}


	@PostMapping("/sendFireAndForget")
	public void sendFireAndForget(@ApiParam(value = "Message 내용", required = true) @RequestBody String msg) {
		log.info(">>> kafka sendFireAndForget data : {}", msg);
		kafkaTemplate().send(topic, msg);
	}

	@PostMapping("/sendSync")
	@ApiOperation(value="Synchronous send", notes="메세지 전송 이후에 get()을 호출하여 결과를 받아 메시지가 성공적으로 전송됬는지 체크하는 동기식 Message send API.")
	public void senderSync(@ApiParam(value = "Message 내용", required = true) @RequestBody String msg) {
		log.info(">>> kafka senderSync data : {}", msg);
		try {
			kafkaTemplate().send(topic, msg).get();
		} catch (InterruptedException | ExecutionException e) {
			//kafka execption 
			
			e.printStackTrace();
		}
	}

	@PostMapping("/sendAsync")
	@ApiOperation(value="Asynchronous send", notes="메세지 전송 이후에 콜백(callback) 메서드를 구현한 객체를 매개변수로 전달 하는 방식의 비동기식 Message send API.")
	public void senderAsync(@ApiParam(value = "Message 내용", required = true) @RequestBody String msg) {
		log.info(">>> kafka senderAsync data : {}",msg);
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, msg);
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate().send(producerRecord);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>(){
			@Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sent message= [" + topic, msg + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("Message 전달 오류 " + topic, msg + "] due to : " + ex.getMessage());
            }
		});
	}
	
	//produce 하는 방법에 따라서 사용 예(표로 만들기)
	
	@PostMapping("/senderPartition")
	@ApiOperation(value="send partition#1", notes="파티션된 토픽(round-robin 방식)의 Message send API.")
	public void senderPartition(@ApiParam(value = "Message 내용", required = true) @RequestBody String msg) {
		log.info(">>> kafka senderPartition data : {}",msg);
		String topic = "partition-topic";
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, msg);
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate().send(producerRecord);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>(){
			@Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sent message= [" + topic, msg + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("Message 전달 오류 " + topic, msg + "] due to : " + ex.getMessage());
            }
		});
	}
	
	//구현중
	@PostMapping("/senderCustomPartition")
	@ApiOperation(value="send partition#2", notes="파티션된 토픽(hash 기반 custom 방식)의 Message send API.")
	public void senderCustomPartition(@ApiParam(value = "Message 내용", required = true) @RequestBody String msg) {
		log.info(">>> kafka senderPartition data : {}",msg);
		String topic = "partition-topic";
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, msg);
		ListenableFuture<SendResult<String, String>> future = kafkaPatitionTemplate().send(producerRecord);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>(){
			@Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sent message= [" + topic, msg + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("Message 전달 오류 " + topic, msg + "] due to : " + ex.getMessage());
            }
		});
	}
	
	

}
