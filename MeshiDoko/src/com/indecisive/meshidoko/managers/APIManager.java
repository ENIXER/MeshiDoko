/**
 * 
 */
package com.indecisive.meshidoko.managers;

import java.util.ArrayList;

import com.indecisive.meshidoko.models.Restaurant;

/**
 * @author KOJISUKE
 *
 */
public class APIManager {
	
	private static final String API_KEY = "";
	
	private String genreCode;
	
	private ArrayList<Restaurant> restaurantList;

	public String getGenreCode() {
		return genreCode;
	}

	public void setGenreCode(String genreCode) {
		this.genreCode = genreCode;
	}

	public ArrayList<Restaurant> getRestaurantList() {
		return restaurantList;
	}

	public void setRestaurantList(ArrayList<Restaurant> restaurantList) {
		this.restaurantList = restaurantList;
	}
	
	public APIManager(String genreCode) {
		this.genreCode = genreCode;
	}
}
