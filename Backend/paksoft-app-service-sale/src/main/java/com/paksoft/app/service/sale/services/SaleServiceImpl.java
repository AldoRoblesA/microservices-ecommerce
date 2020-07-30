package com.paksoft.app.service.sale.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paksoft.app.commons.product.models.entity.Product;
import com.paksoft.app.service.sale.clients.ResponseFeignClient;
import com.paksoft.app.service.sale.models.entity.Sale;
import com.paksoft.app.service.sale.models.repository.SaleRepository;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private ResponseFeignClient client;
	
	@Autowired
	private SaleRepository service;

	@Override
	public void actualizarStock(List<Product> products) {
		client.actualizarStock(products);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Sale> findAll() {
		return service.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Sale> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Sale> findById(Long id) {
		return service.findById(id);
	}

	@Override
	@Transactional
	public Sale save(Sale sale) {
		return service.save(sale);
	}


	@Override
	@Transactional
	public void deleteById(Long id) {
		service.deleteById(id);
	}	
}
