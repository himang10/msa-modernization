package com.skcc.mongodb.exam03.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.skcc.mongodb.exam03.entity.Video;
import com.skcc.mongodb.exam03.service.VideoService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
	 * 사진 ID 목록 조회
	 * 
	 * @param nameRegex
	 * @return
	 */
	@ApiOperation(value = "ID 목록 조회")
    @GetMapping("/video/ids")
	public ResponseEntity<List<String>> getPhotoIds() {
		return new ResponseEntity<>(videoService.getVideos(), HttpStatus.OK);
	}
	
	/**
	 * 비디오 조회
	 * 
	 * @param nameRegex
	 * @return
	 */
	@ApiOperation(value = "비디오 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "",            required = true, dataType = "string", defaultValue = ""),
		@ApiImplicitParam(name = "title", value = "", required = true, dataType = "string", defaultValue = ""),
	})
    @GetMapping("/videos/{id}")
    public String getVideo(@PathVariable String id, Model model) throws IllegalStateException, IOException {
        Video video = videoService.getVideo(id);
        model.addAttribute("title", video.getTitle());
        model.addAttribute("url", "/videos/stream/" + id);
        return "videos";
    }

	@ApiOperation(value = "비디오 조회 (테스트 제외 API) - 비디오 조회 페이지를 통해 테스트")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "", required = true, dataType = "string", defaultValue = ""),
	})
    @GetMapping("/videos/stream/{id}")
    public void streamVideo(@PathVariable String id, HttpServletResponse response) throws IllegalStateException, IOException {
        Video video = videoService.getVideo(id);
        FileCopyUtils.copy(video.getStream(), response.getOutputStream());
    }

	/**
	 * 비디오 업로드
	 * 
	 * @param nameRegex
	 * @return
	 */
	@ApiOperation(value = "비디오 업로드")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "title", value = "", required = true, dataType = "string", defaultValue = ""),
	})
    @PostMapping("/videos/add")
    public String addVideo(@RequestParam("title") String title, @RequestParam("file") MultipartFile file, Model model) throws IOException {
    	String id = videoService.addVideo(title, file);
    	return "redirect:/videos/" + id;
    }

	/**
	 * 업로드 페이지 (Test 대상 제외)
	 *
	 * @param nameRegex
	 * @return
	 */
	@ApiOperation(value = "비디오 업로드 페이지 (테스트 제외 API) - http://127.0.0.1:8080/videos/upload")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "message", value = "hello", required = true, dataType = "string", defaultValue = "hello"),
	})
    @GetMapping("/videos/upload")
    public String uploadVideo(Model model) {
        model.addAttribute("message", "hello");
        return "uploadVideo";
    }

}
