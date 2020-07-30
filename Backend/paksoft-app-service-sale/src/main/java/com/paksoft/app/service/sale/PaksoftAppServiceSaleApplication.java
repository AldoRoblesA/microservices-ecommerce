package com.paksoft.app.service.sale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.paksoft.app.commons.product.models.entity",
			 "com.paksoft.app.commons.customer.models.entity",
	         "com.paksoft.app.service.sale.models.entity"})		
public class PaksoftAppServiceSaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaksoftAppServiceSaleApplication.class, args);
	}

}
