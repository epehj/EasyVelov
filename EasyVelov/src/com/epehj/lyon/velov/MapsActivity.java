package com.epehj.lyon.velov;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
=======
>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c
import android.widget.Toast;

import com.epehj.lyon.velov.pojo.Station;
import com.epehj.lyon.velov.pojo.StationComplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
<<<<<<< HEAD
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
=======
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.octo.android.robospice.GsonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

//TODO ajouter un calque avec les stations favorites qui se rafraichissent automatiquement au lancement
<<<<<<< HEAD
//TODO faire un zoom pour voir l'ensemble des stations fav et les rafraichir mais sans bloquer l'UI
//TODO garder en m�moire l'�tat des markers (vert, jaune, rouge) au passage favs/all
// TODO ne pas afficher l'infoWindow de la derniere station fav refresh
// TODO pb dans la deserialisation du json, att position pas correctement pos�e

=======
//TODO faire un zoom pour voir l'ensemble des stations fav et les rafraichir
>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c
/**
 * 
 * @author e_msette
 * 
 *         TODO modifier le json global pour ajouter les stations favorites
 *         TODO utiliser le padding pour afficher un autre layout et ajouter un bouton pour afficher les fav
<<<<<<< HEAD
 */

public class MapsActivity extends Activity implements LocationListener, OnClickListener,
		ClusterManager.OnClusterClickListener<Station>,
		ClusterManager.OnClusterItemClickListener<Station>,
		ClusterManager.OnClusterItemInfoWindowClickListener<Station> {
=======
 *         TODO creer les marqueurs des fav en parall�le mais ne pas les afficher de suite.
 *         TODO utiliser map utility libs
 */

public class MapsActivity extends Activity implements LocationListener, OnMarkerClickListener,
		OnInfoWindowClickListener {
>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c
	private LocationManager locationManager;
	private GoogleMap map;
	private ClusterManager<Station> cm;

	private final String contract = "Lyon"; // TODO�: a rendre modulaire
	private final Map<Marker, Station> stations = new HashMap<Marker, Station>();
<<<<<<< HEAD
	// contient exactement stations, mais utilise les stations comme cl�s plutot que les markers.
	// utilis� pour ajouter des stations en favoris
	// en gros �a sert uniquement a faire une liste doublement chain�e, il doit y avoir plus propre

	/** un marker li� a l'id d'une station */
	private final Map<String, Marker> markers = new HashMap<String, Marker>();
	// private Map<Marker, Station> favs = null;
	/** la liste des stations favorites */
	private List<Station> favs;
=======
	private final Map<Marker, Station> favs = new HashMap<Marker, Station>();
>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c

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

		cm = new ClusterManager<Station>(this, map);
		cm.setRenderer(new StationRenderer());
		// map.setInfoWindowAdapter(cm.getMarkerManager());
		// cm.getMarkerCollection().setOnInfoWindowAdapter(new StationInfoWindowAdapter());

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				(long) 1 * 60 * 1000, 10, this); // You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
		final Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		final InputStream is = getResources().openRawResource(R.raw.lyon);
		final JsonReader jr = new JsonReader(new InputStreamReader(is));
		// final List<Station> stations = new ArrayList<Station>();
		try {

			jr.beginArray();
			final Gson gson = new Gson();
			while (jr.hasNext()) {
				final Station s = (Station) gson.fromJson(jr, Station.class);
				s.setContract(contract);
				// s.compute();
				cm.addItem(s);
				// plutot faire un dico, Marker => station
<<<<<<< HEAD
				// stations.put(
				// selectedMarker = map.addMarker(new MarkerOptions().title(s.getName())
				// .position(s.getPosition()).visible(false)), s);
				// markers.put(s.getNumber(), selectedMarker); // inutile�selectedmarker == null !!
=======
				stations.put(
						selectedMarker = map.addMarker(new MarkerOptions().title(s.getName())
								.position(
										new LatLng(Float.parseFloat(s.getLat()), Float.parseFloat(s
												.getLng())))), s);
				// initFavs();

>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c
				// refresh(s);
				// stations.add(s);
<<<<<<< HEAD
				// en faisant un refresh ici, on est pas encore pass� dans le onClusterItemRendered donc le lien stationId �<-> marker n'existe pas
				// encore
				// refreshNextToMe(loc, s);

			}// while
			jr.endArray();

			// initFavs, on pourrait le faire dans un thread � part pour all�ger l'UI
			initFavs();
			// stations est vide en fait ici, c'est pour �a que �a sert � rien de refresh�
			// refreshNextToMe(loc, stations.values());

=======
			}
			jr.endArray();

>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

<<<<<<< HEAD
=======
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				(long) 1 * 60 * 1000, 10, this); // You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
		final Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(loc.getLatitude(), loc.getLongitude()), 15));

		// Listeners
