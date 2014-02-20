package com.epehj.lyon.velov.pojo;

public class Station {
	private String number;
	private String name;
	private String latitude, longitude;

	public Station(final String num, final String nom, final String lati, final String longi) {
		number = num;
		name = nom;
		latitude = lati;
		longitude = longi;
		// lat = Float.parseFloat(lati);
		// lng = Float.parseFloat(longi);
	}

	public String getNumber() {
		return number;
	}

	public String getLat() {
		return latitude;
	}

	public void setLat(final String lat) {
		this.latitude = lat;
	}

	public String getLng() {
		return longitude;
	}

	public void setLng(final String lng) {
		this.longitude = lng;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	// public float getLat() {
	// return lat;
	// }
	//
	// public void setLat(final float lat) {
	// this.lat = lat;
	// }
	//
	// public float getLng() {
	// return lng;
	// }
	//
	// public void setLng(final float lng) {
	// this.lng = lng;
	// }

}
