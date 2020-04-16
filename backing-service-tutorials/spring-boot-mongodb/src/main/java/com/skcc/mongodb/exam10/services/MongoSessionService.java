package com.skcc.mongodb.exam10.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.ClientSessionOptions;
import com.mongodb.client.ClientSession;
import com.skcc.mongodb.config.MongoConfig;
import com.skcc.mongodb.exam09.entity.User;

@Service
public class MongoSessionService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired MongoConfig mongoConfig;
	
	public void withSession() {
		ClientSessionOptions sessionOptions = ClientSessionOptions.builder().causallyConsistent(true).build();

		ClientSession session = mongoTemplate.getMongoDbFactory().getSession(sessionOptions);
		session.startTransaction();
		
		// callback
		User user = mongoTemplate.withSession(() -> session).execute(action -> {
			Query query = new Query().addCriteria(Criteria.where("name").is("John"));
			User durzo = action.findOne(query, User.class);
			Integer age = durzo.getAge();

			User newUser = new User("John", age);
			action.insert(newUser, "user");

			return newUser;
		});

		session.commitTransaction();
		session.close();
	}
	
}