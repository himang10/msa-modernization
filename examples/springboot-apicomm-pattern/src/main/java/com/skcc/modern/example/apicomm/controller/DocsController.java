package com.skcc.modern.example.apicomm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class DocsController {

	@RequestMapping(value = "/v1/viewdocs", method = RequestMethod.GET)
    public String viewDocs() {
        return "docs";
    }
	

	@RequestMapping(value = "/v1/viewcustomdocs", method = RequestMethod.GET)
	@ApiOperation(value = "API 명칭", notes = "API에 대한 간략한 설명을 작성한다", response= String.class)
    public String viewCustomDocs() {
        return "viewcustomdocs";
    }
	
	@RequestMapping(value = "/v1/viewcustomdocs2", method = RequestMethod.GET)
	@ApiOperation(value = "API 명칭", notes = "API에 대한 간략한 설명을 작성한다", response= String.class)
    public String viewCustomDocs2(
    		@ApiParam("Parameter에 대한 설명") @RequestParam(required = false) String param1,
    		@RequestParam(required = false) String param2
    		) {
        return "viewcustomdocs2";
    }
}
