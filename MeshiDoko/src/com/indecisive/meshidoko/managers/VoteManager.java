package com.indecisive.meshidoko.managers;

public class VoteManager {
	private int[] voteTotals = new int[3];
	private int voteNum;

	public VoteManager(int peopleNum) {
		voteNum = peopleNum;
	}

	// restaurantId is 0 ~ 2 for now because there are only 3 restaurants
	public void vote(int restaurantId) {
		voteTotals[restaurantId]++;
	}
	
	public int getVoteTotalOfRestaurant(int restaurantId) {
		return voteTotals[restaurantId];
	}
}
