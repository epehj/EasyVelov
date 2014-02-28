package com.epehj.lyon.velov;

import com.epehj.lyon.velov.pojo.StationCompleteList;
import com.epehj.lyon.velov.tools.Globals;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class StationRequest extends SpringAndroidSpiceRequest<StationCompleteList> {

	private final String contract;
	private final String number;

	public StationRequest(final String contract, final String number) {
		super(StationCompleteList.class);
		this.contract = contract;
		this.number = number;
	}

	@Override
	public StationCompleteList loadDataFromNetwork() throws Exception {

		final String request = String.format(
				"https://api.jcdecaux.com/vls/v1/stations/%s?contract=%s&apiKey=%s", number,
				contract, Globals.API_KEY);
		return getRestTemplate().getForObject(request, StationCompleteList.class);
	}

}
