package com.skcc.cloud.modern.pattern.metric.java.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.cloud.modern.pattern.metric.java.service.MetricExporterService;

@RestController
@RequestMapping("/metrics")
public class MetricExporterController {

	@Autowired
	MetricExporterService service;
	
	@RequestMapping(method = RequestMethod.GET, produces="text/plain")
	public String sample() {
		return service.sampleCollector();
	}
}
