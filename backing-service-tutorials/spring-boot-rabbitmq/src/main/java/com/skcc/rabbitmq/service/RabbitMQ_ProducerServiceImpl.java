package com.skcc.rabbitmq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.skcc.rabbitmq.config.RabbitMQ_AbstractConfig;
import com.skcc.rabbitmq.models.RabbitMQ_AbstractEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RabbitMQ_ProducerServiceImpl implements RabbitMQ_ProducerService {
	
	@Autowired
	private RabbitMQ_AbstractConfig rabbitmqConfig;
	
	@Autowired
	protected Environment environment;

	@Override
	public void simplePublish(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception {
		log.info("simplePublish - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");
		
		String queue_name = (String) rabbitmqEntity.getQueueName();
		String message = (String) rabbitmqEntity.getMessage();
		
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
			
		    Channel channel = connection.createChannel();
		    
		    // (String)queue_name, (Boolean)durable, (Boolean)exclusive, (Boolean)autoDelete, (Map<String,Object>)arguments
		    channel.queueDeclare(queue_name, false, false, false, null);
		    
		    // (String)exchange, (String)routingKey, (AMQP.BasicProperties)props, (byte[])body
		    channel.basicPublish("", queue_name, null, message.getBytes("UTF-8"));
		    System.out.println(" [x] Sent '" + message + "'");
		    
		    channel.close();
		    connection.close();

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}

	@Override
	public void changingQueuePublish(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception {
		log.info("changingQueuePublish - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");
		
		String default_queue_name = rabbitmqEntity.getQueueName();
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
			
		    Channel channel = connection.createChannel();
		    
		    for(int i=0; i<rabbitmqEntity.getRabbitmqCount(); i++) {
		    	String queue_name = default_queue_name+"["+(i+1)+"]";
		    	String message = queue_name + "'s Message.";
		    			
		    	// (String)queue_name, (Boolean)durable, (Boolean)exclusive, (Boolean)autoDelete, (Map<String,Object>)arguments
		    	channel.queueDeclare(queue_name, false, false, false, null);
		    	
		    	// (String)exchange, (String)routingKey, (AMQP.BasicProperties)props, (byte[])body
		    	channel.basicPublish("", queue_name, null, message.getBytes("UTF-8"));
		    	System.out.println(" [x] Sent '" + message + "'");
		    }
		    
		    channel.close();
		    connection.close();

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}

	@Override
	public void durableQueuePublish(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception {
		log.info("durableQueuePublish - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");
		
		String queue_name = (String) rabbitmqEntity.getQueueName();
		String message = (String) rabbitmqEntity.getMessage();
		
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
			
		    Channel channel = connection.createChannel();
		    
		    // (String)queue_name, (Boolean)durable, (Boolean)exclusive, (Boolean)autoDelete, (Map<String,Object>)arguments
		    channel.queueDeclare(queue_name, true, false, false, null);
		    
		    // (String)exchange, (String)routingKey, (AMQP.BasicProperties)props, (byte[])body
		    channel.basicPublish("", queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
		    System.out.println(" [x] Sent '" + message + "'");
		    
		    channel.close();
		    connection.close();

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}

	@Override
	public void fanoutPublish(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception {
		log.info("fanoutPublish - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");
		
		String exchange_name = (String) rabbitmqEntity.getExchangeName();
		String message = (String) rabbitmqEntity.getMessage();
		
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
			
		    Channel channel = connection.createChannel();
		    
		    // (String)exchange_name, (String)type
		    channel.exchangeDeclare(exchange_name, "fanout");
		    
		    // fanout 방식이므로 Queue를 선언하지 않고 Publish합니다.
		    // (String)queue_name, (Boolean)durable, (Boolean)exclusive, (Boolean)autoDelete, (Map<String,Object>)arguments
		    // channel.queueDeclare(queue_name, false, false, false, null);
		    
		    // (String)exchange, (String)routingKey, (AMQP.BasicProperties)props, (byte[])body
		    channel.basicPublish(exchange_name, "", null, message.getBytes("UTF-8"));
		    System.out.println(" [x] Sent '" + message + "'");
		    
		    channel.close();
		    connection.close();

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}

	@Override
	public void directPublish(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception {
		log.info("directPublish - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");
		
		String exchange_name = (String) rabbitmqEntity.getExchangeName();
		String RoutingKey = (String) rabbitmqEntity.getRoutingKey();
		String message = (String) rabbitmqEntity.getMessage();
		
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
			
		    Channel channel = connection.createChannel();
		    
		    // (String)exchange_name, (String)type
		    channel.exchangeDeclare(exchange_name, "direct");
		    
		    // Direct 방식이므로 BindingKey를 입력하여 Publish합니다.
		    // (String)queue_name, (Boolean)durable, (Boolean)exclusive, (Boolean)autoDelete, (Map<String,Object>)arguments
		    // channel.queueDeclare(queue_name, false, false, false, null);
		    
		    // (String)exchange, (String)routingKey, (AMQP.BasicProperties)props, (byte[])body
		    channel.basicPublish(exchange_name, RoutingKey, null, message.getBytes("UTF-8"));
		    System.out.println(" [x] Sent '" + RoutingKey + "':'" + message + "'");
		    
		    channel.close();
		    connection.close();

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}

	@Override
	public void topicPublish(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception {
		log.info("topicPublish - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");
		
		String exchange_name = (String) rabbitmqEntity.getExchangeName();
		String RoutingKey = (String) rabbitmqEntity.getRoutingKey();
		String message = (String) rabbitmqEntity.getMessage();
		
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
			
		    Channel channel = connection.createChannel();
		    
		    // (String)exchange_name, (String)type
		    channel.exchangeDeclare(exchange_name, "topic");
		    
		    // Topic 방식이므로 BindingKey를 입력하여 Publish합니다.
		    // (String)queue_name, (Boolean)durable, (Boolean)exclusive, (Boolean)autoDelete, (Map<String,Object>)arguments
		    // channel.queueDeclare(queue_name, false, false, false, null);
		    
		    // (String)exchange, (String)routingKey, (AMQP.BasicProperties)props, (byte[])body
		    channel.basicPublish(exchange_name, RoutingKey, null, message.getBytes("UTF-8"));
		    System.out.println(" [x] Sent '" + RoutingKey + "':'" + message + "'");
		    
		    channel.close();
		    connection.close();

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}

}
