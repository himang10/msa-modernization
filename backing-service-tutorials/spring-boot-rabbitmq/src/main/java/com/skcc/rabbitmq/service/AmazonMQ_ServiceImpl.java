package com.skcc.rabbitmq.service;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.skcc.rabbitmq.config.AmazonMQ_AbstractConfig;
import com.skcc.rabbitmq.models.AmazonMQ_AbstractEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AmazonMQ_ServiceImpl implements AmazonMQ_Service{
	
	@Autowired
	private AmazonMQ_AbstractConfig amazonmqConfig;
	
	@Autowired
	protected Environment environment;
	
    private static final int DELIVERY_MODE = DeliveryMode.NON_PERSISTENT;
    private static final int ACKNOWLEDGE_MODE = Session.AUTO_ACKNOWLEDGE;

    private static final String QUEUE = "MyQueue";

    
	@Override
	public void simplePublish(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception {
		log.info("simplePublish - Config("+environment.getProperty("spring.amazonmq.host")+":"
					+environment.getProperty("spring.amazonmq.port")+")");

		String queue_name = (String) amazonmqEntity.getQueueName();
		String message = (String) amazonmqEntity.getMessage();
		
		Connection producerConnection = amazonmqConfig.amazonmqProducerConnection();
        producerConnection.start();
        
        // Create a session.
        Session producerSession = producerConnection.createSession(false, ACKNOWLEDGE_MODE);

        // Create a queue named "MyQueue".
        Destination producerDestination = producerSession.createQueue(queue_name);
        log.info("simplePublish - connected to QUEUE : '"+queue_name+"'");
        

        // Create a producer from the session to the queue.
        MessageProducer producer = producerSession.createProducer(producerDestination);
        producer.setDeliveryMode(DELIVERY_MODE);

        // Create a message. send message
        TextMessage producerMessage = producerSession.createTextMessage(message);
        producer.send(producerMessage);
        System.out.println("simplePublish - Message '"+message+"'sent.");

        // Clean up the producer.
        producer.close();
        producerSession.close();
        producerConnection.close();
    }
    

	@Override
	public void topicPublish(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception {
		log.info("topicPublish - Config("+environment.getProperty("spring.amazonmq.host")+":"
				+environment.getProperty("spring.amazonmq.port")+")");
	
		String topic_name = (String) amazonmqEntity.getTopicName();
		String message = (String) amazonmqEntity.getMessage();
		
		Connection producerConnection = amazonmqConfig.amazonmqProducerConnection();
	    producerConnection.start();
	    
	    // Create a session.
	    Session producerSession = producerConnection.createSession(false, ACKNOWLEDGE_MODE);
	
	    // Create a queue named "MyQueue".
	    Destination producerDestination = producerSession.createTopic(topic_name);
	    log.info("topicPublish - connected to TOPIC : '"+topic_name+"'");
	    
	
	    // Create a producer from the session to the queue.
	    MessageProducer producer = producerSession.createProducer(producerDestination);
	    producer.setDeliveryMode(DELIVERY_MODE);
	
	    // Create a message. send message
	    TextMessage producerMessage = producerSession.createTextMessage(message);
	    producer.send(producerMessage);
	    System.out.println("topicPublish - Message '"+message+"'sent.");
	
	    // Clean up the producer.
	    producer.close();
	    producerSession.close();
	    producerConnection.close();
	}
	
	@Override
	public void virtualTopicPublish(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception {
		log.info("virtualTopicPublish - Config("+environment.getProperty("spring.amazonmq.host")+":"
				+environment.getProperty("spring.amazonmq.port")+")");
	
		String topic_name = "VirtualTopic."+(String) amazonmqEntity.getTopicName();
		String message = (String) amazonmqEntity.getMessage();
		
		Connection producerConnection = amazonmqConfig.amazonmqProducerConnection();
	    producerConnection.start();
	    
	    // Create a session.
	    Session producerSession = producerConnection.createSession(false, ACKNOWLEDGE_MODE);
	
	    // Create a queue named "MyQueue".
	    Destination producerDestination = producerSession.createTopic(topic_name);
	    log.info("virtualTopicPublish - connected to TOPIC : '"+topic_name+"'");
	    
	
	    // Create a producer from the session to the queue.
	    MessageProducer producer = producerSession.createProducer(producerDestination);
	    producer.setDeliveryMode(DELIVERY_MODE);
	
	    // Create a message. send message
	    TextMessage producerMessage = producerSession.createTextMessage(message);
	    producer.send(producerMessage);
	    System.out.println("virtualTopicPublish - Message '"+message+"'sent.");
	
	    // Clean up the producer.
	    producer.close();
	    producerSession.close();
	    producerConnection.close();
	}
	
	
	/*===============================================================================================
	 * Consumer Section
	 ===============================================================================================*/
	
	public void simpleConsume(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception {
		log.info("simpleConsume - Config("+environment.getProperty("spring.amazonmq.host")+":"
				+environment.getProperty("spring.amazonmq.port")+")");
		
		String queue_name = (String) amazonmqEntity.getQueueName();
		
        // 참고: 소비자는 PooledConnectionFactory를 사용해서는 안 됩니다.
        Connection consumerConnection = amazonmqConfig.amazonmqConsumerConnection();
        consumerConnection.start();

        // 세션 생성.
        Session consumerSession = consumerConnection.createSession(false, ACKNOWLEDGE_MODE);

        // "MyQueue"라는 대기열 생성.
        Destination consumerDestination = consumerSession.createQueue(queue_name);
        log.info("simpleConsume - connnected to QUEUE : '"+queue_name+"'");

        // 세션에서 대기열에 대한 메시지 소비자 생성.
        MessageConsumer consumer = consumerSession.createConsumer(consumerDestination);

        // 메시지를 기다리기 시작.
        Message consumerMessage = consumer.receive(1000); //ms단위

        // 메시지가 도착하면 이를 수신.
        TextMessage consumerTextMessage = (TextMessage) consumerMessage;
        System.out.println("Message received: " + consumerTextMessage.getText());
        
        // 소비자 정리.
        consumer.close();
        consumerSession.close();
        consumerConnection.close();
//        pooledConnectionFactory.stop();

	}

	@Override
	public void longConsume(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception {
		log.info("longConsume - Config("+environment.getProperty("spring.amazonmq.host")+":"
				+environment.getProperty("spring.amazonmq.port")+")");
		
		String queue_name = (String) amazonmqEntity.getQueueName();
		
        // 참고: 소비자는 PooledConnectionFactory를 사용해서는 안 됩니다.
        Connection consumerConnection = amazonmqConfig.amazonmqConsumerConnection();
        consumerConnection.start();

        // 세션 생성.
        Session consumerSession = consumerConnection.createSession(false, ACKNOWLEDGE_MODE);

        // "MyQueue"라는 대기열 생성.
        Destination consumerDestination = consumerSession.createQueue(queue_name);
        log.info("longConsume - connnected to QUEUE : '"+queue_name+"'");

        // 세션에서 대기열에 대한 메시지 소비자 생성.
        MessageConsumer consumer = consumerSession.createConsumer(consumerDestination);

        int i =1;
        while(i<6) {
        	Message consumerMessage = consumer.receive(); //msg가 receive될 때 까지 기다린다.
        	
            TextMessage consumerTextMessage = (TextMessage) consumerMessage;
            System.out.println("Message"+i+" received: " + consumerTextMessage.getText());
            i++;
        }

        // 소비자 정리.
        consumer.close();
        consumerSession.close();
        consumerConnection.close();
//        pooledConnectionFactory.stop();
	}


	@Override
	public void simpleSubscribe(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception {
		log.info("simpleSubscribe - Config("+environment.getProperty("spring.amazonmq.host")+":"
				+environment.getProperty("spring.amazonmq.port")+")");
		
		String topic_name = (String) amazonmqEntity.getTopicName();
		
        // 참고: 소비자는 PooledConnectionFactory를 사용해서는 안 됩니다.
        Connection consumerConnection = amazonmqConfig.amazonmqConsumerConnection();
        consumerConnection.start();

        // 세션 생성.
        Session consumerSession = consumerConnection.createSession(false, ACKNOWLEDGE_MODE);

        // "MyQueue"라는 대기열 생성.
        Destination consumerDestination = consumerSession.createTopic(topic_name);
        log.info("simpleSubscribe - connnected to TOPIC : '"+topic_name+"'");

        // 세션에서 대기열에 대한 메시지 소비자 생성.
        MessageConsumer consumer = consumerSession.createConsumer(consumerDestination);

        // 메시지를 기다리기 시작.
        log.info("simpleSubscribe - waiting for TOPIC : '"+topic_name+"'...");
        Message consumerMessage = consumer.receive(); //ms단위

        // 메시지가 도착하면 이를 수신.
        TextMessage consumerTextMessage = (TextMessage) consumerMessage;
        System.out.println("Message received: " + consumerTextMessage.getText());
        
        // 소비자 정리.
        consumer.close();
        consumerSession.close();
        consumerConnection.close();
//        pooledConnectionFactory.stop();
		
	}


	@Override
	public void virtualTopicSubscribe(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception {
		log.info("simpleConsume - Config("+environment.getProperty("spring.amazonmq.host")+":"
				+environment.getProperty("spring.amazonmq.port")+")");
		
		String queue_name = "Consumer."+(String) amazonmqEntity.getQueueName()+".VirtualTopic." + (String) amazonmqEntity.getTopicName();
		
        // 참고: 소비자는 PooledConnectionFactory를 사용해서는 안 됩니다.
        Connection consumerConnection = amazonmqConfig.amazonmqConsumerConnection();
        consumerConnection.start();

        // 세션 생성.
        Session consumerSession = consumerConnection.createSession(false, ACKNOWLEDGE_MODE);

        // "MyQueue"라는 대기열 생성.
        Destination consumerDestination = consumerSession.createQueue(queue_name);
        log.info("virtualTopicSubscribe - connnected to QUEUE : '"+queue_name+"'");

        // 세션에서 대기열에 대한 메시지 소비자 생성.
        MessageConsumer consumer = consumerSession.createConsumer(consumerDestination);

        // 메시지를 기다리기 시작.
        Message consumerMessage = consumer.receive(2000); //ms단위

        // 메시지가 도착하면 이를 수신.
        TextMessage consumerTextMessage = (TextMessage) consumerMessage;
        System.out.println("Message received: " + consumerTextMessage.getText());
        // 소비자 정리.
        
        consumer.close();
        consumerSession.close();
        consumerConnection.close();
//        pooledConnectionFactory.stop();
	}

	
}
