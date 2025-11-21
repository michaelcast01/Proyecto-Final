package com.example.TiendaSuplementos.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI tiendaSuplementosOpenAPI() {
        Server httpsServer = new Server();
        httpsServer.setUrl("https://suplements-v1.fly.dev");
        httpsServer.setDescription("Servidor de Producción (HTTPS)");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Servidor Local de Desarrollo");

        return new OpenAPI()
                .servers(List.of(httpsServer, localServer))
                .info(new Info()
                        .title("API Tienda de Suplementos")
                        .description("Documentación de la API de la tienda de suplementos")
                        .version("1.0.0"));
    }
}
