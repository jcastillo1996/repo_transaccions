package com.example.demo.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.webclient.dto.ProductDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebClientProduct {
	private final WebClient webclient;

	public WebClientProduct(WebClient.Builder webclientBuilder, @Value("${wproduct.urls.product}") String baseURl) {
		this.webclient = webclientBuilder.baseUrl(baseURl).build();
	}

	public Flux<ProductDTO> getAllProducts() {
		return webclient.get().uri("/").retrieve().bodyToFlux(ProductDTO.class);
	}

	public Mono<ProductDTO> getProductById(Long id) {
		return webclient.get().uri("/{id}", id).retrieve().onStatus(HttpStatus::is4xxClientError, response -> {
			return Mono
					.error(new ResponseStatusException(response.statusCode(), response.statusCode().getReasonPhrase()));
		}).onStatus(HttpStatus::is5xxServerError, response -> {
			return Mono
					.error(new ResponseStatusException(response.statusCode(), response.statusCode().getReasonPhrase()));
		}).bodyToMono(ProductDTO.class);

	}

	public Mono<ProductDTO> updateProduct(ProductDTO prod) {

		return webclient.put()
				.uri("")
				.body(Mono.just(prod),ProductDTO.class)
				.retrieve()
				.bodyToMono(ProductDTO.class);
	}
}
