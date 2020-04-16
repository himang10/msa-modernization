package com.skcc.mongodb.exam09.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
//@EnableMongoRepositories(basePackages = "com.skcc.mongodb")
public class MongoTransactionConfig {
    @Bean
    MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
    	return new MongoTransactionManager(dbFactory);
    }
}
