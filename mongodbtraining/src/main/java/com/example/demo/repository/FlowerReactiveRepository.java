package com.example.demo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.example.demo.document.Flower;

import reactor.core.publisher.Flux;

public interface FlowerReactiveRepository extends ReactiveCrudRepository<Flower, String> {

	Flux<Flower> findByColor(String color);
}
