package com.skcc.mongodb.exam05.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.repository.init.Jackson2ResourceReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.mongodb.exam05.services.TextSearchService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/exam5")
public class TextSearchRestController {
	@Autowired
	private TextSearchService textSearchService;
	
	@Autowired MongoOperations operations;
	
	@ApiOperation(value = "초기 데이터를 입력 합니다.")
	@GetMapping("/initData")
	public ResponseEntity<String> initData() {
		try {
			Jackson2ResourceReader reader = new Jackson2ResourceReader();
			
			Object source = reader.readFrom(new ClassPathResource("data/spring-blog.atom.json"), this.getClass().getClassLoader());
			
			if (source instanceof Iterable) {
				((Iterable) source).forEach(element -> operations.save(element));
			} else {
				operations.save(source);
			}
			
			log.info("Imported blog posts from classpath!");
			
			return new ResponseEntity<>("init data success!", HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>("init data fail! \n" + e.getMessage(), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "매칭되는 단어 검색")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "matchingAny", value = "", allowMultiple = true, paramType = "query", dataType = "string")
    })
	@GetMapping("/findAllBlogPostsWithRelease")
	public ResponseEntity<String> findAllBlogPostsWithRelease(@RequestParam(value = "matchingAny") String[] matchingAny) {
		return new ResponseEntity<>(textSearchService.findAllBlogPostsWithRelease(matchingAny), HttpStatus.OK);
	}
	
	@ApiOperation(value = "매칭 단어 & 제외 단어 검색")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "matchingAny", value = "", allowMultiple = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "notMatching", value = "", required = true, dataType = "string", defaultValue = "engineering")
    })
	@GetMapping("/findAllBlogPostsWithReleaseButHeyIDoWantTheEngineeringStuff")
	public ResponseEntity<String> findAllBlogPostsWithReleaseButHeyIDoWantTheEngineeringStuff(@RequestParam(value = "matchingAny") String[] matchingAny,
			@RequestParam(value = "notMatching") String notMatching) {
		return new ResponseEntity<>(textSearchService.findAllBlogPostsWithReleaseButHeyIDoWantTheEngineeringStuff(matchingAny, notMatching), HttpStatus.OK);
	}
	
	@ApiOperation(value = "매칭 구문 검색")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "matchingPhrase", value = "", required = true, dataType = "string", defaultValue = "release candidate"),
    })
	@GetMapping("/findAllBlogPostsByPhrase")
	public ResponseEntity<String> findAllBlogPostsByPhrase(@RequestParam(value = "matchingPhrase") String matchingPhrase) {
		return new ResponseEntity<>(textSearchService.findAllBlogPostsByPhrase(matchingPhrase), HttpStatus.OK);
	}
	
	@ApiOperation(value = "매칭 구문 검색 (스코어에 따른 정렬) - title(3), content(2), categories(1)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "matchingPhrase", value = "", required = true, dataType = "string", defaultValue = "release candidate"),
    })
	@GetMapping("/findAllBlogPostsByPhraseSortByScore")
	public ResponseEntity<String> findAllBlogPostsByPhraseSortByScore(@RequestParam(value = "matchingPhrase") String matchingPhrase) {
		return new ResponseEntity<>(textSearchService.findAllBlogPostsByPhraseSortByScore(matchingPhrase), HttpStatus.OK);
	}
}