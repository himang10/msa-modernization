package com.skcc.mongodb.exam09.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skcc.mongodb.exam09.entity.User;
import com.skcc.mongodb.exam09.repository.TransactionalUserRepository;

@Service
//@Scope("prototype")
public class MongoTransactionalService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private TransactionalUserRepository userRepository;

	@Transactional
	public void whenPerformMongoTransaction_thenSuccess() {
		
		mongoTemplate.insert(new User("John", 30), "user");
		
		Query query = new Query().addCriteria(Criteria.where("name").is("John"));
		List<User> users = mongoTemplate.find(query, User.class);

		System.out.println(users.size());

		mongoTemplate.insert(new User("Ringo", 35), "user");
		
		List<User> findAll = mongoTemplate.findAll( User.class);
		
		System.out.println(findAll.size());
	}

	//com.mongodb.MongoCommandException: 
	//Command failed with error 263 (OperationNotSupportedInTransaction): 'Cannot run 'listCollections' in a multi-document transaction.' on server 127.0.0.1:30001.
	@Transactional
	public void whenListCollectionDuringMongoTransaction_thenException() {
		if (mongoTemplate.collectionExists(User.class)) {
			mongoTemplate.save(new User("John", 30));
			mongoTemplate.save(new User("Ringo", 35));
		}
	}

	@Transactional
	public void whenCountDuringMongoTransaction_thenException() {
		userRepository.save(new User("John", 30));
		userRepository.save(new User("Ringo", 35));
		userRepository.count();
	}

	@Transactional
	public void save_and_rollback() {
		userRepository.save(new User("Jane3", 20));
		
		String a = null;
		a.indexOf("a");
		
		userRepository.save(new User("Nick3", 33));
		List<User> users = mongoTemplate.find(new Query(), User.class);

		
		
		System.out.println(users.size());
	}

	// ==== Using test instead of before and after due to @transactional doesn't
	// allow list collection

	public void setup() {
		if (!mongoTemplate.collectionExists(User.class)) {
			mongoTemplate.createCollection(User.class);
		}
	}

	public void ztearDown() {
		mongoTemplate.dropCollection(User.class);
	}
}