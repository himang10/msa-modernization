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
public class RabbitMQ_AbstractEntity {
	private int rabbitmqCount;
	private String ExchangeName;
	private String RoutingKey;
	private String queueName;
	private String message;
	private Map<String, Object> insertData;
}
