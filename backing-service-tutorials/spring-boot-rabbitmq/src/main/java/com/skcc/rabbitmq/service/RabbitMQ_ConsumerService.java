package com.skcc.rabbitmq.service;

import com.skcc.rabbitmq.models.RabbitMQ_AbstractEntity;

public interface RabbitMQ_ConsumerService {

	void simpleSubscribe(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception;

	void durableQueueSubscribe(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception;

	void ackSubscribe(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception;

	void fanoutSubscribe(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception;

	void directSubscribe(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception;

	void topicSubscribe(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception;

}
