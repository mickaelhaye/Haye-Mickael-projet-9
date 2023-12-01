package com.mbackpatient.microservicebackpatient.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI usersMicroserviceOpenAPI() {
		return new OpenAPI().info(new Info().title("MicroService Back Patient").description("").version("1.0"));
	}
}