package com.skcc.modern.pattern.initializr.springbootmoderntemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

import com.skcc.modern.pattern.initializr.springbootmoderntemplate.circuitbreaker.exception.ResourceNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	/**
	 * Spring Cloud Circuitbreaker 기반의 Resilience4J 설정 구성
	 * Resilience4J을 이용한 CircuitBreakerd을 구성하기 위하여 Resilience4JConfigBuilder을 통한 설정 구성
	 * 
	 * TimeLimiterConfig 설정 기능으로 Timeout 3s 로 설정
	 * CircuitBreakerConfig는 ofDefaults을 이용하여 기본값으로 설정
	 * 
	 * Reference : https://resilience4j.readme.io/docs/circuitbreaker
	 */
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
		.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3)).build())
		.circuitBreakerConfig(
			CircuitBreakerConfig.custom()
			.waitDurationInOpenState(Duration.ofSeconds(CircuitBreakerConfig.DEFAULT_SLOW_CALL_DURATION_THRESHOLD))
			.permittedNumberOfCallsInHalfOpenState(CircuitBreakerConfig.DEFAULT_PERMITTED_CALLS_IN_HALF_OPEN_STATE)
			.slidingWindowSize(CircuitBreakerConfig.DEFAULT_SLIDING_WINDOW_SIZE)
            .slidingWindowType(CircuitBreakerConfig.DEFAULT_SLIDING_WINDOW_TYPE)
            .minimumNumberOfCalls(CircuitBreakerConfig.DEFAULT_MINIMUM_NUMBER_OF_CALLS)
			.failureRateThreshold(CircuitBreakerConfig.DEFAULT_FAILURE_RATE_THRESHOLD)
            .automaticTransitionFromOpenToHalfOpenEnabled(false)
            .slowCallRateThreshold(CircuitBreakerConfig.DEFAULT_SLOW_CALL_RATE_THRESHOLD)
            .slowCallDurationThreshold(Duration.ofSeconds(CircuitBreakerConfig.DEFAULT_SLOW_CALL_DURATION_THRESHOLD))
			.writableStackTraceEnabled(CircuitBreakerConfig.DEFAULT_WRITABLE_STACK_TRACE_ENABLED)
			// .ignoreExceptions(IOException.class, TimeoutException.class)
            // .recordExceptions(ResourceNotFoundException.class)
			.build()
		)
		.build());
	}
}
