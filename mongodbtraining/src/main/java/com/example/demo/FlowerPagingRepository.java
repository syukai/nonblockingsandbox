package com.example.demo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface FlowerPagingRepository extends PagingAndSortingRepository<Flower, String> {
	
	List<Flower> findByColor(String color);
}

