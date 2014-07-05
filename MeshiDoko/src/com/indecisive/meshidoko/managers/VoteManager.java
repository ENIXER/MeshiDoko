package com.indecisive.meshidoko.managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.indecisive.meshidoko.models.Restaurant;

public class VoteManager implements Serializable {
	private static final long serialVersionUID = -4734049795992977745L;
	private int[] voteTotals = new int[3];
	private int voteNum;
	private int voteCount = 0;
	private List<Restaurant> restaurants = new ArrayList<Restaurant>();

	public VoteManager(int peopleNum) {
		voteNum = peopleNum;
		restaurants.add(new Restaurant());
	}

	// restaurantId is 0 ~ 2 for now because there are only 3 restaurants
	public void vote(int restaurantId) {
		voteTotals[restaurantId]++;
		voteCount++;
	}

	public int getVoteTotalOfRestaurant(int restaurantId) {
		return voteTotals[restaurantId];
	}

	public boolean isVoteFinished() {
		if (voteCount < voteNum) {
			return false;
		} else {
			return true;
		}
	}

	public List<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(List<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public Restaurant getVoteResult() {
		Restaurant result = restaurants.get(0);
		int max = 0;
		for (int i = 0; i < 3; i++) {
			if (voteTotals[i] > voteTotals[max]) {
				result = restaurants.get(i);
				max = i;
			}
		}
		return result;
	}
}
