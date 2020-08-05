package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.demo.model.Transaccion;
import com.example.demo.model.TransaccionType;
import com.example.demo.service.impl.TransaccionServiceImpl;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TransaccionController.class)
class TransaccionControllerTest {

	@MockBean
	TransaccionServiceImpl service;

	@Autowired
	private WebTestClient webClient;

	@Test
	void findById() {
		Long id = 1L;
		Transaccion tran = new Transaccion();
		tran.setTransaccionId(id);
		Mono<Transaccion> transacMono = Mono.just(tran);
		when(service.findById(id)).thenReturn(transacMono);
		webClient.get().uri("/api_transaccion/{id}", id).accept(MediaType.APPLICATION_JSON).exchange().expectStatus()
				.isOk().expectBody(Transaccion.class).value(tr1 -> tran.getTransaccionId(), equalTo(id));
	}

	@Test
	void save() {
		Long id = 9L;
		Transaccion tran = new Transaccion();
		tran.setTransaccionId(id);
		tran.setTransaccionDate(LocalDateTime.now());
		tran.setProductId(1L);
		tran.setTransaccionType(new TransaccionType(1L,"PAGO"));// PAGO , RETIRO
		tran.setTransaccionAmount(250.00);
		Mono<Transaccion> crdMono = Mono.just(tran);
		when(service.save(tran)).thenReturn(crdMono);
		webClient.post().uri("/api_transaccion").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(tran), Transaccion.class).exchange().expectStatus()
				.isCreated();
	}

	@Test
	void update() {
		Long id = 2L;
		Transaccion tran = new Transaccion();
		tran.setTransaccionId(id);
		tran.setTransaccionDate(LocalDateTime.now());
		tran.setProductId(1L);
		tran.setTransaccionType(new TransaccionType(2L,"RETIRO"));// PAGO , RETIRO
		tran.setTransaccionAmount(550.95);
		Mono<Transaccion> crdMono = Mono.just(tran);
		when(service.update(tran)).thenReturn(crdMono);
		webClient.put().uri("/api_transaccion").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(tran), Transaccion.class).exchange().expectStatus()
				.isOk();
	}

	@Test
	void deleteById() {
		Long id = 2L;
		when(service.deleteById(id)).thenReturn(Mono.empty());
		webClient.delete().uri("/api_transaccion/{id}", id).exchange().expectStatus().isOk();
	}
}
