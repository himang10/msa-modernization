package com.skcc.mongodb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoRepositories(basePackages = "com.skcc.mongodb")
public class MongoConfig extends AbstractMongoConfiguration {

	@Value("${spring.data.mongodb.host:127.0.0.1}")
	public String host;
	
	@Value("${spring.data.mongodb.port:27017}")
	public String port;
	
	@Value("${spring.data.mongodb.username:root}")
	public String username;
	
	@Value("${spring.data.mongodb.password}")
	public String password;
	
	@Value("${spring.data.mongodb.database:exam}")
	public String database;

	@Override
	protected String getDatabaseName() {
		return "exam";
	}

	@Override
	public MongoClient mongoClient() {
		String uri = String.format("mongodb://%s:%s@%s:%s/%s?authSource=admin", username, password, host, port, database);
		MongoClientURI connStr = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(connStr);

		return mongoClient;
	}
}
