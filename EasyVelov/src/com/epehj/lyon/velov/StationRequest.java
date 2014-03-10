package com.epehj.lyon.velov;

import com.epehj.lyon.velov.pojo.StationComplete;
import com.epehj.lyon.velov.tools.Globals;
import com.google.android.gms.maps.model.LatLng;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class StationRequest extends SpringAndroidSpiceRequest<StationComplete> {

	private final String contract;
	private final String number;
	private final LatLng position;

	public StationRequest(final String contract, final String number, final LatLng pos) {
		super(StationComplete.class);
		this.contract = contract;
		this.number = number;
		this.position = pos;
	}

	@Override
	public StationComplete loadDataFromNetwork() throws Exception {

		final String request = String.format(
				"https://api.jcdecaux.com/vls/v1/stations/%s?contract=%s&apiKey=%s", number,
				contract, Globals.API_KEY);
		final StationComplete object = getRestTemplate().getForObject(request,
				StationComplete.class);
		object.setPosition(position); // je comprends pas pourquoi il désérialize pas la pos lat/lng
		return object;
	}

	public String createCacheKey() {
		return contract + number + position;
	}

}
