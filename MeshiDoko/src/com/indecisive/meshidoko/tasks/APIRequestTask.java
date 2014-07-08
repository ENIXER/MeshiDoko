/**
 * 
 */
package com.indecisive.meshidoko.tasks;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.indecisive.meshidoko.managers.APIManager;
import com.indecisive.meshidoko.models.Restaurant;

/**
 * @author KOJISUKE
 * 
 */
public class APIRequestTask extends AsyncTask<String, Integer, ArrayList<Restaurant>> {

	@Override
	protected ArrayList<Restaurant> doInBackground(String... contents) {
		// ジャンルコードを元にAPI通信を行い、店をランダムに3つ取得するタスク
		String genreCode = contents[0];

		// APIManagerインスタンスの生成
		APIManager apiManager = new APIManager(genreCode);
		// レストランの検索(apiManager.search())
		ArrayList<Restaurant> restaurantList = apiManager.search();

		return restaurantList;
	}

}