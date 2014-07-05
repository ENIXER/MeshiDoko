package com.indecisive.meshidoko.models;

import java.util.ArrayList;

public class Restaurant {
	
	private int id;
	
	private String name;
	
	private String address;
	
	private String imageUrl;
	
	private ArrayList<Genre> genreList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public ArrayList<Genre> getGenreList() {
		return genreList;
	}

	public void setGenreList(ArrayList<Genre> genreList) {
		this.genreList = genreList;
	}
	
	public Restaurant() {
		// TODO: 中身
	}
}