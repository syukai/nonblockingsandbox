package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.document.Flower;
import com.example.demo.repository.FlowerReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive")
public class ReactiveFlowerRestController {
	
	private FlowerReactiveRepository flowerReactiveRepository;
	
	ReactiveFlowerRestController(
			FlowerReactiveRepository flowerReactiveRepository) {
		this.flowerReactiveRepository = flowerReactiveRepository;
	}
	

	@GetMapping("insert")
	Mono<Flower> insert(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("color") String color ){
		return this.flowerReactiveRepository.save(new Flower(id, name, color));
	}
	
	@GetMapping("findOne")
	Mono<Flower> findOne(@RequestParam("id") int id) {
		return this.flowerReactiveRepository.findById(id);
	}
	
	@GetMapping(value="findAll")
	Flux<Flower> findAll() {
		return this.flowerReactiveRepository.findAll();
	}
	
	@GetMapping(value="withoutid")
	Flux<Flower> withoutid() {
		return this.flowerReactiveRepository.findByName("rose");
	}
	
	
	@GetMapping(value="findbycolor")
	Flux<Flower> findByColor(@RequestParam("color") String color) {
		return this.flowerReactiveRepository.findByColor(color);
	}
}
