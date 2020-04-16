package com.skcc.modern.pattern.initializr.springbootmoderntemplate.swagger.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	/*
	 * FIXME : swagger 명세 내역에 사용할 기본 정보 자동주입 정책
	 */
	private static final String TITLE = "SwaggerAPICommPattern";
	private static final String DESCRIPTION = "SwaggerAPICommPattern";
	private static final String VERSION = "SwaggerAPICommPattern v1";
	private static final String TERMS_OF_SERVICE_URL = "https://serviceurl";
	private static final Contact CONTACT = new Contact("name", "url", "email");
	private static final String LICENSE = "LIcense of API";
	private static final String LICENSEURL = "https://swagger.io/docs/";
	
	/*
	 * Swagger MIME types
	 */
	private static final Set<String> SWAGGER_CONSUMES_MIME_TYPES = new HashSet<>(Arrays.asList("application/json"));
	private static final Set<String> SWAGGER_PRODUCES_MIME_TYPES = new HashSet<>(Arrays.asList("application/json","application/xml"));

//	@Bean
//	public Docket defaultDocket() {
//		return new Docket(DocumentationType.SWAGGER_2).select()
//				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any())
//				.build().apiInfo(apiInfo());
//	}
	
	@Bean
	public Docket defaultDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.skcc.modern.pattern.initializr"))
				.paths(PathSelectors.regex("/v1/.*"))
				.build().apiInfo(apiInfo())
				.consumes(SWAGGER_CONSUMES_MIME_TYPES).produces(SWAGGER_PRODUCES_MIME_TYPES);
	}

	private ApiInfo apiInfo() {
		@SuppressWarnings("rawtypes")
		ApiInfo apiInfo = new ApiInfo(TITLE, DESCRIPTION, VERSION, TERMS_OF_SERVICE_URL, CONTACT, LICENSE, LICENSEURL,
				new ArrayList<VendorExtension>());
		return apiInfo;
	}
}
