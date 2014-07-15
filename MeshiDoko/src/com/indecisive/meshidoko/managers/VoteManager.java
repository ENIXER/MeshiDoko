package com.indecisive.meshidoko.managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.indecisive.meshidoko.models.Restaurant;

public class VoteManager implements Serializable {
	private static final long serialVersionUID = -4734049795992977745L;
	private int[] voteTotals = new int[3];
	private int voteNum;
	private int voteCount = 0;
	private List<Restaurant> restaurants = new ArrayList<Restaurant>();

	public VoteManager(int peopleNum) {
		voteNum = peopleNum;
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
		Random rand = new Random(System.currentTimeMillis());
		Restaurant result = restaurants.get(0);
		int max = 0;
		for (int i = 0; i < 3; i++) {
			if (voteTotals[i] > voteTotals[max]) {
				result = restaurants.get(i);
				max = i;
			} else if (voteTotals[i] == voteTotals[max]) {
				int a = rand.nextInt(2);
				if (a == 1) {
					result = restaurants.get(i);
					max = i;
				}
			}
		}
		if (voteTotals[0] == voteTotals[1] && voteTotals[1] == voteTotals[2])
			result = restaurants.get(rand.nextInt(3));
		return result;
	}
}
