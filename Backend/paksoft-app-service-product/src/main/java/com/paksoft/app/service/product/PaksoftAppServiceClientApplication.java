package com.paksoft.app.service.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.paksoft.app.commons.product.models.entity"})
public class PaksoftAppServiceClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaksoftAppServiceClientApplication.class, args);
	}

}
