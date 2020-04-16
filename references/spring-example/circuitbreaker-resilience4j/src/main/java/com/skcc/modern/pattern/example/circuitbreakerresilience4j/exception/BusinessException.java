package com.skcc.modern.pattern.example.circuitbreakerresilience4j.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}