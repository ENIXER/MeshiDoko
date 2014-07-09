package com.indecisive.meshidoko.vote;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.indecisive.meshidoko.R;
import com.indecisive.meshidoko.managers.VoteManager;
import com.indecisive.meshidoko.models.Restaurant;
import com.indecisive.meshidoko.result.ResultActivity;
import com.indecisive.meshidoko.tasks.APIRequestTask;

public class VoteActivity extends Activity {
	private VoteManager voteManager;

	private static final int RESTAURANT_NUM = 3;
	
	private int selected;
	private ArrayList<Restaurant> restaurantList;

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
			private ProgressDialog progressDialog;
			@Override
			protected void onPreExecute(){
				super.onPreExecute();
				progressDialog = new ProgressDialog(VoteActivity.this);
				progressDialog.setMessage("読み込み中");
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.show();
			}
			
			@Override
			protected void onPostExecute(ArrayList<Restaurant> result) {
				if (result != null) {
					// ランダムに3つ店を選ぶ
					restaurantList = getRestaurantListAtRandom(result);
					int[] idList = new int[3];
					idList[0] = R.id.cand0;
					idList[1] = R.id.cand1;
					idList[2] = R.id.cand2;
					
					if(restaurantList != null){
						progressDialog.dismiss();
						progressDialog = null;
						int i = 0;
						for(Restaurant restaurant : restaurantList){
							FrameLayout cand = (FrameLayout)findViewById(idList[i++]);
							ImageView iv = (ImageView)cand.findViewById(R.id.store_image);
							iv.setImageBitmap(restaurant.getImage());
							TextView tv = (TextView)cand.findViewById(R.id.store_name);
							tv.setText(restaurant.getName());
							cand.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									onItemClick(v);
								}
							});
						}
					}
				}
			}
		}.execute(genreCode);

	}

	public void onItemClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("店舗情報");
		switch (v.getId()) {
		case R.id.cand0:
			builder.setMessage(restaurantList.get(0).getName() + "\n" + restaurantList.get(0).getAddress());
			selected = 0;
			break;
		case R.id.cand1:
			builder.setMessage(restaurantList.get(1).getName() + "\n" + restaurantList.get(1).getAddress());
			selected = 1;
			break;
		case R.id.cand2:
			builder.setMessage(restaurantList.get(2).getName() + "\n" + restaurantList.get(2).getAddress());
			selected = 2;
			break;
		}
		builder.setPositiveButton("投票する",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						voteManager.vote(selected);
						if (voteManager.isVoteFinished()) {
							Intent intent = new Intent(VoteActivity.this,
									ResultActivity.class);
							intent.putExtra("voteManager", voteManager);
							startActivity(intent);
							VoteActivity.this.finish();
						}
					}
				})
				.setNegativeButton("キャンセル",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// no operation
							}
						}).show();
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
