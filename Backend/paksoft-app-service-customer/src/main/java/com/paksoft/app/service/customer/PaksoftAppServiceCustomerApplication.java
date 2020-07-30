package com.paksoft.app.service.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.paksoft.app.commons.customer.models.entity"})
public class PaksoftAppServiceCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaksoftAppServiceCustomerApplication.class, args);
	}

}
