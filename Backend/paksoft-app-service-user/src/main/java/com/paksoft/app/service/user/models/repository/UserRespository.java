package com.paksoft.app.service.user.models.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.paksoft.app.commons.user.models.entity.User;

@RepositoryRestResource(path = "usuario")
public interface UserRespository extends PagingAndSortingRepository<User, Long> {
	
	@RestResource(path="buscar-email")
	public User findByEmail(@Param("email") String email);
	
	@RestResource(path="buscar-username")
	public User findByUsername(@Param("username") String username);
}