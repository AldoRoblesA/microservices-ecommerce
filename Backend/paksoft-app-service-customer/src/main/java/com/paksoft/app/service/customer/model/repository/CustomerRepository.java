package com.paksoft.app.service.customer.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.paksoft.app.commons.customer.models.entity.Customer;



public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
	@Query("select c from Customer c where c.firstName like %?1% or c.lastName like %?1%")
	public List<Customer> findByFirstNameAndLastName(String term);
}
