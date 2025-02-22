package com.tugrulhan.elasticsearch_medium1.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public OpenAPI openAPI() {
        return  new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title("Elasticsearch Medium1 API").version("1.0.0"));
    }
}
