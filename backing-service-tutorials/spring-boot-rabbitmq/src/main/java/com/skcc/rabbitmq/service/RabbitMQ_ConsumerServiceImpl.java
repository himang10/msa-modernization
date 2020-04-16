package com.skcc.rabbitmq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.skcc.rabbitmq.config.RabbitMQ_AbstractConfig;
import com.skcc.rabbitmq.models.RabbitMQ_AbstractEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RabbitMQ_ConsumerServiceImpl implements RabbitMQ_ConsumerService {
	
	@Autowired
	private RabbitMQ_AbstractConfig rabbitmqConfig;
	
	@Autowired
	protected Environment environment;
	
	@Override
	public void simpleSubscribe(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception {
		log.info("simpleSubscribe - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");
		
		String queue_name = (String) rabbitmqEntity.getQueueName();
		
		String getData = "";
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
		    Channel channel = connection.createChannel();

		    channel.queueDeclare(queue_name, false, false, false, null);
		    
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(queue_name, true, deliverCallback, consumerTag -> { });


		} catch (Exception e) {
			log.error(e.getMessage());		
		}
		
	}

	@Override
	public void durableQueueSubscribe(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception {
		log.info("durableQueueSubscribe - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");

		String queue_name = (String) rabbitmqEntity.getQueueName();
		
		String getData = "";
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
		    Channel channel = connection.createChannel();

		    channel.queueDeclare(queue_name, true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(queue_name, true, deliverCallback, consumerTag -> { });

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}
	
	@Override
	public void ackSubscribe(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception {
		log.info("ackSubscribe - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");
		
		String queue_name = (String) rabbitmqEntity.getQueueName();
		
		String getData = "";
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
		    Channel channel = connection.createChannel();

		    channel.queueDeclare(queue_name, true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            
            channel.basicQos(1);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
    			String message = new String(delivery.getBody(), "UTF-8");

    			System.out.println(" [x] Received '" + message + "'");
    			try {
    				for (char ch : message.toCharArray()) {
    					if (ch == '.') {
    						try {
    							Thread.sleep(1000);
    						} catch (InterruptedException _ignored) {
    							Thread.currentThread().interrupt();
    						}
    					}
    				}
    			} finally {
    				System.out.println(" [x] Done");
    				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    			}
    		};

    		channel.basicConsume(queue_name, false, deliverCallback, consumerTag -> {
    		});

		} catch (Exception e) {
			log.error(e.getMessage());		
		}
	}

	@Override
	public void fanoutSubscribe(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception {
		log.info("fanoutSubscribe - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");
		
		String exchange_name = (String) rabbitmqEntity.getExchangeName();
		
		String getData = "";
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
		    Channel channel = connection.createChannel();
		    
		    channel.exchangeDeclare(exchange_name, "fanout");
		    
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, exchange_name, "");

			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			};
			
			channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
			});
			
		} catch (Exception e) {
			log.error(e.getMessage());		
		}
		
	}

	@Override
	public void directSubscribe(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception {
		log.info("directSubscribe - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");
		
		String exchange_name = (String) rabbitmqEntity.getExchangeName();
		String RoutingKey = (String) rabbitmqEntity.getRoutingKey();
		
		String getData = "";
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
		    Channel channel = connection.createChannel();
		    
		    channel.exchangeDeclare(exchange_name, "direct");
		    
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

	@Override
	public void topicSubscribe(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception {
		log.info("topicSubscribe - Config("+environment.getProperty("spring.rabbitmq.host")+":"+environment.getProperty("spring.rabbitmq.port")+")");
		
		String exchange_name = (String) rabbitmqEntity.getExchangeName();
		String RoutingKey = (String) rabbitmqEntity.getRoutingKey();
		
		String getData = "";
		try {
			Connection connection = rabbitmqConfig.rabbitmqConnectionFactory().newConnection();
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

}
