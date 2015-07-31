package com.onjection.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.onjection.map.*;
import com.onjection.roomoncall.R;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class HotelAddressOnMap extends android.support.v4.app.FragmentActivity {
	String adrress;
	TextView mapaddress;
	public static GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.hotel_address_on_map);

		Bundle bundle = getIntent().getExtras();

		adrress = bundle.getString("adrress");
		mapaddress = (TextView) findViewById(R.id.mapaddress);
		mapaddress.setText(adrress);

		try {

			SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			if (googleMap != null)
				googleMap = null;

			// Getting a reference to the map
			googleMap = supportMapFragment.getMap();
		} catch (Exception e) {

		}
		if (adrress != null && !adrress.equals("")) {

			new GeocoderTask(HotelAddressOnMap.this).execute(adrress);
		}
	}

}
