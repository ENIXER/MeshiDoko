package com.indecisive.meshidoko.views;

import com.indecisive.meshidoko.R;
import com.indecisive.meshidoko.managers.VoteManager;
import com.indecisive.meshidoko.models.Restaurant;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends Activity {
	private VoteManager voteManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		voteManager = (VoteManager) getIntent().getExtras().getSerializable(
				"voteManager");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Restaurant restaurant = voteManager.getVoteResult();
		ImageView image = (ImageView) findViewById(R.id.restaurant_image);
		image.setImageResource(restaurant.getResourceId());
		TextView name = (TextView) findViewById(R.id.restaurant_name);
		name.setText(restaurant.getName());
		TextView address = (TextView) findViewById(R.id.restaurant_address);
		address.setText(restaurant.getAddress());
	}
}
