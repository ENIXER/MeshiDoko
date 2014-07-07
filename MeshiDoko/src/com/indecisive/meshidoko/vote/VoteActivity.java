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
import com.indecisive.meshidoko.tasks.APIRequestTask;

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

		// 投票マネージャ
		voteManager = new VoteManager(peopleNum);

		// ジャンルコードを元にホットペッパーAPIを利用し、候補店舗をランダムに3つ取得する
		new APIRequestTask() {
			@Override
			protected void onPostExecute(ArrayList<Restaurant> result) {
				if (result != null) {
					for (Restaurant restaurant : result) {
						// TODO: 取得した3つの候補店舗の情報を画面に出力する
					}
				}
			}
		}.execute(genreCode);

	}

	public void onItemClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("店舗情報");

	}
}
