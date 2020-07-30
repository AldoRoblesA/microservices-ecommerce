package com.paksoft.app.service.customer.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paksoft.app.commons.customer.models.entity.Customer;
import com.paksoft.app.commons.customer.models.entity.Ubigeo;
import com.paksoft.app.commons.service.services.CommonServiceImpl;
import com.paksoft.app.service.customer.model.repository.CustomerRepository;
import com.paksoft.app.service.customer.model.repository.UbigeoRepository;

@Service
public class CustomerServiceImpl extends CommonServiceImpl<Customer, CustomerRepository> implements CustomerService {
	
	@Autowired
	private UbigeoRepository ubigeoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Customer> findByFirstNameAndLastName(String term) {
		return repository.findByFirstNameAndLastName(term);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Ubigeo> findUbigeoAll() {
		return ubigeoRepository.findAll();
	}


}
