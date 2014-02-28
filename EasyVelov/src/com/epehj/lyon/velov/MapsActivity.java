package com.epehj.lyon.velov;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.epehj.lyon.velov.pojo.Station;
import com.epehj.lyon.velov.pojo.StationCompleteList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class MapsActivity extends Activity implements LocationListener, OnMarkerClickListener {
	private LocationManager locationManager;
	private GoogleMap map;
	private final String contract = "Lyon"; // à changer après
	final Map<Marker, Station> stations = new HashMap<Marker, Station>();

	protected SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

	@Override
	protected void onCreate(final Bundle SavedInstance) {
		super.onCreate(SavedInstance);
		setContentView(R.layout.activity_main);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

		map.setMyLocationEnabled(true);

		final InputStream is = getResources().openRawResource(R.raw.lyon);
		final JsonReader jr = new JsonReader(new InputStreamReader(is));
		// final List<Station> stations = new ArrayList<Station>();
		try {
			jr.beginArray();
			final Gson gson = new Gson();
			while (jr.hasNext()) {
				final Station s = (Station) gson.fromJson(jr, Station.class);
				s.setContract(contract);
				// plutot faire un dico, Marker => station
				stations.put(map.addMarker(new MarkerOptions().title(s.getName()).position(
						new LatLng(Float.parseFloat(s.getLat()), Float.parseFloat(s.getLng())))), s);

				// stations.add(s);
			}

			jr.endArray();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		map.setOnMarkerClickListener(this);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				(long) 1 * 60 * 1000, 10, this); // You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
		final Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(loc.getLatitude(), loc.getLongitude()), 15));

	}

	@Override
	public void onLocationChanged(final Location arg0) {
		// // TODO Auto-generated method stub
		// final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
		// new LatLng(arg0.getLatitude(), arg0.getLongitude()), 10);
		// map.animateCamera(cameraUpdate);
		// locationManager.removeUpdates(this);

		// apiKey=4bd22a5babb73f4cc20d3a0c1c71b13ec9d53afc
	}

	@Override
	public void onProviderDisabled(final String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(final String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(final String arg0, final int arg1, final Bundle arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMarkerClick(final Marker marker) {
		// recuperer les infos de la stations
		final Station station = stations.get(marker);

		final StationRequest request = new StationRequest(station.getContract(),
				station.getNumber());

		final String lastRequestCache = station.createCacheKey();
		// spiceManager.execute(station, lastRequestCache, DurationInMillis.ONE_MINUTE,
		// new StationRTIRequestListener());
		spiceManager.execute(request, new StationRTIRequestListener());

		marker.setSnippet(station.getName());
		return false;
	}

	// robospice

	@Override
	protected void onStart() {
		spiceManager.start(this);
		super.onStart();
	}

	@Override
	protected void onStop() {
		spiceManager.shouldStop();
		super.onStop();
	}

	// inner RequestListenerClass
	private class StationRTIRequestListener implements RequestListener<StationCompleteList> {

		@Override
		public void onRequestFailure(final SpiceException e) {
			System.out.println("request failure");
			// update your UI
		}

		@Override
		public void onRequestSuccess(final StationCompleteList rtis) {
			// update your UI
			System.out.println("request success");
		}
	}

}
