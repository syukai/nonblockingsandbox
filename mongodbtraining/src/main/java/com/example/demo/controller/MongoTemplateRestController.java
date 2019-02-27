package com.example.demo.controller;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.document.Flower;
import com.example.demo.document.Frog;
import com.mongodb.reactivestreams.client.MongoClients;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("template")
public class MongoTemplateRestController {
	// Flowerを登録する
	@GetMapping("insert")
	Mono<Flower> insert(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("color") String color ){
		ReactiveMongoTemplate mongoOps = new ReactiveMongoTemplate(MongoClients.create(), "database");
		
		return mongoOps.insert(new Flower(id, name, color))
				.flatMap(p -> mongoOps.findOne(new Query(Criteria.where("id").is(p.getId())), Flower.class));
	}
	
	// 違う型でも入っちゃうぜ
	@GetMapping("insertFrog")
	Mono<Frog> insert(@RequestParam("id") int id, @RequestParam("name") String name) {
		// 型ごとに別のコレクションに入る
		ReactiveMongoTemplate mongoOps = new ReactiveMongoTemplate(MongoClients.create(), "database");
		
		return mongoOps.insert(new Frog(id, name));
	}

	// 条件指定して1件取得
	@GetMapping("findOne")
	Mono<Flower> findOne() {
		ReactiveMongoTemplate mongoOps = new ReactiveMongoTemplate(MongoClients.create(), "database");
		
		return mongoOps.findOne(new Query(Criteria.where("name").is("dalia")), Flower.class);
	}

	// Frogを全て取得
	@GetMapping(value="findFrogAll")
	Flux<Frog> findFrogAll() {
		ReactiveMongoTemplate mongoOps = new ReactiveMongoTemplate(MongoClients.create(), "database");
		
		// 型を指定するとその型のデータだけが取れる
		return mongoOps.findAll(Frog.class);
	}
}
