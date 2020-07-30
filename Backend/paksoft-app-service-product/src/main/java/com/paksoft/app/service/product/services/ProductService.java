package com.paksoft.app.service.product.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paksoft.app.commons.product.models.entity.Product;

public interface ProductService {
	
	public Iterable<Product> findProductAll();
	
	public Page<Product> findProductByNameLike(String term, Pageable pageable);
	
	public Page<Product> findProductAll(Pageable pageable); 
		
	public Optional<Product> findProductById(Long id);

	public Product saveProduct(Product product);

	public void deleteProductById(Long id);
	
	public void updateStockProductById(Long id,Integer quantity);
}
