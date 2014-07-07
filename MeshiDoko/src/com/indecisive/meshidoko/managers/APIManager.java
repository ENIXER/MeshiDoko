/**
 * 
 */
package com.indecisive.meshidoko.managers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;

import com.indecisive.meshidoko.models.Location;
import com.indecisive.meshidoko.models.Restaurant;

/**
 * @author KOJISUKE
 * 
 */
public class APIManager {

	private static final String API_KEY = "7501945f51a18bff";
	private static final String WEB_ENCODE_UTF8 = "UTF-8";
	private static final String URL_GOURMET_SEARCH = "http://webservice.recruit.co.jp/hotpepper/gourmet/v1/";

	private String genreCode;
	
	private ArrayList<Restaurant> restaurantList;

	public String getGenreCode() {
		return genreCode;
	}

	public void setGenreCode(String genreCode) {
		this.genreCode = genreCode;
	}

	public ArrayList<Restaurant> getRestaurantList() {
		return restaurantList;
	}

	public void setRestaurantList(ArrayList<Restaurant> restaurantList) {
		this.restaurantList = restaurantList;
	}

	public APIManager(String genreCode) {
		this.genreCode = genreCode;
	}

	public ArrayList<Restaurant> search() {

		try {
			// 検索クエリの作成
			HashMap<String, String> request = getRequest();
			// 検索クエリを元にAPIを利用し、レスポンスを取得する
			String response = getResponse(URL_GOURMET_SEARCH, request);
			// レスポンスを整形し、候補店舗情報を取得する
			parse(response);

			return restaurantList;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 検索用リクエストAPIを作成・取得する
	 * 
	 * @return
	 */
	private HashMap<String, String> getRequest() {
		HashMap<String, String> request = new HashMap<String, String>();
		// TODO: 現在地の取得
		Location location = new Location();

		try {
			// 検索範囲はデフォルト(=1000m)
			request.put("genre", URLEncoder.encode(genreCode, WEB_ENCODE_UTF8));
			request.put("lat", URLEncoder.encode(
					Double.toString(location.getLatitude()), WEB_ENCODE_UTF8));
			request.put("lng", URLEncoder.encode(
					Double.toString(location.getLongitude()), WEB_ENCODE_UTF8));

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return request;
	}

	/**
	 * 検索レスポンスを受け取る
	 * 
	 * @param url
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String getResponse(String url, HashMap<String, String> request)
			throws IOException {
		StringBuilder requestURL = new StringBuilder(url + "?key=" + API_KEY);
		Iterator<String> it = request.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = request.get(key);
			requestURL.append("&" + key + "=" + value);
		}

		// 非同期処理でAPI通信を行う
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(requestURL.toString());

		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet);

			int status = httpResponse.getStatusLine().getStatusCode();

			if (HttpStatus.SC_OK == status) {
				try {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					httpResponse.getEntity().writeTo(outputStream);
					return outputStream.toString();
				} catch (Exception e) {
					Log.d("APIManager", "Error");
				}
			} else {
				Log.d("APIManager", "Status: " + status);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * レスポンス(XML形式)をパースする
	 * 
	 * @param str
	 */
	void parse(String str) {

		try {
			XmlPullParser xmlPullParser = Xml.newPullParser();
			xmlPullParser.setInput(new StringReader(str));

			int eventType;
			while ((eventType = xmlPullParser.next()) != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG
						&& "title".equals(xmlPullParser.getName())) {
					Log.d("APIManager", xmlPullParser.nextText());
				}
			}
			// TODO: 取得結果をパースしてrestaurantListに入れる
			
		} catch (Exception e) {
			Log.d("APIManager", "Error: parse");
		}
	}
}
