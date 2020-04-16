package com.skcc.event.producer;

import org.springframework.messaging.Message;

public interface ProduceService {

    void publish(String topic, Message message);
}
