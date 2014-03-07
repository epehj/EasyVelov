package com.epehj.lyon.velov.pojo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

//faire une classe complete :
/**
 * toString() qui renvoie le nb de bike et stand, avec bonne orthographe
 * un statut de StationComplète (enum?) qui donne tous les cas possibles (zero, ok, nobikestands, nostandsbike)
 * 
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationComplete extends Station {

	public StationComplete(final String num, final String nom, final String lati, final String longi) {
		super(num, nom, lati, longi);
	}

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
