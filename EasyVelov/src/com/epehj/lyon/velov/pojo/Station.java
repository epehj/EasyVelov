package com.epehj.lyon.velov.pojo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import android.net.Uri;

import com.epehj.lyon.velov.tools.Globals;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class Station {
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

	public String getData() {
		InputStreamReader is;
		try {
			// is = new InputStreamReader(new URL(Globals.URL + number + "&contract=" + contract
			// + "&apiKey=" + Globals.API_KEY).openStream());
			// With Uri.Builder class we can build our url is a safe manner
			final Uri.Builder uri = Uri.parse(
					String.format(
							"https://api.jcdecaux.com/vls/v1/stations/%s?contract=%s&apiKey=%s",
							number, contract, Globals.API_KEY)).buildUpon();

			is = new InputStreamReader(uri.build());
			final JsonReader jr = new JsonReader(new InputStreamReader(uri.build()));
			jr.beginArray();
			final Gson gson = new Gson();
			while (jr.hasNext()) {
				rti = gson.fromJson(jr, RealTimeInfos.class);
			}
			jr.endArray();
		} catch (final MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rti.getAvailable_bike_stand() + ":" + rti.getAvailable_bikes();
	}

	public void setContract(final String contract) {
		this.contract = contract;
	}

}
