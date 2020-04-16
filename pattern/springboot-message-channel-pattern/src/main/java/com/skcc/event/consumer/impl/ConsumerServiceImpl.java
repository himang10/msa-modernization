package com.skcc.event.consumer.impl;

import com.skcc.event.consumer.ConsumeService;
import com.skcc.event.util.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Service
public class ConsumerServiceImpl implements ConsumeService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String domainGroup;
    private String domainName;
    private MessageUtils messageUtils;

    @Autowired
    public ConsumerServiceImpl(@Value("${domain.group}") String domainGroup,
                               @Value("${domain.name}") String domainName,
                               MessageUtils messageUtils) {
        this.domainGroup = domainGroup;
        this.domainName = domainName;
        this.messageUtils = messageUtils;
    }

    @Override
    public void subscribe(String[] topics, Method method) {
        try {
            if (topics.length > 0) {
                String queueName = getQueueName(method.getDeclaringClass().getName(), method.getName());
                for (String topic : topics) {
                    String[] array = topic.split("\\.");
                    String exchangeName = "";
                    if (array.length > 1) {
                        exchangeName = this.getExchangeName(array[1]);
                    } else {
                        throw new Exception("Invalid Topic Format: Valid format is '{domain group}.{domain name}.{...}'.");
                    }
                    messageUtils.declareExchangeAndQueueAndBind(exchangeName, queueName, topic + ".#", method);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private String getExchangeName(String sourceService) {
        return StringUtils.isEmpty(sourceService) ? null : domainGroup + "." + sourceService + ".publish";
    }

    private String getQueueName(String... groups) {
        StringBuilder queue = new StringBuilder();
        if (domainGroup != null && domainGroup.length() > 0) {
            queue.append(domainGroup).append(".");
        }
        if (domainName != null && domainName.length() > 0) {
            queue.append(domainName).append(".");
        }
        if (groups != null && groups.length > 0) {
            for (String group : groups) {
                queue.append(".").append(group);
            }
        }
        return queue.toString();
    }

}
