package com.indecisive.meshidoko.managers;

import org.junit.* ;
import static org.junit.Assert.* ;

import com.indecisive.meshidoko.managers.VoteManager;

public class VoteManagerTest {

	@Test
	public void test_voteAccumulation() {
		System.out.println("Test if vote is accumulated correctly...") ;
		int peopleNum = 3;
		VoteManager voteManager = new VoteManager(peopleNum);
		for (int i = 0; i < peopleNum; i++) {
			// everyone votes to restaurant 0
			voteManager.vote(0);
		}
		assertTrue(voteManager.getVoteTotalOfRestaurant(0) == peopleNum);
	}

	@Test
	public void test_voteMultiple() {
		System.out.println("Test if vote is accumulated for each restaurant...") ;
		int peopleNum = 6;
		int restaurantNum = 3;
		VoteManager voteManager = new VoteManager(peopleNum);
		for (int i = 0; i < peopleNum; i++) {
			voteManager.vote(i % restaurantNum);
		}
		for (int i = 0; i < restaurantNum; i++) {
			assertTrue(voteManager.getVoteTotalOfRestaurant(i) == 2); // FIXME: 2 is magic number
		}
	}

	@Test
	public void test_isVoteFinished() {
		System.out.println("Test if isVoteFinished returns correct value...");
		int peopleNum = 2;
		VoteManager voteManager = new VoteManager(peopleNum);
		voteManager.vote(0);
		assertFalse(voteManager.isVoteFinished());
		voteManager.vote(0);
		assertTrue(voteManager.isVoteFinished());
	}
}
