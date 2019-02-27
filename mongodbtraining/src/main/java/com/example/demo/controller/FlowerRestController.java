package com.example.demo.controller;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.document.Flower;
import com.example.demo.document.Frog;
import com.example.demo.repository.FlowerPagingRepository;
import com.example.demo.repository.FlowerReactiveRepository;
import com.mongodb.reactivestreams.client.MongoClients;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class FlowerRestController {
	
	private FlowerPagingRepository flowerPagingRepository;
	private FlowerReactiveRepository flowerReactiveRepository;
	
	FlowerRestController(
			FlowerPagingRepository flowerPagingRepository
			,FlowerReactiveRepository flowerReactiveRepository) {
		this.flowerPagingRepository = flowerPagingRepository;
		this.flowerReactiveRepository = flowerReactiveRepository;
	}
	

	@GetMapping("insert")
	Mono<Flower> insert(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("color") String color ){
//		ReactiveMongoTemplate mongoOps = new ReactiveMongoTemplate(MongoClients.create(), "database");
		
//		return mongoOps.insert(new Flower(id, name, color))
//				.flatMap(p -> mongoOps.findOne(new Query(Criteria.where("id").is(p.getId())), Flower.class));
		return this.flowerReactiveRepository.save(new Flower(id, name, color));
	}
	
	// 違う型でも入っちゃうぜ
	@GetMapping("insertFrog")
	Mono<Frog> insert(@RequestParam("id") int id, @RequestParam("name") String name) {
		
		ReactiveMongoTemplate mongoOps = new ReactiveMongoTemplate(MongoClients.create(), "database");
		
		return mongoOps.insert(new Frog(id, name))
				;
	}

	@GetMapping("findOne")
	Mono<Flower> findOne() {
		ReactiveMongoTemplate mongoOps = new ReactiveMongoTemplate(MongoClients.create(), "database");
		
		return mongoOps.findOne(new Query(Criteria.where("name").is("dalia")), Flower.class);
	}
	
	@GetMapping(value="findAll")
	Flux<Flower> findAll() {
//		ReactiveMongoTemplate mongoOps = new ReactiveMongoTemplate(MongoClients.create(), "database");
//		
//		return mongoOps.findAll(Flower.class);
		return this.flowerReactiveRepository.findAll();
	}
	
	@GetMapping(value="findFrogAll")
	Flux<Frog> findFrogAll() {
		ReactiveMongoTemplate mongoOps = new ReactiveMongoTemplate(MongoClients.create(), "database");
		
		return mongoOps.findAll(Frog.class);
	}
	
	@GetMapping(value="findbycolor")
	Flux<Flower> findByColor(@RequestParam("color") String color) {
		return this.flowerReactiveRepository.findByColor(color);
	}
}
