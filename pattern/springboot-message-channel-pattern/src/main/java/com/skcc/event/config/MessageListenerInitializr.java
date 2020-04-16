package com.skcc.event.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import com.skcc.event.consumer.ConsumeService;
import com.skcc.event.util.MessageListener;
import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

@Component
public class MessageListenerInitializr implements ApplicationRunner {

    private ConsumeService consumeService;
    private ConnectionFactory connectionFactory;

    @Autowired
    public MessageListenerInitializr(ConnectionFactory connectionFactory,
                                     ConsumeService consumeService) {
        this.consumeService = consumeService;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void run(ApplicationArguments args) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(""))
                .setScanners(new MethodAnnotationsScanner())
                .filterInputsBy(new FilterBuilder().includePackage(""))
        );
        Set<Method> method = reflections.getMethodsAnnotatedWith(MessageListener.class);
        method.forEach((m) -> {
            Annotation[] annotations = m.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof MessageListener) {
                    MessageListener listener = (MessageListener) annotation;
                    String[] topics = listener.topics();
                    if (topics.length > 0) {
                        consumeService.subscribe(topics, m);
                    }
                }
            }
        });
    }
}
