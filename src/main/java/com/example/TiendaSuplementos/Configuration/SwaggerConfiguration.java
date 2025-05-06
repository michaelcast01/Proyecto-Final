package com.example.TiendaSuplementos.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI tiendaSuplementosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Tienda de Suplementos")
                        .description("Documentación  de la tienda")
                        .version("1.0.0"));
    }
}
