package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Transaccion;
import com.example.demo.service.impl.TransaccionServiceImpl;
import com.example.demo.webclient.dto.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api_transaccion")
public class TransaccionController {

	@Autowired
	TransaccionServiceImpl service;

	@GetMapping
	public Flux<Transaccion> findAll() {
		return service.findAll();
	}

	@GetMapping("/test")
	public Flux<Product> test(@RequestBody Product prod) {

		service.updateProduct(prod).flatMap(pr -> {
			return Mono.just(pr);
		}).subscribe();
		;

		return service.findAllProducts();
	}

	@GetMapping("/{id}")
	public Mono<Transaccion> findById(@PathVariable(name = "id") Long id) {
		return service.findById(id).switchIfEmpty(
				Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "TRANSACCION NOT FOUND ID: " + id)));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<?> save(@RequestBody Transaccion transaccion) {
		transaccion.setTransaccionDate(LocalDateTime.now());
		return service.findByIdProduct(transaccion.getProductId()).flatMap(prod -> {
			String prodType = prod.getProductType().getTypeName();
			if (prodType.equals("CUENTA")) {
				String typeTransac = transaccion.getTransaccionType().getNameType();
				if (typeTransac.equals("DEPOSITO")) {
					// Aumenta
					prod.setMount(prod.getMount()+transaccion.getTransaccionAmount());
				} else if (typeTransac.equals("RETIRO")) {
					// Disminuye
					prod.setMount(prod.getMount()-transaccion.getTransaccionAmount());
				}
			} else if (prodType.equals("CREDITO")) {

			}
			return service.updateProduct(prod);
		});

	}

	@PutMapping
	public Mono<Transaccion> update(@RequestBody(required = true) Transaccion transaccion) {

		return service.update(transaccion);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteById(@PathVariable Long id) {

		return service.deleteById(id);
	}

	@DeleteMapping("/deleteAll")
	public Mono<Void> deleteAll() {

		return service.deleteAll();
	}

}
