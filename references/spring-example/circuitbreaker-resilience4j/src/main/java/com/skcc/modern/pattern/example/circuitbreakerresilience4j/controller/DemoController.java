package com.skcc.modern.pattern.example.circuitbreakerresilience4j.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;

@RestController
public class DemoController {
    
    Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    private static final String URI_GET = "/get";
    private static final String URI_DELAY = "/delay/{delaySeconds}";
    private static final String URI_DELAY2 = "/delay2/{delaySeconds}";

    private CircuitBreakerFactory circuitBreakerFactory;
    private HttpBinService httpBinService;

    public DemoController(CircuitBreakerFactory circuitBreakerFactory, HttpBinService httpBinService) {
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.httpBinService = httpBinService;
    }

    @GetMapping(URI_GET)
    public Map get() {
        return httpBinService.get();
    }

    @GetMapping(URI_DELAY)
    public Map delay(@PathVariable int delaySeconds) {
        return circuitBreakerFactory.create("delayTest").run(httpBinService.delaySupplier(delaySeconds), throwable -> {
            LOGGER.error("service delay call failed ", throwable);
            Map<String, String> fallback = new HashMap<>();
            fallback.put("circuitbreaker is fallback","delay message");
            return fallback;
        });
    }
}