package com.indecisive.meshidoko.views;

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
import com.indecisive.meshidoko.tasks.APIRequestTask;

public class VoteActivity extends Activity {
	private VoteManager voteManager;
	private int selected;

	private static final int RESTAURANT_NUM = 3;

	private ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();

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

		if ("G004".equals(genreCode)) {
			// TODO: ジャンルごとに...
			setJapaneseRestaurantData();
		} else if ("G005".equals(genreCode)) {
			// TODO: ジャンルごとに...
			setRamenRestaurantData();
		} else if ("G007".equals(genreCode)) {
			// TODO: ジャンルごとに...
			setRamenRestaurantData();
		} else if ("G013".equals(genreCode)) {
			// TODO: ジャンルごとに...
			setRamenRestaurantData();
		}

		// ジャンルコードを元にホットペッパーAPIを利用し、候補店舗をランダムに3つ取得する
		new APIRequestTask() {
			private ProgressDialog progressDialog;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				progressDialog = new ProgressDialog(VoteActivity.this);
				progressDialog.setMessage("読み込み中");
				progressDialog.setCancelable(false);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.show();
			}

			@Override
			protected void onPostExecute(ArrayList<Restaurant> result) {
				if (restaurantList != null) {
					voteManager.setRestaurants(restaurantList);
					int[] idList = new int[3];
					idList[0] = R.id.cand0;
					idList[1] = R.id.cand1;
					idList[2] = R.id.cand2;

					progressDialog.dismiss();
					progressDialog = null;
					int i = 0;
					for (Restaurant restaurant : restaurantList) {
						FrameLayout cand = (FrameLayout) findViewById(idList[i++]);
						ImageView iv = (ImageView) cand
								.findViewById(R.id.store_image);
						iv.setImageResource(restaurant.getResourceId());
						TextView storeNameTV = (TextView) cand
								.findViewById(R.id.store_name);
						storeNameTV.setText(restaurant.getName());
						TextView catchCopyTV = (TextView) cand
								.findViewById(R.id.catch_copy);
						catchCopyTV.setText(restaurant.getCatchCopy());
						TextView requiredTimeTV = (TextView) cand
								.findViewById(R.id.required_time);
						requiredTimeTV.setText(String.valueOf(restaurant
								.getRequiredTime() + "分"));
						cand.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								onItemClick(v);
							}
						});
					}
				}

				// if (result != null) {
				// // ランダムに3つ店を選ぶ
				// restaurantList = getRestaurantListAtRandom(result);
				// voteManager.setRestaurants(restaurantList);
				// int[] idList = new int[3];
				// idList[0] = R.id.cand0;
				// idList[1] = R.id.cand1;
				// idList[2] = R.id.cand2;
				//
				// if (restaurantList != null) {
				// progressDialog.dismiss();
				// progressDialog = null;
				// int i = 0;
				// for (Restaurant restaurant : restaurantList) {
				// FrameLayout cand = (FrameLayout) findViewById(idList[i++]);
				// ImageView iv = (ImageView) cand
				// .findViewById(R.id.store_image);
				// iv.setImageBitmap(restaurant.getImage());
				// TextView tv = (TextView) cand
				// .findViewById(R.id.store_name);
				// tv.setText(restaurant.getName());
				// cand.setOnClickListener(new OnClickListener() {
				// @Override
				// public void onClick(View v) {
				// onItemClick(v);
				// }
				// });
				// }
				// }
				// }
			}
		}.execute(genreCode);

	}

	public void onItemClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("店舗情報");
		switch (v.getId()) {
		case R.id.cand0:
			builder.setMessage(restaurantList.get(0).getName() + "\n"
					+ restaurantList.get(0).getAddress());
			selected = 0;
			break;
		case R.id.cand1:
			builder.setMessage(restaurantList.get(1).getName() + "\n"
					+ restaurantList.get(1).getAddress());
			selected = 1;
			break;
		case R.id.cand2:
			builder.setMessage(restaurantList.get(2).getName() + "\n"
					+ restaurantList.get(2).getAddress());
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

	private void setRamenRestaurantData() {
		Restaurant menzy = new Restaurant(1, "麺爺あぶら 西早稲田店", "東京都新宿区大久保2-2-13",
				"油そば", R.drawable.menzy, 5);
		Restaurant takatora = new Restaurant(2, "麺屋武蔵鷹虎 高田馬場店",
				"東京都新宿区高田馬場2-19-7", "濃厚つけ麺", R.drawable.takatora, 12);
		Restaurant junren = new Restaurant(3, "さっぽろ純連　東京店",
				"東京都新宿区高田馬場3-12-8 高田馬場センタービル1F", "札幌ラーメン", R.drawable.junren,
				18);
		restaurantList.add(menzy);
		restaurantList.add(takatora);
		restaurantList.add(junren);
	}

	private void setJapaneseRestaurantData() {
		Restaurant aduchi = new Restaurant(1, "つけ蕎麦 安土 高田馬場本店",
				"東京都新宿区高田馬場4-18-9", "蕎麦", R.drawable.aduchi, 18);
		Restaurant narikura = new Restaurant(2, "成蔵", "東京都新宿区高田馬場1丁目32-11",
				"とんかつ", R.drawable.narikura, 10);
		Restaurant uoichi = new Restaurant(3, "築地 魚一 高田馬場店",
				"東京都新宿区高田馬場3丁目2-5", "海鮮料理", R.drawable.uoichi, 18);
		restaurantList.add(aduchi);
		restaurantList.add(narikura);
		restaurantList.add(uoichi);
	}
}
