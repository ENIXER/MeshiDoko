/**
 * 
 */
package com.indecisive.meshidoko.tasks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.indecisive.meshidoko.managers.APIManager;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author KOJISUKE
 * 
 */
public class APIRequestTask extends AsyncTask<String, Integer, Integer> {

	private String response;

	public String getResponse() {
		return this.response;
	}

	@Override
	protected Integer doInBackground(String... contents) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(contents[0]);

		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet);

			int status = httpResponse.getStatusLine().getStatusCode();

			if (HttpStatus.SC_OK == status) {
				try {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					httpResponse.getEntity().writeTo(outputStream);
					this.response = outputStream.toString();
				} catch (Exception e) {
					Log.d("HttpSampleActivity", "Error");
				}
			} else {
				Log.d("HttpSampleActivity", "Status" + status);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		synchronized (APIManager.class) {
			APIManager.class.notify();
		}
		return Integer.valueOf(httpResponse.getStatusLine().getStatusCode());
	}

}