package com.example.demo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

public interface FlowerReactiveRepository extends ReactiveCrudRepository<Flower, String> {

	Flux<Flower> findByColor(String color);
	
//	Flux<Flower> findAll(Pageable pageable);
}
