package com.skcc.sample;

import com.skcc.event.util.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class Subscriber {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @MessageListener(topics = {"multiple.order", "multiple.payment"})
    public void requestListener1(Message message) {
        logger.info(" [x] receive1 : " + message.getPayload().toString());
    }

    @MessageListener(topics = {"single.order"})
    public void requestListener2(Message message) {
        logger.info(" [x] receive2 : " + message.getPayload().toString());
    }
}
