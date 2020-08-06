package com.example.demo.service.impl;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.model.Transaccion;
import com.example.demo.repo.TransaccionRepo;
import com.example.demo.service.TransaccionService;
import com.example.demo.webclient.WebClientProduct;
import com.example.demo.webclient.dto.AccountTypeDTO;
import com.example.demo.webclient.dto.ProductDTO;
import com.example.demo.webclient.dto.ProductTypeDTO;

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
	public Flux<ProductDTO> findAllProducts() {
		return webclient.getAllProducts();
	}

	public Mono<ProductDTO> findByIdProduct(Long id) {
		return webclient.getProductById(id);
	}
	public Mono<ProductDTO> updateProduct(ProductDTO prod) {
		List<Long> lds=new ArrayList<>();
		lds.add(2L);
		prod.setIdProduct(3L);
		prod.setProductType(new ProductTypeDTO(1L,"CUENTA"));
		prod.setProductNumber("0009994384383");
		prod.setMoney("S");
		prod.setAccountType(new AccountTypeDTO(3L,"PLAZO FIJO"));
		prod.setCreditType(null);
		prod.setCreationDate(LocalDate.of(2020, Month.JULY, 4));
		prod.setMount(1500.98);
		prod.setIdClient(lds);
		prod.setIdSignatory(null);	
		return webclient.updateProduct(prod);
	}
}
