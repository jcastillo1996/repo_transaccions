package com.example.demo.controller;

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

import com.example.demo.model.TransaccionType;
import com.example.demo.service.impl.TransaccionTypeServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api_transasccion_type")
public class TransaccionTypeController {
	
	@Autowired
	TransaccionTypeServiceImpl service;
	
	@GetMapping
	public Flux<TransaccionType> findAll() {

		return service.findAll();
	}

	@GetMapping("/{id}")
	public Mono<TransaccionType> findById(@PathVariable(name = "id") Long id) {
		return service.findById(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,"TRANSACCION NOT FOUND ID: "+id)));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<TransaccionType> save(@RequestBody  TransaccionType type) {
		
		return service.save(type);
	}

	@PutMapping
	public Mono<TransaccionType> update(@RequestBody(required = true) TransaccionType type) {
		
		return service.update(type);
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

