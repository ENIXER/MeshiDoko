package com.indecisive.meshidoko.views;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.indecisive.meshidoko.R;
import com.indecisive.meshidoko.adapters.GenreAdapter;
import com.indecisive.meshidoko.models.Genre;

public class SettingActivity extends Activity {

	public static final int PEOPLE_NUM_MIN = 3;
	public static final int PEOPLE_NUM_MAX = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ArrayList<String> people = new ArrayList<String>();
		for (int i = PEOPLE_NUM_MIN; i <= PEOPLE_NUM_MAX; i++) {
			people.add(Integer.toString(i));
		}
		ArrayAdapter<String> numAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, people);
		numAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner numSpinner = (Spinner) findViewById(R.id.num_of_people);
		numSpinner.setAdapter(numAdapter);

		ArrayList<Genre> genreList = new ArrayList<Genre>();
		Genre japanese = new Genre("G004", "和食");
		Genre western = new Genre("G005", "洋食");
		Genre chinese = new Genre("G007", "中華");
		Genre ramen = new Genre("G013", "ラーメン");
		genreList.add(japanese);
		genreList.add(western);
		genreList.add(chinese);
		genreList.add(ramen);

		GenreAdapter genreAdapter = new GenreAdapter(this,
				android.R.layout.simple_spinner_item, genreList);
		genreAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner spinner = (Spinner) findViewById(R.id.select_genre);
		spinner.setAdapter(genreAdapter);
	}

	public void onDecideButtonClick(View v) {
		// 選択されているアイテムを取得
		Spinner peopleSpinner = (Spinner) findViewById(R.id.num_of_people);
		int peopleNum = Integer.parseInt((String) peopleSpinner
				.getSelectedItem());
		Spinner genreSpinner = (Spinner) findViewById(R.id.select_genre);
		Genre genre = (Genre) genreSpinner.getSelectedItem();

		Intent intent = new Intent(this, VoteActivity.class);
		intent.putExtra("peopleNum", peopleNum);
		intent.putExtra("genreCode", genre.getCode());
		startActivity(intent);
	}
}
