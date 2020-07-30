package com.paksoft.app.service.customer.controllers;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.paksoft.app.commons.customer.models.entity.Customer;
import com.paksoft.app.commons.service.controllers.CommonController;
import com.paksoft.app.service.customer.services.CustomerService;


@RestController
public class CustomerController extends CommonController<Customer, CustomerService> {
	
	
	@PostMapping("/crear-foto")
	public ResponseEntity<?> crearConFoto(@Valid Customer customer, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {
		if (!archivo.isEmpty()) {
			customer.setPhoto(archivo.getBytes());
		}
		return super.crear(customer, result);
	}

	@PutMapping("/editar-foto/{id}")
	public ResponseEntity<?> editarConFoto(@Valid Customer customer, BindingResult result, @PathVariable Long id,@RequestParam MultipartFile archivo) throws IOException {
		
		if (result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Customer> oCustomer = service.findById(id);
		if (oCustomer.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Customer customerdb = oCustomer.get();
		customerdb.setAddress(customer.getAddress());
		customerdb.setEmail(customer.getEmail());
		customerdb.setFirstName(customer.getFirstName());
		customerdb.setLastName(customer.getLastName());
		customerdb.setPhone(customer.getPhone());
		/*
		customerdb.setDepartament(customer.getDepartament());
		customerdb.setProvince(customer.getProvince());
		customerdb.setDistrict(customer.getDistrict());
		*/
		
		if (!archivo.isEmpty()) {
			customerdb.setPhoto(archivo.getBytes());
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(customerdb));

	}
	
	
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable Long id){
		Optional<Customer> oCustomer = service.findById(id);
		if(oCustomer.isEmpty() || oCustomer.get().getPhoto() == null) {
			return ResponseEntity.notFound().build();
		}
		
		Resource imagen = new ByteArrayResource(oCustomer.get().getPhoto());
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(imagen);
		
	}
		
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Customer customer, BindingResult result, @PathVariable Long id) {
		
		if (result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Customer> oCustomer = service.findById(id);
		if (oCustomer.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Customer customerdb = oCustomer.get();
		customerdb.setAddress(customer.getAddress());
		customerdb.setEmail(customer.getEmail());
		customerdb.setFirstName(customer.getFirstName());
		customerdb.setLastName(customer.getLastName());
		customerdb.setPhone(customer.getPhone());
		/*
		customerdb.setDepartament(customer.getDepartament());
		customerdb.setProvince(customer.getProvince());
		customerdb.setDistrict(customer.getDistrict());
		*/

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(customerdb));

	}

	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){
		return ResponseEntity.ok(service.findByFirstNameAndLastName(term));
	}
	
	
	@GetMapping("/ubigeo")
	public ResponseEntity<?> listarUbigeo() {
		return ResponseEntity.ok().body(service.findUbigeoAll());
	}
	
}
