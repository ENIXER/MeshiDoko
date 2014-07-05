package com.indecisive.meshidoko;

import com.indecisive.meshidoko.setting.SettingActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MeshiDoko extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		// TODO:ここでデータベースの初期化を行う
		Intent intent = new Intent(this, SettingActivity.class);
		startActivity(intent);
		finish();
	}
}
