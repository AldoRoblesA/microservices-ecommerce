package com.paksoft.app.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class PaksoftAppEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaksoftAppEurekaServerApplication.class, args);
	}

}
