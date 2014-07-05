package com.indecisive.meshidoko.vote;

import com.indecisive.meshidoko.R;
import com.indecisive.meshidoko.result.ResultActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.indecisive.meshidoko.managers.VoteManager;

public class VoteActivity extends Activity {
	private VoteManager voteManager;
	private int selected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote);
		Intent myIntent = getIntent();
		Bundle extras = myIntent.getExtras();
		int peopleNum = extras.getInt("peopleNum");
		String genreCode = extras.getString("genreCode");
		
		// ジャンルコードを元にホットペッパーAPIを利用し、候補店舗をランダムに3つ取得する
		
		voteManager = new VoteManager(peopleNum);
	}

	public void onItemClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("店舗情報");
		switch (v.getId()) {
		case R.id.cand0:
			builder.setMessage("1番目の店舗");
			selected = 0;
			break;
		case R.id.cand1:
			builder.setMessage("2番目の店舗");
			selected = 1;
			break;
		case R.id.cand2:
			builder.setMessage("3番目の店舗");
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
}
