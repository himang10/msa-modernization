package com.skcc.event.util;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

@Component
public class MessageUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RabbitAdmin rabbitAdmin;

    @Autowired
    public MessageUtils(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    public void declareExchange(String exchangeName) {
        try {
            if (StringUtils.isEmpty(exchangeName)) {
                throw new Exception("The exchange name is required.");
            }
            rabbitAdmin.declareExchange(new TopicExchange(exchangeName));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void declareQueue(String queueName) {
        try {
            if (StringUtils.isEmpty(queueName)) {
                throw new Exception("The queue name is required.");
            }
            rabbitAdmin.declareQueue(new Queue(queueName));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void bindQueue(String exchange, String queue, String topic) {
        try {
            if (StringUtils.isEmpty(exchange) || StringUtils.isEmpty(queue)) {
                throw new Exception("The exchange and queue name are required.");
            }
            rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue(queue)).to(new TopicExchange(exchange)).with(topic));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void declareExchangeAndQueueAndBind(String exchangeName, String queueName, String topic, Method method) {
        try {
            if (StringUtils.isEmpty(exchangeName) || StringUtils.isEmpty(queueName)) {
                throw new Exception("The exchange and queue name are required.");
            }
            ConnectionFactory connectionFactory = rabbitAdmin.getRabbitTemplate().getConnectionFactory();
            Connection connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(true);

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true, true, null);
            channel.queueDeclare(queueName, true, false, true, null);
            channel.queueBind(queueName, exchangeName, topic);

            logger.info(method.getName());
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           byte[] body) {
                    String message = new String(body, StandardCharsets.UTF_8);
                    try {
                        Class<?> cls = Class.forName(method.getDeclaringClass().getName());
                        Object object = cls.newInstance();
                        method.invoke(object, MessageBuilder.withPayload(message).build());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            channel.basicQos(1);
            channel.basicConsume(queueName, true, "", false, false, null, consumer);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
