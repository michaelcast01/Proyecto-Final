package com.example.TiendaSuplementos.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

    /**
     * Configuración del pool de threads para tareas asíncronas
     */
    @Bean(name = "emailVerificationTaskExecutor")
    public Executor emailVerificationTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("EmailVerification-");
        executor.initialize();
        return executor;
    }

    /**
     * Bean para realizar llamadas HTTP (callbacks)
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}