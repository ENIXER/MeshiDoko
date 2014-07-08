package com.indecisive.meshidoko.vote;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.indecisive.meshidoko.R;
import com.indecisive.meshidoko.managers.VoteManager;
import com.indecisive.meshidoko.models.Restaurant;
import com.indecisive.meshidoko.tasks.APIRequestTask;

public class VoteActivity extends Activity {
	private VoteManager voteManager;

	private static final int RESTAURANT_NUM = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote);
		Intent myIntent = getIntent();
		Bundle extras = myIntent.getExtras();
		int peopleNum = extras.getInt("peopleNum");
		String genreCode = extras.getString("genreCode");

		// 投票マネージャ
		voteManager = new VoteManager(peopleNum);

		// ジャンルコードを元にホットペッパーAPIを利用し、候補店舗をランダムに3つ取得する
		new APIRequestTask() {
			@Override
			protected void onPostExecute(ArrayList<Restaurant> result) {
				if (result != null) {
					// ランダムに3つ店を選ぶ
					ArrayList<Restaurant> restaurantList = getRestaurantListAtRandom(result);
					// TODO: 店情報を表示する
				}
			}
		}.execute(genreCode);

	}

	public void onItemClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("店舗情報");

	}

	/**
	 * 候補店リストから3つランダムに取得する
	 * 
	 * @param restaurantList
	 * @return
	 */
	private ArrayList<Restaurant> getRestaurantListAtRandom(
			ArrayList<Restaurant> restaurantList) {
		ArrayList<Restaurant> ret = new ArrayList<Restaurant>();
		ArrayList<Integer> idList = new ArrayList<Integer>();
		int num = restaurantList.size();
		if (num >= RESTAURANT_NUM) {
			while (ret.size() < 3) {
				// ランダムに3つ店舗を選ぶ
				int id = (int) Math.floor(Math.random() * num);
				if (!idList.contains(id)) {
					Restaurant restaurant = restaurantList.get(id);
					ret.add(restaurant);
					idList.add(id);
				}
			}
		}

		return ret;
	}
}
