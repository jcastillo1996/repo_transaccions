package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Transaccion;
import com.example.demo.repo.TransaccionRepo;
import com.example.demo.service.TransaccionService;
import com.example.demo.webclient.WebClientProduct;
import com.example.demo.webclient.dto.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransaccionServiceImpl implements TransaccionService {

	@Autowired
	TransaccionRepo repo;

	@Autowired
	WebClientProduct webclient;

	@Override
	public Mono<Transaccion> save(Transaccion obj) {

		return repo.insert(obj);
	}

	@Override
	public Mono<Transaccion> update(Transaccion obj) {

		return repo.save(obj);
	}

	@Override
	public Flux<Transaccion> findAll() {

		return repo.findAll();
	}

	@Override
	public Mono<Transaccion> findById(Long v) {

		return repo.findById(v);
	}

	@Override
	public Mono<Void> deleteById(Long v) {

		return repo.deleteById(v);
	}

	@Override
	public Mono<Void> deleteAll() {

		return repo.deleteAll();
	}

	/*
	 * WEb Client
	 */
	public Flux<Product> findAllProducts() {
		return webclient.getAllProducts();
	}

	public Mono<Product> findByIdProduct(Long id) {
		return webclient.getProductById(id);
	}
	public Mono<Product> updateProduct(Product prod) {

		return webclient.updateProduct(prod);
	}
}
