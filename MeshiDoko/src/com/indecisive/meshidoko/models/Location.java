/**
 * 
 */
package com.indecisive.meshidoko.models;

/**
 * @author KOJISUKE
 * 
 */
public class Location {

	/** 緯度 */
	private float latitude;

	/** 経度 */
	private float longitude;

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public Location(float latitude, float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
