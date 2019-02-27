package com.example.demo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Flower {

	/**
	 * finalをつけるとFluxがSubscribeするときにエラーになる。Setterはなくてもいいけどfinalはだめ
	 */
	@Id
	private Integer id;
	
	private String name;
	
	private String color;
	
	Flower(){
		id = null;
		name = "";
		color = "";
	}
	
	public Flower(int id, String name, String color){
		this.id = id;
		this.name = name;
		this.color = color;
	}
	
	
	public Integer getId() {
		return this.id;
	}	
	public String getName() {
		return this.name;
	}
	
	public String getColor() {
		return this.color;
	}
	public void setColor(String value) {
		this.color = value;
	}
}
