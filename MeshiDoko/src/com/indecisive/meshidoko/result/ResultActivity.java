package com.indecisive.meshidoko.result;

import com.indecisive.meshidoko.R;
import com.indecisive.meshidoko.managers.VoteManager;

import android.app.Activity;
import android.os.Bundle;

public class ResultActivity extends Activity {
	private VoteManager voteManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		voteManager = (VoteManager)getIntent().getExtras().getSerializable("voteManager");
	}
}
