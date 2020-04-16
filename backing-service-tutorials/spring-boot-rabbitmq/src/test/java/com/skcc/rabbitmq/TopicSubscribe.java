package com.skcc.rabbitmq;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TopicSubscribe {

	public static void main(String[] args) {
		String exchange_name = "";
		String buffer = "";
		String RoutingKey = "";
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Exchange 이름을 입력하세요.");
		exchange_name = sc.next();
		System.out.println("RoutingKey를 입력하세요");
		buffer = sc.nextLine();
		RoutingKey = sc.nextLine();
		
		sc.close();
		
		try {
			Connection connection = rabbitmqConnectionFactory().newConnection();
			Channel channel = connection.createChannel();
		    
		    channel.exchangeDeclare(exchange_name, "topic");
		    
			String queueName = channel.queueDeclare().getQueue();
			
			if (RoutingKey.length() < 1) {
				log.error("Routing Key 확인 필요 : "+RoutingKey);
				System.exit(1);
			}
			
			channel.queueBind(queueName, exchange_name, RoutingKey);

			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");
				System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
			};
			channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
			});
			
		} catch (Exception e) {
			log.error(e.getMessage());		
		}
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
