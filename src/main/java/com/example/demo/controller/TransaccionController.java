package com.example.demo.controller;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Transaccion;
import com.example.demo.service.impl.TransaccionServiceImpl;

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

	@GetMapping("/{id}")
	public Mono<ResponseEntity<Transaccion>> findById(@PathVariable(name = "id") Long id) {
		return service.findById(id).map(transaccion -> new ResponseEntity<>(transaccion, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Transaccion> save(@RequestBody  Transaccion transaccion) {
		
		return service.save(transaccion);
	}

	@PutMapping
	public Mono<Transaccion> update(@RequestBody(required = true) Transaccion transaccion) {
		transaccion.transaccionDate=LocalDateTime.now();
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