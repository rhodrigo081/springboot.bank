package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Transactions Control API")
                .version("1.0.0")
                .description("This API provides a comprehensive set of functionalities essential financial " +
                        "operations typical of a digital bank, specifically managing deposits, withdrawals, " +
                        "and instant transfers (similar to Brazil's Pix System)."));
    }

}
