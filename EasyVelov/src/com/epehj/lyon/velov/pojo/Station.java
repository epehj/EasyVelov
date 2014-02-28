package com.epehj.lyon.velov.pojo;

import com.epehj.lyon.velov.tools.Globals;
import com.google.android.gms.maps.model.Marker;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class Station  {
	private String number;
	private String name;
	private String latitude, longitude;
	private Marker marker;
	private String contract;
	private RealTimeInfos rti;

	public Station(final String num, final String nom, final String lati, final String longi) {

		number = num;
		name = nom;
		latitude = lati;
		longitude = longi;
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

	public void setMarker(final Marker m) {
		marker = m;
	}

	public void setContract(final String contract) {
		this.contract = contract;
	}

	public String createCacheKey() {
		// TODO Auto-generated method stub
		return " cache " + contract;
	}

	public String getContract() {
		return contract;
	}

}
