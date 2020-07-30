package com.paksoft.app.commons.customer.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ubigeos")
public class Ubigeo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String name;
	
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler","sons"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Ubigeo father;
	
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler","father"},allowGetters = true)
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "father",cascade = CascadeType.ALL)
	private List<Ubigeo> sons;
	
	
	public Ubigeo() {
		this.sons = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Ubigeo getFather() {
		return father;
	}

	public void setFather(Ubigeo father) {
		this.father = father;
	}

	public List<Ubigeo> getSons() {
		return sons;
	}

	public void setSons(List<Ubigeo> sons) {
		this.sons = sons;
	}
		
}
