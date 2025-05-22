package com.checkin.swagger_config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("CheckIn Service API")
                        .description("API documentation for the CheckIn Microservice")
                        .version("1.0.0")
                        .contact(new Contact().name("Udit Patel").email("your-email@example.com")));
    }
}
