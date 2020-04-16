package com.skcc.rabbitmq;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DirectPublish {

	public static void main(String[] args) throws Exception {
		
		String exchange_name = "";
		String RoutingKey = "";
		String message = "";
		String buffer = "";
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Exchange 이름을 입력하세요.");
		exchange_name = sc.next();
		System.out.println("Routing Key를 입력하세요");
		buffer = sc.nextLine();
		RoutingKey = sc.nextLine();
		System.out.println("Message를 입력하세요");
		buffer = sc.nextLine();
		message = sc.nextLine();
		
		try {
			Connection connection = rabbitmqConnectionFactory().newConnection();
			
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
		
		sc.close();
	}
	
	public static ConnectionFactory rabbitmqConnectionFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(System.getProperty("spring.rabbitmq.host"));
		factory.setPort(Integer.valueOf(System.getProperty("spring.rabbitmq.port")));
		factory.setUsername(System.getProperty("spring.rabbitmq.username"));
		factory.setPassword(System.getProperty("spring.rabbitmq.password"));
		factory.setVirtualHost(System.getProperty("spring.rabbitmq.virtual-host"));
		
		return factory;
	}

}
