package com.example.demo.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.model.Transaccion;

public interface ITransaccionRepo extends ReactiveMongoRepository<Transaccion, Long> {

}
