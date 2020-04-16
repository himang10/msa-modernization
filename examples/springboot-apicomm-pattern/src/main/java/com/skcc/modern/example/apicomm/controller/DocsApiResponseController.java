package com.skcc.modern.example.apicomm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class DocsApiResponseController {
	
	@RequestMapping(value = "/v1/viewcustomdocs3", method = RequestMethod.GET)
	@ApiOperation(value = "API 명칭", notes = "API에 대한 간략한 설명을 작성한다", response= String.class)
	@ApiResponse(code = 404, message = "Person not found")
    public String viewCustomDocs3(
    		@ApiParam("Parameter에 대한 설명") @RequestParam(required = false) String param1,
    		@RequestParam(required = false) String param2
    		) {
        return "viewcustomdocs3";
    }
	
	@RequestMapping(value = "/v1/viewcustomdocs4", method = RequestMethod.GET)
	@ApiOperation(value = "API 명칭", notes = "API에 대한 간략한 설명을 작성한다", response= String.class)
	@ApiResponses(value = {@ApiResponse(code = 404, message = "Docs not found")})
    public String viewCustomDocs4(
    		@ApiParam("Parameter에 대한 설명") @RequestParam(required = false) String param1,
    		@RequestParam(required = false) String param2
    		) {
        return "viewcustomdocs4";
    }
}
