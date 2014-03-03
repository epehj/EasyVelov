package com.epehj.lyon.velov;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.epehj.lyon.velov.pojo.Station;
import com.epehj.lyon.velov.pojo.StationComplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.octo.android.robospice.GsonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

//TODO ajouter un calque avec les stations favorites qui se rafraichissent automatiquement au lancement
//TODO faire un zoom pour voir l'ensemble des stations fav et les rafraichir
public class MapsActivity extends Activity implements LocationListener, OnMarkerClickListener {
	private LocationManager locationManager;
	private GoogleMap map;

	private final String contract = "Lyon"; // TODO : a rendre modulaire
	private final Map<Marker, Station> stations = new HashMap<Marker, Station>();
	private ProgressDialog progressDial = null;

	protected SpiceManager spiceManager = new SpiceManager(GsonSpringAndroidSpiceService.class);
	private Marker selectedMarker;

	@Override
	protected void onCreate(final Bundle SavedInstance) {
		super.onCreate(SavedInstance);
		setContentView(R.layout.activity_main);
		progressDial = new ProgressDialog(this);

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
				stations.put(
						selectedMarker = map.addMarker(new MarkerOptions().title(s.getName())
								.position(
										new LatLng(Float.parseFloat(s.getLat()), Float.parseFloat(s
												.getLng())))), s);
				// refresh(s);

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
		selectedMarker = marker;
		final Station station = stations.get(marker);
		refresh(station);
		return false;
	}

	private void refresh(final Station station) {
		final StationRequest request = new StationRequest(station.getContract(),
				station.getNumber());

		// spiceManager.execute(station, lastRequestCache, DurationInMillis.ONE_MINUTE,
		// new StationRTIRequestListener());

		setProgressBarIndeterminate(false);
		setProgressBarVisibility(true);
		progressDial.setMessage("Refresh…");
		progressDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDial.show();

		// cache toujours expiré : TODO a changer puisque les données sont valables 1 min
		spiceManager.execute(request, null, DurationInMillis.ALWAYS_EXPIRED,
				new StationRTIRequestListener(selectedMarker));
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
	private class StationRTIRequestListener implements RequestListener<StationComplete> {

		private final Marker marker;

		public StationRTIRequestListener(final Marker marker) {
			this.marker = marker;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onRequestFailure(final SpiceException e) {
			System.out.println("request failure");
			progressDial.dismiss();
			// update your UI
		}

		@Override
		public void onRequestSuccess(final StationComplete station) {
			// update your UI
			System.out.println("request success");
			progressDial.dismiss();
			final int bike = Integer.parseInt(station.getAvailable_bikes());
			final int stand = Integer.parseInt(station.getAvailable_bike_stands());

			BitmapDescriptor desc = null;
			final StringBuffer bikes = new StringBuffer(bike + " bike");
			final StringBuffer stands = new StringBuffer(" " + stand + " stand");

			if (bike >= 1 && stand >= 1) {
				desc = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
				bikes.append('s');
				stands.append('s');
			} else {
				if (bike < 1) {
					// plus de velo
					desc = BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
					// desc = BitmapDescriptorFactory.fromAsset("standsnobikes.png");
				} else if (stand < 1) {
					// plus de stands
					desc = BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
					// desc = BitmapDescriptorFactory.fromAsset("bikenostands.png");
				} else {
					desc = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
				}
			}
			marker.setIcon(desc);

			marker.setSnippet(bikes.toString() + stands.toString());
			marker.hideInfoWindow();
			marker.showInfoWindow();
		}
	}

}
