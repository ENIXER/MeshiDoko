package com.indecisive.meshidoko.result;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.indecisive.meshidoko.R;
import com.indecisive.meshidoko.managers.VoteManager;
import com.indecisive.meshidoko.models.Restaurant;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
		String imageUrl = restaurant.getImageUrl();
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			InputStream is = connection.getInputStream();
			Bitmap bm = BitmapFactory.decodeStream(is);
			image.setImageBitmap(bm);
		} catch (Exception e) {
		}
		TextView name = (TextView) findViewById(R.id.restaurant_name);
		name.setText(restaurant.getName());
	}
}
