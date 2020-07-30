package com.paksoft.app.service.sale.models.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.paksoft.app.service.sale.models.entity.Sale;

public interface SaleRepository extends PagingAndSortingRepository<Sale, Long> {
		
}
