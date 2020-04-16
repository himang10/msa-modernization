package com.skcc.mongodb.exam09.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.skcc.mongodb.exam09.entity.User;


public interface TransactionalUserRepository extends MongoRepository<User, String> {
}
