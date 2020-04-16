package com.skcc.rabbitmq.service;

import com.skcc.rabbitmq.models.RabbitMQ_AbstractEntity;

public interface RabbitMQ_ProducerService {

	void simplePublish(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception;

	void changingQueuePublish(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception;

	void durableQueuePublish(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception;

	void fanoutPublish(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception;

	void directPublish(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception;

	void topicPublish(RabbitMQ_AbstractEntity rabbitmqEntity) throws Exception;

}
