package com.paksoft.app.service.product.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.paksoft.app.commons.product.models.entity.Categorie;
import com.paksoft.app.commons.product.models.entity.Product;
import com.paksoft.app.service.product.services.CategorieService;
import com.paksoft.app.service.product.services.ProductService;
import com.paksoft.app.service.product.services.UploadFileService;



@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategorieService categorieService;
	
	
	@Autowired
	private UploadFileService uploadService;
	
	@GetMapping
	public ResponseEntity<?> listarProductos() {
		return ResponseEntity.ok().body(productService.findProductAll());
	}
	
	@GetMapping("/pagina")
	public ResponseEntity<?> listarProductos(Pageable pageable) {
		Pageable pageableEnv = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
		return ResponseEntity.ok().body(productService.findProductAll(pageableEnv));
	}
	
	/*
	@GetMapping("/pagina/{page}/filtro/{term}")
	public ResponseEntity<?> listarProductos(@PathVariable String term, @PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page - 1, 10);
		return ResponseEntity.ok().body(productService.findProductByNameLike(term, pageable));
	}
	*/

	
	@GetMapping("/pagina/{page}")
	public ResponseEntity<?> listarProductos(@PathVariable Integer page,@RequestParam(required = false) String term) {
		Pageable pageable = PageRequest.of(page - 1, 10);
		if(term == null) {
			term = "%";
		}
		
		return ResponseEntity.ok().body(productService.findProductByNameLike(term, pageable));
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<?> verProducto(@PathVariable Long id) {
		Optional<Product> oProduct = productService.findProductById(id);
		if (oProduct.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oProduct.get());
	}

	@PostMapping
	public ResponseEntity<?> crearProducto(@Valid @RequestBody Product product, BindingResult result) {
		
		if (result.hasErrors()) {
			return this.validar(result);
		}
		
		Product productdb = productService.saveProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(productdb);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
		productService.deleteProductById(id);
		return ResponseEntity.noContent().build();
	}


	@PutMapping("/{id}")
	public ResponseEntity<?> editarProducto(@Valid @RequestBody Product product,BindingResult result , @PathVariable Long id) {
		
		Map<Object, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			
			return this.validar(result);
		}
		
		Product productAct = null;
		
	
		Optional<Product> oProduct = productService.findProductById(id);
		if (oProduct.isEmpty()) {
			response.put("message", "Producto de código "+ id + ", no encontrado!");
			//return ResponseEntity.notFound().build();
			return new ResponseEntity<Map<Object, Object>>(response, HttpStatus.NOT_FOUND);
		}

		Product productdb = oProduct.get();
		productdb.setBuyPrice(product.getBuyPrice());
		productdb.setCategorie(product.getCategorie());
		productdb.setName(product.getName());
		productdb.setQuantity(product.getQuantity());
		productdb.setSalePrice(product.getSalePrice());

		productAct = productService.saveProduct(productdb);
		response.put("content", productAct);
		response.put("error", null);
		response.put("message", "Producto Actualizado correctamente");
		
		return new ResponseEntity<Map<Object, Object>>(response, HttpStatus.CREATED);
		
		//return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productdb));

	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
		Optional<Product> oProduct = productService.findProductById(id);
		if (oProduct.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Product productdb = oProduct.get();
		if(!archivo.isEmpty()) {
			String nombreArchivo = null;
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				//response.put("mensaje", "Error al subir la imagen del cliente");
				//response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				//return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String nombreFotoAnterior = productdb.getCoverPage();
			uploadService.eliminar(nombreFotoAnterior);
			productdb.setCoverPage(nombreArchivo);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productdb));
	}
	
	@GetMapping("/uploads/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){

		Resource recurso = null;
		
		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	
	@PutMapping("/actualiza-stock")
	public ResponseEntity<?> actualizarStock(@RequestBody List<Product> products) {
		
		products.forEach(p -> {
			productService.updateStockProductById(p.getId(), p.getQuantity());
		});

		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/categorias")
	public ResponseEntity<?> listarCategorias() {
		
		Iterable<Categorie> categories = null;
		Map<Object, Object> response = new HashMap<>();
		categories = categorieService.findCategorieAll();
		response.put("content", categories);
		
		//return ResponseEntity.ok().body(response);
		return new ResponseEntity<Map<Object, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/categorias/{id}")
	public ResponseEntity<?> verCategoria(@PathVariable Integer id) {
		Optional<Categorie> oCategorie = categorieService.findCategorieById(id);
		if (oCategorie.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oCategorie.get());
	}

	@PostMapping("/categorias")
	public ResponseEntity<?> crearCategoria(@RequestBody Categorie categorie) {
		Categorie categoriedb = categorieService.saveCategorie(categorie);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriedb);
	}

	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<?> eliminarCategoria(@PathVariable Integer id) {
		categorieService.deleteCategorieById(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@PutMapping("/categorias/{id}")
	public ResponseEntity<?> editarCategoria(@RequestBody  Categorie categorie, @PathVariable Integer id) {
		Optional<Categorie> oCategorie = categorieService.findCategorieById(id);
		if (oCategorie.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Categorie categoriedb = oCategorie.get();
		categoriedb.setName(categorie.getName());

		return ResponseEntity.status(HttpStatus.CREATED).body(categorieService.saveCategorie(categoriedb));

	}
	
	private ResponseEntity<?> validar(BindingResult result){
		//Map<String, Object> response = new HashMap<>();
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), " El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		//response.put("messageval", errores);
		
		return ResponseEntity.badRequest().body(errores);
		
		//return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}

}
