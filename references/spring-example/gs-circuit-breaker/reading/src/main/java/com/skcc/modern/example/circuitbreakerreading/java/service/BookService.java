package com.skcc.modern.example.circuitbreakerreading.java.service;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class BookService {

	private final RestTemplate restTemplate;
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	public BookService(RestTemplate rest) {
		this.restTemplate = rest;
	}

	/**
	 * 상세 설정 정보는 해당 Configuration 페이지에서 확인 가능
	 * https://github.com/Netflix/Hystrix/wiki/Configuration
	 * 
	 * @return
	 */
	@HystrixCommand(
			// circuit의 key로 사용되면 기본값은 method 명으로 처리
			commandKey = "readingList",
			// fallback할 Method
			fallbackMethod = "reliable",
			// 설정 Properties
			commandProperties = {
					// HystrixTimeout 시간 제한 설정. 기본값 1000
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
					// Circuit을 열기 위한 정해진 시간 동안의 error 비율. 기본값 50
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
					// circuit 을 열기 위한 호출 횟수. 기본값 20
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
					// circuit 기동시 유지시간. 기본값 5000
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "6000") })
	public String readingList() {
		URI uri = URI.create("http://circuit-breaker-bookstore:8080/recommended");
		return this.restTemplate.getForObject(uri, String.class);
	}
	
	public String reliable(Throwable t) {
		logger.info(t.getMessage());
		return "Cloud Native Java (O'Reilly)";
	}

}
