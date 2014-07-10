/**
 * 
 */
package com.indecisive.meshidoko.models;

/**
 * @author KOJISUKE
 * 
 */
public class Location {

	/** 西早稲田キャンパス - 緯度 */
	private static final double LAT_NISHIWASEDA = 35.706069;
	/** 西早稲田キャンパス - 経度 */
	private static final double LNG_NISHIWASEDA = 139.70681;

	/** 緯度 */
	private double latitude;

	/** 経度 */
	private double longitude;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Location() {
		this(LAT_NISHIWASEDA, LNG_NISHIWASEDA);
	}

	public Location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
