package com.paksoft.app.service.sale.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.paksoft.app.commons.customer.models.entity.Customer;


@Entity
@Table(name = "sales")
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column(unique = true,length = 20,nullable = false)
	private String voucher;
	
	private Boolean state;
	
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@JsonIgnoreProperties(value = {"sale", "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "sale",orphanRemoval = true)
	private List<Item> items;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sale_date")
	private Date saleDate;
	
	@PrePersist
	public void prePersist() {
		this.saleDate = new Date();
	}

	public Sale() {
		this.items = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items.clear();
		items.forEach(this::addItem);
	}
	
	public void addItem(Item item) {
		this.items.add(item);
		item.setSale(this);
	}
	
	public void removeItem(Item item) {
		this.items.remove(item);
		item.setSale(null);
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	
	public Double getTotal() {
		Double total = 0.00;
		for (Item item : items) {
			total += item.getSubTotal();
		}
		return total;
	}
}
