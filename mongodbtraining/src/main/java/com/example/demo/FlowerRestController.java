package com.example.demo;

import java.util.List;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.reactivestreams.client.MongoClients;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class FlowerRestController {

	@GetMapping("insert")
	Mono<Flower> insert(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("color") String color ){
		ReactiveMongoTemplate mongoOps = new ReactiveMongoTemplate(MongoClients.create(), "database");
		
		return mongoOps.insert(new Flower(id, name, color));
	}

	@GetMapping("findOne")
	Mono<Flower> findOne() {
		ReactiveMongoTemplate mongoOps = new ReactiveMongoTemplate(MongoClients.create(), "database");
		
		return mongoOps.findOne(new Query(Criteria.where("name").is("dalia")), Flower.class);
	}
	
	@GetMapping("findAll")
	Flux<Flower> findAll() {
		ReactiveMongoTemplate mongoOps = new ReactiveMongoTemplate(MongoClients.create(), "database");
		
		return mongoOps.findAll(Flower.class);
	}
}
