/**
 * 
 */
package com.indecisive.meshidoko.managers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
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
import org.xmlpull.v1.XmlPullParserFactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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
	private static final String API_IS_OPEN_TIME = "now";
	private static final String API_LUNCH_VALUE = "1";
	private static final String API_BUDGET_VALUE = "B001";
	private static final String API_RANGE_VALUE = "4";
	private static final String API_COUNT_MAX_VALUE = "100";

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
		this.restaurantList = new ArrayList<Restaurant>();
	}

	public ArrayList<Restaurant> search() {

		HashMap<String, String> request = new HashMap<String, String>();
		
		try {
			// 検索クエリの作成
			request = getRequest();
			// 検索クエリを元にAPIを利用し、レスポンスを取得する
			String response = getResponse(URL_GOURMET_SEARCH, request);
			// レスポンスを整形し、候補店舗情報を取得する
			parse(response);

			return restaurantList;
		} catch (UnsupportedEncodingException e) {
			// Auto-generated catch block
			Log.d("APIManager", "Error:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			// Auto-generated catch block
			Log.d("APIManager", "Error:" + e);
			e.printStackTrace();
		} finally {
			if (request != null) {
				request.clear();
			}
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
			request.put("genre", URLEncoder.encode(genreCode, WEB_ENCODE_UTF8));
			request.put("lat", URLEncoder.encode(
					Double.toString(location.getLatitude()), WEB_ENCODE_UTF8));
			request.put("lng", URLEncoder.encode(
					Double.toString(location.getLongitude()), WEB_ENCODE_UTF8));

			// デフォルト条件(営業時間+ランチ有無+予算+検索範囲+上限取得数)
			request.put("is_open_time",
					URLEncoder.encode(API_IS_OPEN_TIME, WEB_ENCODE_UTF8));
			request.put("lunch",
					URLEncoder.encode(API_LUNCH_VALUE, WEB_ENCODE_UTF8));
			request.put("budget",
					URLEncoder.encode(API_BUDGET_VALUE, WEB_ENCODE_UTF8));
			request.put("range",
					URLEncoder.encode(API_RANGE_VALUE, WEB_ENCODE_UTF8));
			request.put("count",
					URLEncoder.encode(API_COUNT_MAX_VALUE, WEB_ENCODE_UTF8));

		} catch (UnsupportedEncodingException e) {
			// Auto-generated catch block
			Log.d("APIManager", "Error:" + e);
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

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet);

			int status = httpResponse.getStatusLine().getStatusCode();

			if (HttpStatus.SC_OK == status) {
				try {
					httpResponse.getEntity().writeTo(outputStream);
					return outputStream.toString();
				} catch (Exception e) {
					Log.d("APIManager", "Error");
				}
			} else {
				Log.d("APIManager", "Status: " + status);
			}
		} catch (ClientProtocolException e) {
			Log.d("APIManager", "Error:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("APIManager", "Error:" + e);
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}

		return "";
	}

	/**
	 * レスポンス(XML形式)をパースする
	 * 
	 * @param str
	 */
	private void parse(String str) {

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);

			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(str));

			int eventType;
			int count = 1;
			while ((eventType = xmlPullParser.next()) != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG: {
					String key = xmlPullParser.getName();
					if ("shop".equals(key)) {
						// <shop>
						String name = "";
						String address = "";
						String imageUrl = "";
						while (!((eventType = xmlPullParser.next()) == XmlPullParser.END_TAG && "shop"
								.equals(xmlPullParser.getName()))) {
							switch (eventType) {
							case XmlPullParser.START_TAG: {
								if ("".equals(name)
										&& "name".equals(xmlPullParser
												.getName())) {
									eventType = xmlPullParser.next();
									if (eventType == XmlPullParser.TEXT) {
										name = xmlPullParser.getText();
									}
								} else if ("".equals(imageUrl)
										&& eventType == XmlPullParser.START_TAG
										&& "pc".equals(xmlPullParser.getName())) {
									eventType = xmlPullParser.next();
									if (eventType == XmlPullParser.START_TAG
											&& "l".equals(xmlPullParser
													.getName())) {
										eventType = xmlPullParser.next();
										if (eventType == XmlPullParser.TEXT) {
											imageUrl = xmlPullParser.getText();
										}
									}
								} else if ("".equals(address)
										&& eventType == XmlPullParser.START_TAG
										&& "address".equals(xmlPullParser
												.getName())) {
									eventType = xmlPullParser.next();
									if (eventType == XmlPullParser.TEXT) {
										address = xmlPullParser.getText();
									}
								}
								break;
							}
							}
						}
						// </shop>
						Restaurant restaurant = new Restaurant(count, name,
								address, imageUrl);
						URL url = new URL(restaurant.getImageUrl());
						InputStream is = url.openStream();
						Bitmap bmp = BitmapFactory.decodeStream(is);
						restaurant.setImage(bmp);
						restaurantList.add(restaurant);
						count++;
					}
					break;
				}
				default:
					break;
				}
			}

		} catch (Exception e) {
			Log.d("APIManager", "Error: parse");
			e.printStackTrace();
		}
	}
}
