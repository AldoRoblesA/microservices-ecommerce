package com.paksoft.app.service.sale.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paksoft.app.commons.product.models.entity.Product;
import com.paksoft.app.service.sale.models.entity.Sale;

public interface SaleService {
	
	public Iterable<Sale> findAll();
	
	public Page<Sale> findAll(Pageable pageable); 
		
	public Optional<Sale> findById(Long id);

	public Sale save(Sale sale);

	public void deleteById(Long id);
	
	public void actualizarStock(List<Product> products);	
}
