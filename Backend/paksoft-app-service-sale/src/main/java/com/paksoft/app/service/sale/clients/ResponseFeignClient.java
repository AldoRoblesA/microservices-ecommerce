package com.paksoft.app.service.sale.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.paksoft.app.commons.product.models.entity.Product;

@FeignClient(name = "service-products")
public interface ResponseFeignClient {
	
	@PutMapping("/actualiza-stock")
	public void actualizarStock(@RequestBody List<Product> products);
	
}
