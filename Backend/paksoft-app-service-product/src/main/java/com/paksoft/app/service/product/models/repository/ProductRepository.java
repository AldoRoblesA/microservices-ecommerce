package com.paksoft.app.service.product.models.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.paksoft.app.commons.product.models.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	
	 @Modifying(clearAutomatically = true)
	 @Query("UPDATE Product c SET c.quantity = c.quantity - :quantity WHERE c.id = :id")
	 public void updateStockProductById(@Param("id") Long id, @Param("quantity") Integer quantity);
	 
	 @Query("select u from Product u where lower(u.name) like lower(concat('%',:term,'%'))")
	 public Page<Product> findAllByNameLike(@Param("term") String term,Pageable pageable);
	 
	 //public Page<Product> findAllByNameLike(String term, Pageable pageable);
	 
	 
}
