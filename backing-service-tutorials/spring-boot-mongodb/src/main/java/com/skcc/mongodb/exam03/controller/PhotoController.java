package com.skcc.mongodb.exam03.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.skcc.mongodb.exam03.entity.Photo;
import com.skcc.mongodb.exam03.service.PhotoService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 사진 업로드 및 조회
 * @author a06919
 *
 */
@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    /**
	 * 사진 ID 목록 조회
	 * 
	 * @param nameRegex
	 * @return
	 */
	@ApiOperation(value = "ID 목록 조회")
    @GetMapping("/photo/ids")
	public ResponseEntity<List<String>> getPhotoIds() {
		return new ResponseEntity<>(photoService.getPhotos(), HttpStatus.OK);
	}
    
	/**
	 * 사진 조회
	 * 
	 * @param nameRegex
	 * @return
	 */
	@ApiOperation(value = "사진 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "title", value = "사진_제목", required = true, dataType = "string", defaultValue = "사진_제목"),
	})
    @GetMapping("/photos/{id}")
    public String getPhoto(@PathVariable String id, Model model) {
        Photo photo = photoService.getPhoto(id);
        model.addAttribute("title", photo.getTitle());
        model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
        return "photos";
    }

	/**
	 * 사진 업로드
	 * 
	 * @param nameRegex
	 * @return
	 */
	@ApiOperation(value = "사진 업로드")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "title", value = "사진_제목", required = true, dataType = "string", defaultValue = "사진_제목"),
	})
	@PostMapping("/photos/add")
	public String addPhoto(@RequestParam("title") String title, @RequestParam("image") MultipartFile image, Model model) throws IOException {
		String id = photoService.addPhoto(title, image);
		return "redirect:/photos/" + id;
	}

	/**
	 * 업로드 페이지 (Test 대상 제외) - http://127.0.0.1:8080/photos/upload
	 *
	 * @param nameRegex
	 * @return
	 */
	@ApiOperation(value = "사진 업로드 페이지 (테스트 제외 API) - http://127.0.0.1:8080/photos/upload")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "message", value = "hello", required = true, dataType = "string", defaultValue = "hello"),
	})
    @GetMapping("/photos/upload")
    public String uploadPhoto(Model model) {
        model.addAttribute("message", "hello");
        return "uploadPhoto";
    }

}
