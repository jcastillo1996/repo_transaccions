package com.example.demo.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.webclient.dto.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebClientProduct {

	@Autowired
	private WebClient webclient;

	public Flux<Product> getAllProducts() {
		return webclient.get().uri("/").retrieve().bodyToFlux(Product.class);
	}

	public Mono<Product> getProductById(Long id) {
		return webclient.get().uri("/{id}", id).retrieve().onStatus(HttpStatus::is4xxClientError, response -> {
			return Mono
					.error(new ResponseStatusException(response.statusCode(), response.statusCode().getReasonPhrase()));
		}).onStatus(HttpStatus::is5xxServerError, response -> {
			return Mono
					.error(new ResponseStatusException(response.statusCode(), response.statusCode().getReasonPhrase()));
		}).bodyToMono(Product.class);

	}

	public Mono<Product> updateProduct(Product prod) {
		System.out.println(prod.toString());
		return webclient.put().uri("/").contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(prod), Product.class).retrieve()
				.bodyToMono(Product.class);
	}
}
