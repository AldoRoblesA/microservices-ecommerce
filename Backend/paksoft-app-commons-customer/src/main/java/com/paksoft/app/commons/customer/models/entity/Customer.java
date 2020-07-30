package com.paksoft.app.commons.customer.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name = "first_name",length = 100)
	private String firstName;

	@NotEmpty
	@Column(name = "last_name",length = 100)
	private String lastName;

	@NotEmpty
	@Column(length = 150)
	private String address;

	@Email
	@Column(length = 100,unique = true)
	private String email;

	@Column(length = 50)
	private String phone;
	
	@Lob
	@JsonIgnore
	private byte[] photo;
	
	/*
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Ubigeo departament;

	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Ubigeo province;
	
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Ubigeo district;
	*/
	
	@Temporal(TemporalType.DATE)
	@Column(name = "customer_date")
	private Date customerDate;

	@PrePersist
	public void prePersist() {
		customerDate = new Date();
	}
	
	private Integer getPhotoHashCode(){
		return (this.photo != null) ? this.photo.hashCode() : null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCustomerDate() {
		return customerDate;
	}

	public void setCustomerDate(Date customerDate) {
		this.customerDate = customerDate;
	}

	/*	 
	public Ubigeo getDepartament() {
		return departament;
	}

	public void setDepartament(Ubigeo departament) {
		this.departament = departament;
	}

	public Ubigeo getProvince() {
		return province;
	}

	public void setProvince(Ubigeo province) {
		this.province = province;
	}

	public Ubigeo getDistrict() {
		return district;
	}

	public void setDistrict(Ubigeo district) {
		this.district = district;
	}
	 */
	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}		
	
}
