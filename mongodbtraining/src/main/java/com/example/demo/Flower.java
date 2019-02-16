package com.example.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Flower {

	@Id
	private Integer id;
	
	private String name;
	
	private String color;
	
	Flower(){
		id = null;
		name = "";
		color = "";
	}
	
	Flower(int id, String name, String color){
		this.id = id;
		this.name = name;
		this.color = color;
	}
	
	
	public Integer getId() {
		return this.id;
	}
//	// IDのSetterが無いとfindOneできない。なんで？？？
//	public void setId(Integer value) {
//		this.id = value;
//	}
	
	public String getName() {
		return this.name;
	}
	
	public String getColor() {
		return this.color;
	}
}
