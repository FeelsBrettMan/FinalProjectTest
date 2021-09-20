package com.example.finalprojecttest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// http://localhost:8080/swagger-ui.html
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiDetails());
				
	}
	
	public ApiInfo apiDetails() {
		
		return new ApiInfo("Restaurant API", // title of the documentation
							"Open source API for obtaining/updating restaurants pages information.", // description
							"1.0", // version
							"Free to use", // terms of use
							new Contact("Java Annihilator", "http://github.com/XuanTLe", "xuanthanhle079@gmail.com"), // contact info
							"API Liscense", // license
							"http://github.com/XuanTLe", // url to the license
							Collections.emptyList()); // list of vendors
	}
}
