package com.indecisive.meshidoko.vote;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.indecisive.meshidoko.R;
import com.indecisive.meshidoko.managers.APIManager;
import com.indecisive.meshidoko.managers.VoteManager;
import com.indecisive.meshidoko.models.Restaurant;

public class VoteActivity extends Activity {
	private VoteManager voteManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote);
		Intent myIntent = getIntent();
		Bundle extras = myIntent.getExtras();
		int peopleNum = extras.getInt("peopleNum");
		String genreCode = extras.getString("genreCode");
		
		// ジャンルコードを元にホットペッパーAPIを利用し、候補店舗をランダムに3つ取得する
		APIManager apiManager = new APIManager(genreCode);
		ArrayList<Restaurant> restaurantList = apiManager.search();
		
		voteManager = new VoteManager(peopleNum);
	}

	public void onItemClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("店舗情報");
		
	}
}
