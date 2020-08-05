package com.example.demo.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.model.TransaccionType;

public interface TransaccionTypeRepo extends ReactiveMongoRepository<TransaccionType, Long> {

}
