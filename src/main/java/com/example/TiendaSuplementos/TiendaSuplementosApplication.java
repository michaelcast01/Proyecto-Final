package com.example.TiendaSuplementos;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TiendaSuplementosApplication {

	public static void main(String[] args) {
		loadEnv();
		SpringApplication.run(TiendaSuplementosApplication.class, args);
	}

	private static void loadEnv() {
		try {
			// Try to load .env file if it exists
			Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
			
			// Set system properties from .env file if they exist, otherwise use system env variables
			setSystemPropertyFromEnv(dotenv, "DB_URL");
			setSystemPropertyFromEnv(dotenv, "DB_USERNAME");
			setSystemPropertyFromEnv(dotenv, "DB_PASSWORD");
			setSystemPropertyFromEnv(dotenv, "STRIPE_SECRET_KEY");
			setSystemPropertyFromEnv(dotenv, "STRIPE_PUBLISHABLE_KEY");
		} catch (Exception e) {
			// If .env loading fails, environment variables should be available from system
			System.out.println("Warning: Could not load .env file, using system environment variables");
		}
	}
	
	private static void setSystemPropertyFromEnv(Dotenv dotenv, String key) {
		String value = dotenv.get(key);
		if (value != null && !value.isEmpty()) {
			System.setProperty(key, value);
		} else {
			// Fall back to system environment variable
			String systemValue = System.getenv(key);
			if (systemValue != null && !systemValue.isEmpty()) {
				System.setProperty(key, systemValue);
			}
		}
	}
}
