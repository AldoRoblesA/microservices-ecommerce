package com.paksoft.app.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class PaksoftAppConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaksoftAppConfigServerApplication.class, args);
	}

}
