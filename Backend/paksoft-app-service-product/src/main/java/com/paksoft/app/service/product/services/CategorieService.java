package com.paksoft.app.service.product.services;

import java.util.Optional;

import com.paksoft.app.commons.product.models.entity.Categorie;

public interface CategorieService {
	
	public Iterable<Categorie> findCategorieAll();

	public Optional<Categorie> findCategorieById(Integer id);

	public Categorie saveCategorie(Categorie categorie);

	public void deleteCategorieById(Integer id);

}
