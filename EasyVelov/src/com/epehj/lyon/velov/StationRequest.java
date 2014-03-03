package com.epehj.lyon.velov;

import com.epehj.lyon.velov.pojo.StationComplete;
import com.epehj.lyon.velov.tools.Globals;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class StationRequest extends SpringAndroidSpiceRequest<StationComplete> {

	private final String contract;
	private final String number;

	public StationRequest(final String contract, final String number) {
		super(StationComplete.class);
		this.contract = contract;
		this.number = number;
	}

	@Override
	public StationComplete loadDataFromNetwork() throws Exception {

		final String request = String.format(
				"https://api.jcdecaux.com/vls/v1/stations/%s?contract=%s&apiKey=%s", number,
				contract, Globals.API_KEY);
		return getRestTemplate().getForObject(request, StationComplete.class);
	}

}
