package com.example.demo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Frog {

	@Id
	private int id;
	
	private String name;
	
	public Frog(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
}
