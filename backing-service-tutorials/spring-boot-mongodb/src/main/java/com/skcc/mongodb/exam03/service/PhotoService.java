package com.skcc.mongodb.exam03.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.skcc.mongodb.exam03.entity.Photo;
import com.skcc.mongodb.exam03.repository.PhotoRepository;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepo;

    public List<String> getPhotos() {
        return photoRepo.findAll().stream().map(c -> {
			String id = c.getId();
			return id;
		}).collect(Collectors.toList());
    }
    
    public Photo getPhoto(String id) {
        return photoRepo.findById(id).get();
    }

    public String addPhoto(String title, MultipartFile file) throws IOException {
        Photo photo = new Photo(title);
        photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo = photoRepo.insert(photo);
        return photo.getId();
    }
}
