package com.indecisive.meshidoko.managers;

public class VoteManager {
	private int[] voteTotals = new int[3];
	private int voteNum;
	private int voteCount = 0;

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
}
