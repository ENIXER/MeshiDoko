package com.indecisive.meshidoko.models;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Restaurant implements Serializable {

	private static final long serialVersionUID = 5861556354097654856L;

	private int id;

	private String name;

	private String address;

	private String imageUrl;

	transient private Bitmap image;

	private byte[] mBitmapArray;
	
	private String catchCopy;
	
	private int resourceId;
	
	private int requiredTime;	// TODO: 緯度経度をフィールドとして持ち、所要時間はGoogleMapAPIを利用して取得するようにする

	public final void serializeBitmap() {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 100, bout);
		mBitmapArray = bout.toByteArray();
	}

	public final Bitmap getSerializedImage() {
		if (mBitmapArray == null) {
			return null;
		}
		Bitmap bitmap = BitmapFactory.decodeByteArray(mBitmapArray, 0,
				mBitmapArray.length);
		return bitmap;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
		serializeBitmap();
	}

	public String getCatchCopy() {
		return catchCopy;
	}

	public void setCatchCopy(String catchCopy) {
		this.catchCopy = catchCopy;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public int getRequiredTime() {
		return requiredTime;
	}

	public void setRequiredTime(int requiredTime) {
		this.requiredTime = requiredTime;
	}

	public Restaurant(int id, String name, String address, String imageUrl) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.imageUrl = imageUrl;
		this.image = null;
	}
	
	public Restaurant(int id, String name, String address, String catchCopy, int resourceId, int requiredTime) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.catchCopy = catchCopy;
		this.resourceId = resourceId;
		this.requiredTime = requiredTime;
		this.image = null;
	}
}