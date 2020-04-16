package com.skcc.rabbitmq.service;

import com.skcc.rabbitmq.models.AmazonMQ_AbstractEntity;

public interface AmazonMQ_Service {

	void simplePublish(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception;
	void topicPublish(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception;
	void virtualTopicPublish(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception;

	void simpleConsume(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception;
	void longConsume(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception;
	void simpleSubscribe(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception;
	void virtualTopicSubscribe(AmazonMQ_AbstractEntity amazonmqEntity) throws Exception;

}
