package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.document.Flower;

import reactor.core.publisher.Flux;

public interface FlowerPagingRepository extends PagingAndSortingRepository<Flower, String> {
	
	Page<Flower> findByColor(String color, Pageable pageable);
}

