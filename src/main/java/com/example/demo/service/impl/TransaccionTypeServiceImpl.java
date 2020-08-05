package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TransaccionType;
import com.example.demo.repo.TransaccionTypeRepo;
import com.example.demo.service.TransaccionTypeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class TransaccionTypeServiceImpl implements TransaccionTypeService{

	@Autowired
	TransaccionTypeRepo repo;
	
	@Override
	public Mono<TransaccionType> save(TransaccionType obj) {
		
		return repo.insert(obj);
	}

	@Override
	public Mono<TransaccionType> update(TransaccionType obj) {
		
		return repo.save(obj);
	}

	@Override
	public Flux<TransaccionType> findAll() {
		
		return repo.findAll();
	}

	@Override
	public Mono<TransaccionType> findById(Long v) {
		
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

}
