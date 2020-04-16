package com.skcc.modern.example.apicomm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class MethodTypeController {
	
	@RequestMapping(value = "/v1/methodTypeGet", method = RequestMethod.GET)
	@ApiOperation(value = "GET Method", notes = "Get Method에 대한 예시", response= String.class)
    public String methodTypeGet(
    		@ApiParam("Parameter에 대한 설명") @RequestParam(required = true) String key) {
        return "methodTypeGet";
    }
	
	@RequestMapping(value = "/v1/methodTypePut", method = RequestMethod.PUT)
	@ApiOperation(value = "PUT Method", notes = "PUT Method에 대한 예시", response= String.class)
    public String methodTypePut(
    		@ApiParam("Parameter에 대한 설명") @RequestParam(required = true) String key,
    		@ApiParam("Parameter에 대한 설명") @RequestParam(required = true) String value) {
        return "methodTypePut";
    }
	
	@RequestMapping(value = "/v1/methodTypeDelete", method = RequestMethod.DELETE)
	@ApiOperation(value = "DELETE Method", notes = "DELETE Method에 대한 예시", response= String.class)
    public String methodTypeDelete(
    		@ApiParam("Parameter에 대한 설명") @RequestParam(required = true) String key) {
        return "methodTypeDelete";
    }
	
	@RequestMapping(value = "/v1/methodTypePost", method = RequestMethod.POST)
	@ApiOperation(value = "POST Method", notes = "POST Method에 대한 예시", response= String.class)
    public String methodTypePost(
    		@ApiParam("Parameter에 대한 설명") @RequestParam(required = true) String value) {
        return "methodTypePost";
    }
}
