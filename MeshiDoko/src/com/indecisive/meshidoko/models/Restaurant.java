package com.indecisive.meshidoko.models;

import android.graphics.Bitmap;


public class Restaurant {

	private int id;

	private String name;

	private String address;

	private String imageUrl;
	
	private Bitmap image;

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
	
	public Bitmap getImage(){
		return image;
	}
	
	public void setImage(Bitmap image){
		this.image = image;
	}

	public Restaurant(int id, String name, String address, String imageUrl) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.imageUrl = imageUrl;
		this.image = null;
	}
}