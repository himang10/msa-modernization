package com.skcc.rabbitmq;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChangingQueuePublish {

public static void main(String[] args) throws Exception {
		
		String default_queue_name = "";
		int count = 0;
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Queue를 입력하세요. ex) Hello");
		default_queue_name = sc.next();
		System.out.println("Publish할 횟수를 입력하세요.");
		count = sc.nextInt();
		
		try {
			Connection connection = rabbitmqConnectionFactory().newConnection();
			
			Channel channel = connection.createChannel();
		    
		    for(int i=0; i<count; i++) {
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
