package com.skcc.mongodb.exam03.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.skcc.mongodb.exam03.entity.Photo;

public interface PhotoRepository extends MongoRepository<Photo, String> {

}
