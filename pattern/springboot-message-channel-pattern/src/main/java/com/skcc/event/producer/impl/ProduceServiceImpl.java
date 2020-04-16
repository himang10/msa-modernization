package com.skcc.event.producer.impl;

import com.skcc.event.producer.ProduceService;
import com.skcc.event.util.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProduceServiceImpl implements ProduceService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String domainGroup;
    private String domainName;
    private RabbitTemplate rabbitTemplate;
    private MessageUtils messageUtils;

    @Autowired
    public ProduceServiceImpl(@Value("${domain.group}") String domainGroup,
                              @Value("${domain.name}") String domainName,
                              RabbitTemplate rabbitTemplate,
                              MessageUtils messageUtils) {
        this.domainGroup = domainGroup;
        this.domainName = domainName;
        this.rabbitTemplate = rabbitTemplate;
        this.messageUtils = messageUtils;
    }

    @Override
    public void publish(String topic, Message message) {
        try {
            String exchangeName = getExchangeName(domainName);
            messageUtils.declareExchange(exchangeName);
            rabbitTemplate.convertAndSend(exchangeName, topic, message);
            rabbitTemplate.setChannelTransacted(true);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private String getExchangeName(String sourceService) {
        return StringUtils.isEmpty(sourceService) ? null : domainGroup + "." + domainName + ".publish";
    }
}
