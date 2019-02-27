package com.example.demo.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.example.demo.document.Flower;

import reactor.core.publisher.Flux;

public interface FlowerReactiveRepository extends ReactiveCrudRepository<Flower, Integer> {

	Flux<Flower> findByColor(String color);

	@Query(value="{'name': ?0}", fields="{'name': 1, 'color': 1}")
	Flux<Flower> findByName(String name);
}