<<<<<<< HEAD
		// map.setOnMarkerClickListener(this);
		map.setOnMarkerClickListener(cm);
		map.setOnInfoWindowClickListener(cm);
		map.setOnCameraChangeListener(cm);
		cm.setOnClusterClickListener(this);
		cm.setOnClusterItemClickListener(this);
		cm.setOnClusterItemInfoWindowClickListener(this);

		final Button fav = (Button) findViewById(R.id.btn);
		fav.setTag(true);
		fav.setOnClickListener(this);

		map.setOnMapLoadedCallback(new OnMapLoadedCallback() {

			@Override
			public void onMapLoaded() {
				refreshNextToMe(loc, stations.values());

			}
		});
		map.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener() {

			@Override
			public boolean onMyLocationButtonClick() {
				refreshNextToMe(loc, stations.values());
				return false;
			}
		});
=======
		map.setOnMarkerClickListener(this);
		map.setOnInfoWindowClickListener(this);
>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c
		map.setPadding(0, 0, 30, 0);

	}

<<<<<<< HEAD
	private void refreshNextToMe(final Location loc, final Collection<Station> k) {
		System.out.println("Refreshing all");
		System.out.println("Collection size " + k.size());
		if (k != null) {
			for (final Station s : k) {
				refreshNextToMe(loc, s);
			}
		}
	}

	private void initFavs() {
		// favs = new HashMap<Marker, Station>();
		favs = new ArrayList<Station>();
		try {
			final FileInputStream fis = openFileInput(contract.toLowerCase(Locale.FRANCE)
					+ "_favs.json");
			try {
				final JsonReader jr = new JsonReader(new InputStreamReader(fis));
				final Gson gson = new Gson();
				jr.beginArray();
				while (jr.hasNext()) {
					final Station station = gson.fromJson(jr, Station.class);
					// final String idStation = station.getNumber();
					// favs.put(markers.get(idStation), station);
					favs.add(station);
				}
				jr.endArray();

			} finally {
				fis.close();
			}
		} catch (final FileNotFoundException e) {
			// create file
			// c'est mal d'utiliser des exceptions pour faire des if/else
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	private void refreshNextToMe(final Location loc, final Station s) {
		final Double distance = SphericalUtil.computeDistanceBetween(new LatLng(loc.getLatitude(),
				loc.getLongitude()), s.getPosition());
		if (distance < 1000) {
			System.out.println("NEXT�TO�ME�: " + s.getName());
			refresh(s);
		}
=======
	// sur quit de l'app, sauvegarde en json des favs
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c
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

	// @Override
	// public boolean onMarkerClick(final Marker marker) {
	// // recuperer les infos de la station
	// selectedMarker = marker; // je pense que c'est bien inutile
	// final Station station = stations.get(marker);
	// refresh(station);
	// return false;
	// }

	private void refresh(final Station station) {
		// je peux peut etre passer direct la station en param
		final StationRequest request = new StationRequest(station.getContract(),
<<<<<<< HEAD
				station.getNumber(), station.getPosition());
=======
				station.getNumber());
>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c

		setProgressBarIndeterminate(false);
		setProgressBarVisibility(true);
		progressDial.setMessage("Refresh�");
		progressDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDial.show();

		final String cache = request.createCacheKey();
		// cache toujours expir� : TODO a changer puisque les donn�es sont valables 1 min
		spiceManager.execute(request, cache, DurationInMillis.ONE_MINUTE,
				new StationRTIRequestListener());
	}

	// robospice

	@Override
	protected void onStart() {
		spiceManager.start(this);
		final Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		refreshNextToMe(loc, stations.values());
		super.onStart();
	}

	/**
	 * serialization des favs
	 */
	@Override
	protected void onStop() {
		spiceManager.shouldStop();
		final Gson gson = new Gson();
		// final Collection<Station> ls = favs.values();

		// final String json = gson.toJson(ls);
		final String json = gson.toJson(favs);
		try {
			// creation du fichier
			final FileOutputStream fos = openFileOutput(contract.toLowerCase(Locale.FRANCE)
					+ "_favs.json", MODE_PRIVATE);
			try {
				fos.write(json.getBytes());
			} finally {
				fos.close();
			}
		} catch (final FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onStop();
	}

	// sur clic, on place en fav
<<<<<<< HEAD
	// les stations peuvent �tre en double, je pense que c'est li� aux markers pendant qu'on fav
	// on vient de clicker sur une station, alors qu'on voyait tous les markers
	// -> il faut ajouter le marker de stations et la station, et pas un nouveau marker
	// -> dans l'init des favs, il faut reprendre le marker correspondant � ce fav l�
	/*
	 * Faire une double map, avec comme cl� les stations et comme valeur le marqueurs associ�. C'est ce marqueur que l'on utilisera pour ajouter dans
	 * ler favs
	 * les clusters foutent en l'air l'affichage des favs
	 */
	@Override
	public void onClusterItemInfoWindowClick(final Station arg0) {
		// dans le cas on ou ne voit que les favs, et qu'on click sur la infowindow
		// if (favs.get(arg0) == null) {
		// favs enregistr�s,station pr�sente mais non reconnue (ref de station !=, mais id identique�)
		if (!favs.contains(arg0)) {
			// favs.put(arg0);
			favs.add(arg0);
=======
	@Override
	public void onInfoWindowClick(final Marker arg0) {
		if (favs.get(arg0) == null) {
			favs.put(arg0, stations.get(arg0));
>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c
			Toast.makeText(getApplicationContext(), "Fav added",
					(int) DurationInMillis.ONE_SECOND * 2).show();
		} else {
			favs.remove(arg0);
<<<<<<< HEAD
			// arg0.setVisible(false);
=======
>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c
			Toast.makeText(getApplicationContext(), "Fav removed",
					(int) DurationInMillis.ONE_SECOND * 2).show();
		}
	}

<<<<<<< HEAD
	// public class StationInfoWindowAdapter implements InfoWindowAdapter {
	//
	// @Override
	// public View getInfoContents(final Marker marker) {
	//
	// return null;
	// }
	//
	// @Override
	// public View getInfoWindow(final Marker marker) {
	// marker.setTitle(selectedItem.getName());
	// marker.setSnippet(selectedItem.getAvailable_bikes());
	// // TODO Auto-generated method stub
	// return null;
	// }
	// }

	public class StationRenderer extends DefaultClusterRenderer<Station> {

		public StationRenderer() {
			super(getApplicationContext(), map, cm);
		}
=======
	// inner RequestListenerClass
	private class StationRTIRequestListener implements RequestListener<StationComplete> {
>>>>>>> 11e4ea5e96f85a3732e6eb046733d6a5252be30c

		@Override
		protected void onBeforeClusterItemRendered(final Station item,
				final MarkerOptions markerOptions) {
			// TODO Auto-generated method stub
			super.onBeforeClusterItemRendered(item, markerOptions);

		}

		@Override
		protected void onClusterItemRendered(final Station station, final Marker marker) {
			super.onClusterItemRendered(station, marker);
			// pour faire le lien marker-station et pouvoir travailler sur le marker si evenenement
			stations.put(marker, station);
			markers.put(station.getNumber(), marker);
			marker.setTitle(station.getName());
			// marker.setSnippet(selectedItem.getAvailable_bikes() + "bike(s) avalaible");
		}

	}

	// inner RequestListenerClass
	private class StationRTIRequestListener implements RequestListener<StationComplete> {

		// private Marker marker;
		//
		// public StationRTIRequestListener(final Marker marker) {
		// this.marker = marker;
		// }

		@Override
		public void onRequestFailure(final SpiceException e) {
			System.out.println("request failure");
			progressDial.dismiss();
			Toast.makeText(getApplicationContext(), "Could not refresh station infos",
					(int) DurationInMillis.ONE_SECOND * 5).show();
			// update your UI
		}

		@Override
		public void onRequestSuccess(final StationComplete station) {
			// update your UI
			System.out.println("request success");
			progressDial.dismiss();
			final Marker marker = markers.get(station.getNumber());

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

			final Location loc = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			final double dist = SphericalUtil.computeDistanceBetween(new LatLng(loc.getLatitude(),
					loc.getLongitude()), station.getPosition());
			marker.setSnippet(bikes.toString() + stands.toString() + " @ " + dist + " meters");
			marker.hideInfoWindow();
			marker.showInfoWindow();
			marker.setVisible(true);
		}
	}

	/**
	 * si button.getTag() == true alors affiche tous les markers
	 * TODO cacher les clusters quand on affiche les favoris !
	 */
	@Override
	public void onClick(final View arg0) {
		final Button button = (Button) findViewById(R.id.btn);
		if ((Boolean) button.getTag()) {
			button.setText("All");
			// je suis sur qu'on peut faire autrement que boucler sur toutes les stations
			// ne pas afficher les clusters quand affichage des favoris
			/*
			 * probleme avec la vue fav et les clusters
			 * si on est en train de regarder les favs et que l'on dezoom pour voir les clusters, et que l'on rezoom, on perds l'affichage des favs et
			 * on repasse en mode on voit toutes les stations
			 */

			if (favs.size() > 0) {
				cm.clearItems();
				// for (final Marker m : cm.getClusterMarkerCollection().getMarkers()) {
				// m.setVisible(false);
				// }
				// on efface toutes les stations
				// for (final Marker m : stations.keySet()) {
				// m.setVisible(false);
				// }
				// on affiche explicitement les stations qui sont en favs
				// for (final Station s : favs) {
				cm.addItems(favs);
				// final Marker m = markers.get(s.getNumber());
				// m.setVisible(true);
				// }

				// for (final String stationNumber : markers.keySet()) {
				// final Marker m = markers.get(stationNumber);
				// if (m != null) {
				// final Station s = stations.get(m);
				// if (s != null) {
				// if (favs.contains(s)) {
				// m.setVisible(true);
				// } else {
				// m.setVisible(false);
				// }
				// } else {
				// // vide = station clusteris�e je pense
				// System.out.println("vide");
				// }
				// }
				// }
				// creer un zoom pour ne voir toutes les stations favs
				final LatLngBounds.Builder builder = LatLngBounds.builder();
				for (final Station s : favs) {
					builder.include(s.getPosition());
					refresh(s);
				}
				final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(builder.build(), 10);
				map.animateCamera(cu);

			} else {
				Toast.makeText(this, "No favs", (int) DurationInMillis.ONE_SECOND * 3).show();
			}
		} else {
			cm.clearItems();
			cm.addItems(stations.values());
			// for (final Marker m : cm.getClusterMarkerCollection().getMarkers()) {
			// m.setVisible(true);
			// }
			// for (final Marker m : stations.keySet()) {
			// m.setVisible(true);
			// }
			button.setText("Fav");

		}

		// for (final Marker m : stations.keySet()) {
		// if (!favs.contains(markers.get(m))) {
		// m.setVisible(false);
		// }
		// }

		button.setTag(!(Boolean) button.getTag());
		// ne plus voir les clusters de markers quand on affiche les favoris
		// for (final Marker m : stations.keySet()) {
		// m.setVisible(!m.isVisible());
		// }
		// Marker m;
		// for (final Station station : favs) {
		// m = markers.get(station.getNumber());
		// m.setVisible(!m.isVisible());
		// }
		cm.cluster();

	}

	@Override
	public boolean onClusterClick(final Cluster<Station> cluster) {
		System.out.println("CLUSTER�CLICK");
		// sur clic d'un cluster faire un zoom camera
		// final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(cluster.getPosition());
		final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(cluster.getPosition(),
				map.getCameraPosition().zoom + 1);
		// map.animateCamera(CameraUpdateFactory.zoomIn());
		map.animateCamera(cameraUpdate);
		return true;
	}

	@Override
	public boolean onClusterItemClick(final Station item) {
		refresh(item);
		return false;
	}

}
