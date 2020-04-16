package com.skcc.mongodb.exam03.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.skcc.mongodb.exam03.entity.User;

public interface UserRepository extends MongoRepository<User, Long> {

}
