package com.paksoft.app.service.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.paksoft.app.commons.user.models.entity.User;

@FeignClient(name = "service-users")
public interface UserFeignClient {

	/*
	@GetMapping("/usuario/search/buscar-email")
	public User findByEmail(@RequestParam String email);
	*/
	
	@GetMapping("/usuario/search/buscar-username")
	public User findByUsername(@RequestParam String username);
	
	@PutMapping("/usuario/{id}")
	public User update(@RequestBody User user, @PathVariable Long id);
	
}
