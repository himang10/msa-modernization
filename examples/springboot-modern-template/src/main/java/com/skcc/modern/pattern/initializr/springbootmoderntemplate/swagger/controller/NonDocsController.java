package com.skcc.modern.pattern.initializr.springbootmoderntemplate.swagger.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NonDocsController {
	
	@RequestMapping(value = "/nondocs", method = RequestMethod.GET)
    public String nondocs() {
        return "nondocs";
    }
}
