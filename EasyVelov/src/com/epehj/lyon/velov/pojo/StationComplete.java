package com.epehj.lyon.velov.pojo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.android.gms.maps.model.Marker;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StationComplete {
	private String number;
	private String name;
	private String lat, lng;
	private Marker marker;
	private String contract;
	private String banking;
	private String status;
	private String bike_stands;
	private String available_bike_stands;
	private String contract_name;
	private String available_bikes;
	private String last_update;

	public String getBike_stands() {
		return bike_stands;
	}

	public void setBike_stands(final String bike_stands) {
		this.bike_stands = bike_stands;
	}

	public String getNumber() {
		return number;
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

	public String getLatitude() {
		return lat;
	}

	public void setLatitude(final String latitude) {
		this.lat = latitude;
	}

	public String getLongitude() {
		return lng;
	}

	public void setLongitude(final String longitude) {
		this.lng = longitude;
	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(final Marker marker) {
		this.marker = marker;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(final String contract) {
		this.contract = contract;
	}

	public String getBanking() {
		return banking;
	}

	public void setBanking(final String banking) {
		this.banking = banking;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getAvailable_bike_stands() {
		return available_bike_stands;
	}

	public void setAvailable_bike_stands(final String available_bike_stands) {
		this.available_bike_stands = available_bike_stands;
	}

	public String getAvailable_bikes() {
		return available_bikes;
	}

	public void setAvailable_bikes(final String available_bikes) {
		this.available_bikes = available_bikes;
	}

	public String getLast_update() {
		return last_update;
	}

	public void setLast_update(final String last_update) {
		this.last_update = last_update;
	}

	public String getContract_name() {
		return contract_name;
	}

	public void setContract_name(final String contract_name) {
		this.contract_name = contract_name;
	}

}
