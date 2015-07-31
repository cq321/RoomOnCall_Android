package com.onjection.map;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.onjection.parser.JSONParser;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

class GeocoderTask extends AsyncTask<String, Void, List<Address>> {
	// GoogleMap googleMap;
	// LatLng latLng;
	MarkerOptions markerOptions;
	Context ctx;
	String addressText;
	JSONArray results = null;
	ProgressDialog progressDialog;
	LatLng latLng2;

	public GeocoderTask(Context ctx) {
		// TODO Auto-generated constructor stub
		this.ctx = ctx;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		progressDialog = ProgressDialog.show(ctx, "Fetching Location...",
				"Loading...");
		progressDialog.setCancelable(true);
	}

	@Override
	protected List<Address> doInBackground(String... locationName) {
		// Creating an instance of Geocoder class

		Geocoder geocoder = new Geocoder(ctx);
		List<Address> addresses = null;

		// Getting a maximum of 3 Address that matches the input text
		try {
			addresses = geocoder.getFromLocationName(locationName[0], 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (addresses == null || addresses.size() == 0) {
			addressText = locationName[0];
			latLng2 = getLAtLongFromJson(locationName[0]);
		}

		return addresses;
	}

	@Override
	protected void onPostExecute(List<Address> addresses) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
		if (addresses == null || addresses.size() == 0) {
			if (null != HotelAddressOnMap.googleMap && latLng2 != null) {

				HotelAddressOnMap.googleMap.clear();
				HotelAddressOnMap.googleMap.setMyLocationEnabled(true);
				HotelAddressOnMap.googleMap.moveCamera(CameraUpdateFactory
						.newLatLng(latLng2));
				HotelAddressOnMap.googleMap.animateCamera(CameraUpdateFactory
						.zoomTo(6));
				HotelAddressOnMap.googleMap.addMarker(new MarkerOptions()
						.position(latLng2).title(addressText).draggable(true));
			}
		} else {
			for (int i = 0; i < addresses.size(); i++) {

				Address address = (Address) addresses.get(i);

				// Creating an instance of GeoPoint, to display in Google Map
				LatLng latLng = new LatLng(address.getLatitude(),
						address.getLongitude());

				addressText = String.format(
						"%s, %s",
						address.getMaxAddressLineIndex() > 0 ? address
								.getAddressLine(0) : "", address
								.getCountryName());

				if (null != HotelAddressOnMap.googleMap) {
					HotelAddressOnMap.googleMap.clear();
					HotelAddressOnMap.googleMap.setMyLocationEnabled(true);
					HotelAddressOnMap.googleMap.moveCamera(CameraUpdateFactory
							.newLatLng(latLng));
					HotelAddressOnMap.googleMap
							.animateCamera(CameraUpdateFactory.zoomTo(4));
					HotelAddressOnMap.googleMap.addMarker(new MarkerOptions()
							.position(latLng).title(addressText)
							.draggable(true));

				}

				if (latLng != null) {
					break;
				}

			}
		}
	}

	private LatLng getLAtLongFromJson(String address) {
		JSONParser jParser = new JSONParser();

		LatLng lng2 = null;
		String url = "https://maps.googleapis.com/maps/api/geocode/json?";
		try {
			// encoding special characters like space in the user input place
			address = URLEncoder.encode(address, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String newaddress = "address=" + address;
		String sensor = "sensor=false";
		url = url + newaddress + "&" + sensor;

		JSONObject jsonObject = jParser.getJSONFromURL(url);

		try {
			// results = jsonObject.getJSONArray("results");
			//
			// // for (int i = 0; i < results.length(); i++) {
			// JSONObject r = results.getJSONObject(0);

			double lng = ((JSONArray) jsonObject.get("results"))
					.getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location").getDouble("lng");

			double lat = ((JSONArray) jsonObject.get("results"))
					.getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location").getDouble("lat");

			lng2 = new LatLng(lat, lng);

			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lng2;
	}
}
