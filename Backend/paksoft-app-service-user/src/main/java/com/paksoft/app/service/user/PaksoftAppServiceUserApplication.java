package com.paksoft.app.service.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.paksoft.app.commons.user.models.entity"})
public class PaksoftAppServiceUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaksoftAppServiceUserApplication.class, args);
	}

}
