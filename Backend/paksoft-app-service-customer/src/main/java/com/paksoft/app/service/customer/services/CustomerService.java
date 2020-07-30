package com.paksoft.app.service.customer.services;

import java.util.List;

import com.paksoft.app.commons.customer.models.entity.Customer;
import com.paksoft.app.commons.customer.models.entity.Ubigeo;
import com.paksoft.app.commons.service.services.CommonService;


public interface CustomerService extends CommonService<Customer> {
	public List<Customer> findByFirstNameAndLastName(String term);
	public Iterable<Ubigeo> findUbigeoAll();
}
