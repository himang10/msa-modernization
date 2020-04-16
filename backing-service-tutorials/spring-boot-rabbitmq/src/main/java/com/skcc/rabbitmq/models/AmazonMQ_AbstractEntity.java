package com.skcc.rabbitmq.models;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Configuration
public class AmazonMQ_AbstractEntity {
	private String queueName;
	private String topicName;
	private String message;
}
