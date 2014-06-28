package com.indecisive.meshidoko.setting;

import java.util.ArrayList;
import java.util.List;

import com.indecisive.meshidoko.R;
import com.indecisive.meshidoko.vote.VoteActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		String people[] = { "3人", "4人", "5人", "6人", "7人", "8人", "9人", "10人" };
		ArrayAdapter<String> numAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, people);
		numAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner numSpinner = (Spinner) findViewById(R.id.num_of_people);
		numSpinner.setAdapter(numAdapter);

		List<String> genre = new ArrayList<String>();
		genre.add("ラーメン");
		genre.add("定食");
		genre.add("カレー");
		ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, genre);
		genreAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner spinner = (Spinner) findViewById(R.id.select_genre);
		spinner.setAdapter(genreAdapter);
	}

	public void onDecideButtonClick(View v) {
		Intent intent = new Intent(this, VoteActivity.class);
		startActivity(intent);
	}
}
