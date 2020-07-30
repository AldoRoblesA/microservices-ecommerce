package com.paksoft.app.service.sale.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paksoft.app.commons.product.models.entity.Product;
import com.paksoft.app.service.sale.models.entity.Item;
import com.paksoft.app.service.sale.models.entity.Sale;
import com.paksoft.app.service.sale.services.SaleService;

@RestController
public class SaleController {
	
	@Autowired
	private SaleService service;
	
	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/pagina")	
	public ResponseEntity<?> listar(Pageable pageable) {
		//Integer page = pageable.getPageNumber() - 1;
		Pageable pageableEnv = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
		return ResponseEntity.ok().body(service.findAll(pageableEnv));
	}
	
	@PostMapping
	public ResponseEntity<?> crear(@Valid @RequestBody Sale sale, BindingResult result) {
		
		if (result.hasErrors()) {
			return this.validar(result);
		}
		
		List<Product> products = new ArrayList<>();

		sale.getItems().forEach(p -> {
			p.getProduct().setQuantity(p.getQty());
			products.add(p.getProduct());
		});
		
		service.actualizarStock(products);
		
		Sale saledb = service.save(sale);
		return ResponseEntity.status(HttpStatus.CREATED).body(saledb);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Sale sale, BindingResult result, @PathVariable Long id) {

		if (result.hasErrors()) {
			return this.validar(result);
		}

		Optional<Sale> o = service.findById(id);

		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Sale saleDb = o.get();
		saleDb.setState(sale.getState());
		saleDb.setVoucher(sale.getVoucher());

		saleDb.getItems().stream().filter(pdb -> !sale.getItems().contains(pdb)).forEach(saleDb::removeItem);

		saleDb.setItems(sale.getItems());

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(saleDb));
	}

	@PutMapping("/{id}/agregar-item")
	public ResponseEntity<?> agregarItem(@RequestBody List<Item> items, @PathVariable Long id) {

		Optional<Sale> o = service.findById(id);

		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Sale saledb = o.get();
		items.forEach(saledb::addItem);

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(saledb));
	}

	@PutMapping("/{id}/quitar-item")
	public ResponseEntity<?> quitarItem(@RequestBody Item item, @PathVariable Long id) {

		Optional<Sale> o = service.findById(id);

		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Sale saledb = o.get();

		saledb.removeItem(item);

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(saledb));
	}
	
	private ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), " El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}


}
