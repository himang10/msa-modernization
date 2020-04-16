package com.skcc.modern.pattern.example.circuitbreakerresilience4j.controller;

import java.util.Map;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpBinService {
    private RestTemplate restTemplate;

    private static final String HTTPBIN_URL = "https://httpbin.org";
    private static final String HTTPBIN_URL_GET = HTTPBIN_URL + "/get";
    private static final String HTTPBIN_URL_DELAY = HTTPBIN_URL + "/delay/";

    public HttpBinService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map get() {
        return restTemplate.getForObject(HTTPBIN_URL_GET, Map.class);
    }

    public Map delay(int delaySeconds) {
        return restTemplate.getForObject(HTTPBIN_URL_DELAY + delaySeconds, Map.class);
    }

    public Supplier<Map> delaySupplier(int delaySeconds) {
        return () -> this.delay(delaySeconds);
    }
}