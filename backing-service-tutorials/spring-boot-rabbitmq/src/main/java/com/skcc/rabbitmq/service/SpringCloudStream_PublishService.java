package com.skcc.rabbitmq.service;

import com.skcc.rabbitmq.models.SpringCloudStream_sampleEntity;

public interface SpringCloudStream_PublishService {

	void simplePublish(String message) throws Exception;

	void simplePublish2(SpringCloudStream_sampleEntity entity) throws Exception;

}
