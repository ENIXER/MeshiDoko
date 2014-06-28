package com.indecisive.meshidoko.vote;

import com.indecisive.meshidoko.R;
import com.indecisive.meshidoko.result.ResultActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VoteActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote);
	}

	public void onItemClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("店舗情報");
		switch (v.getId()) {
		case R.id.cand0:
			builder.setMessage("1番目の店舗");
			break;
		case R.id.cand1:
			builder.setMessage("2番目の店舗");
			break;
		case R.id.cand2:
			builder.setMessage("3番目の店舗");
			break;
		}
		builder.setPositiveButton("投票する",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(VoteActivity.this,
								ResultActivity.class);
						startActivity(intent);
						VoteActivity.this.finish();
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
}
