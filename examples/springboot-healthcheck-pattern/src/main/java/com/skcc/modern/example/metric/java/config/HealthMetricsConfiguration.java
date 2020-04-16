package com.skcc.modern.example.metric.java.config;

import java.util.List;

import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicatorRegistry;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class HealthMetricsConfiguration {
    private CompositeHealthIndicator healthIndicator;
    public HealthMetricsConfiguration(HealthAggregator healthAggregator, 
                                    HealthIndicatorRegistry healthIndicatorRegistry,
                                    MeterRegistry registry) {
        healthIndicator = new CompositeHealthIndicator(healthAggregator, healthIndicatorRegistry);
        registry.gauge("health", healthIndicator, health -> {
            Status status = health.health().getStatus();
            switch (status.getCode()) {
                case "UP":
                    return 3;
                case "OUT_OF_SERVICE":
                    return 2;
                case "DOWN":
                    return 1;
                case "UNKNOWN":
                default:
                    return 0;
            }
        });
    }
}