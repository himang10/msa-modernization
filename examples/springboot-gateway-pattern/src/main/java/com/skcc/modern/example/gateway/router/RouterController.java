package com.skcc.modern.example.gateway.router;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.modern.example.gateway.properties.UriConfiguration;

import reactor.core.publisher.Mono;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class RouterController {

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
		String httpUri = uriConfiguration.getHttpbin();
		return builder.routes()
				// header 추가
				.route(p -> p.path("/get")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri(httpUri))
				// 
				.route(p -> p.host("*.hystrix.com")
						.filters(f -> f.hystrix(config -> config.setName("mycmd").setFallbackUri("forward:/fallback")))
						.uri(httpUri))
				.route("test1", r -> {
	                  return r.host("*.somehost.org").and().path("/somepath")
	                        .filters(f -> f.addRequestHeader("header1", "header-value-1"))
	                        .uri("http://someuri")
	                        .metadata(RESPONSE_TIMEOUT_ATTR, 200)
	                        .metadata(CONNECT_TIMEOUT_ATTR, 200);
	               })
				.build();
	}
	
	/*
	 * simple fallback
	 */
	@RequestMapping("/fallback")
	public Mono<String> fallback() {
		return Mono.just("fallback");
	}
}
