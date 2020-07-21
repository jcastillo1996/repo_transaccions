package com.example.demo.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICRUD<T,V>{

	Mono<T> save(T obj);

	Mono<T> update(T obj);

	Flux<T> findAll();

	Mono<T> findById(V v);

	Mono<Void> deleteById(V v);

	Mono<Void> deleteAll();
	
}