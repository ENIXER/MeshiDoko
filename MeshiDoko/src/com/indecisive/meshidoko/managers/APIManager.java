/**
 * 
 */
package com.indecisive.meshidoko.managers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.util.Log;

import com.indecisive.meshidoko.models.Location;
import com.indecisive.meshidoko.models.Restaurant;
import com.indecisive.meshidoko.tasks.APIRequestTask;

/**
 * @author KOJISUKE
 * 
 */
public class APIManager {

	private static final String API_KEY = "7501945f51a18bff";
	private static final String WEB_ENCODE_UTF8 = "UTF-8";
	private static final String URL_GOURMET_SEARCH = "http://webservice.recruit.co.jp/hotpepper/gourmet/v1/";

	private String genreCode;

	public String getGenreCode() {
		return genreCode;
	}

	public void setGenreCode(String genreCode) {
		this.genreCode = genreCode;
	}

	public APIManager(String genreCode) {
		this.genreCode = genreCode;
	}

	public ArrayList<Restaurant> search() {
		ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();

		try {
			// 検索クエリの作成
			HashMap<String, String> request = getRequest();
			// 検索クエリを元にAPIを利用し、レスポンスを取得する
			String response = getResponse(URL_GOURMET_SEARCH, request);
			Log.d("hoge", "hoge");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return restaurantList;
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
			// 検索範囲はデフォルト(=1000m)

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

		APIRequestTask apiTask = new APIRequestTask();
		apiTask.execute(requestURL.toString());
		
		synchronized (this) 
        {
            try 
            {
                this.wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

		return apiTask.getResponse();

		// URL urlObj = new URL(requestURL.toString());
		// URLConnection connection = urlObj.openConnection();
		// HttpURLConnection http = (HttpURLConnection) urlObj.openConnection();
		// connection.setDoInput(true);
		// http.setRequestMethod("GET");
		// http.connect();

		// HttpClient httpClient = new DefaultHttpClient();
		// HttpGet httpGet = new HttpGet(requestURL.toString());
		//
		// HttpResponse httpResponse = null;
		// try {
		// httpResponse = httpClient.execute(httpGet);
		//
		// int status = httpResponse.getStatusLine().getStatusCode();
		//
		// if (HttpStatus.SC_OK == status) {
		// try {
		// ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// httpResponse.getEntity().writeTo(outputStream);
		// return outputStream.toString();
		// } catch (Exception e) {
		// Log.d("HttpSampleActivity", "Error");
		// }
		// } else {
		// Log.d("HttpSampleActivity", "Status" + status);
		// }
		// } catch (ClientProtocolException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		// System.out.println(http.getResponseCode());
		// System.out.println(http.getResponseMessage());
		//
		// Map<String, List<String>> headerFields = http.getHeaderFields();
		// Iterator<String> headerIt = headerFields.keySet().iterator();
		// while (headerIt.hasNext()) {
		// String key = headerIt.next();
		// List<String> valList = headerFields.get(key);
		// if (key != null) {
		// StringBuilder sb = new StringBuilder();
		// for (String val : valList) {
		// if (sb.length() > 0)
		// sb.append("\n");
		// sb.append(val);
		// }
		// // System.out.println(key + " : " + sb.toString());
		// }
		// }
		//
		// // ボディ(コンテンツ)の取得
		// InputStream is = http.getInputStream();
		// BufferedReader reader = new BufferedReader(new InputStreamReader(is,
		// WEB_ENCODE_UTF8));
		// StringBuilder sbBody = new StringBuilder();
		// String s;
		// while ((s = reader.readLine()) != null) {
		// sbBody.append(s);
		// sbBody.append("\n");
		// }
		// System.out.println(sbBody.toString());
		//
		// return http.toString();
	}
}
