package com.paksoft.app.service.product.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paksoft.app.commons.product.models.entity.Categorie;
import com.paksoft.app.commons.product.models.entity.Product;
import com.paksoft.app.service.product.models.repository.CategorieRepository;
import com.paksoft.app.service.product.models.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService, CategorieService {
	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategorieRepository categorieRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Categorie> findCategorieAll() {
		
		return categorieRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Categorie> findCategorieById(Integer id) {
		
		return categorieRepository.findById(id);
	}

	@Override
	@Transactional
	public Categorie saveCategorie(Categorie categorie) {
		
		return categorieRepository.save(categorie);
	}

	@Override
	@Transactional
	public void deleteCategorieById(Integer id) {
		
		categorieRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Product> findProductAll() {
		
		return productRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Product> findProductById(Long id) {
		
		return productRepository.findById(id);
	}

	@Override
	@Transactional
	public Product saveProduct(Product product) {
		
		return productRepository.save(product);
	}

	@Override
	@Transactional
	public void deleteProductById(Long id) {
		
		productRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Product> findProductByNameLike(String term,Pageable pageable) {
		return productRepository.findAllByNameLike(term,pageable);
	}

	@Override
	@Transactional
	public void updateStockProductById(Long id, Integer quantity) {
		productRepository.updateStockProductById(id, quantity);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Product> findProductAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

}
