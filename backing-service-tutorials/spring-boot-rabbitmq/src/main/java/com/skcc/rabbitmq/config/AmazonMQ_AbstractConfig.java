package com.skcc.rabbitmq.config;

import java.util.Hashtable;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.jms.Connection;

@Slf4j
@Configuration
public class AmazonMQ_AbstractConfig {
   
	 @Value("${spring.amazonmq.host}")
	 private String hostname;
	
	 @Value("${spring.amazonmq.port}")
	 private int portnum;
	 
	 @Value("${spring.amazonmq.username}")
	 private String username;
	 
	 @Value("${spring.amazonmq.password}")
	 private String awsmqqpw;
	 
	 public ConnectionFactory amazonmqConnectionFactory() throws NamingException {
		 
		 String ENDPOINT = hostname + ":" + portnum; 
		 
	     // Use JNDI to specify the AMQP endpoint
	     Hashtable<Object, Object> env = new Hashtable<Object, Object>();
	     env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
	     env.put("connectionfactory.factoryLookup", ENDPOINT);
	     javax.naming.Context context = new javax.naming.InitialContext(env);
	     // Create a connection factory.
	     ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("factoryLookup");
	     
		 return connectionFactory;
	 }
	 
	 public Connection amazonmqProducerConnection() throws NamingException {
		 log.info("doing creating producer's connection");

		 ConnectionFactory connectionFactory = amazonmqConnectionFactory();

	     // Create a pooled connection factory.
	     PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
	     pooledConnectionFactory.setConnectionFactory(connectionFactory);
	     pooledConnectionFactory.setMaxConnections(10);

	     // Establish a connection for the producer.
	     Connection producerConnection = null;
		try {
			producerConnection = pooledConnectionFactory.createConnection(username, awsmqqpw);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return producerConnection;
	 }

	 public Connection amazonmqConsumerConnection() throws NamingException, JMSException {
		 log.info("creating consumer's connection");
		 ConnectionFactory connectionFactory = amazonmqConnectionFactory();

		 Connection consumerConnection = connectionFactory.createConnection(username, awsmqqpw);

	     return consumerConnection;
	 }
	 
}
