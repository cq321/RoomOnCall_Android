package com.onjection.roomoncall;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.onjection.adapter.AllHotelListAdapter;
import com.onjection.adapter.hotelroomdetails;
import com.onjection.booking.RoomDetails;
import com.onjection.handler.ServiceHandler;
import com.onjection.lazylist.ImageLoader;
import com.onjection.map.HotelAddressOnMap;
import com.onjection.model.HotelData;
import com.onjection.model.LocationInfo;
import com.onjection.model.Roomdetails;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HotelDescription extends Activity {
	private ListView listView;
	ArrayList<Roomdetails> roomdeatilsinfo = new ArrayList<Roomdetails>();

	Context thiscontext;
	String hotel_id;
	String phone, email, stars, lowest_price, name, address, hotel_image,
			agent_commision;
	TextView txtemail, txthotelname, txtphone, txthoteldefaultrate,
			txthotelagent_commision, hotelStarsid, txthoteladdress,
			txtfacilities, txtDetailsfacilities;
	private ProgressDialog pd;
	ImageView fulllimagehotel;
	private hotelroomdetails adapter;

	ListView mainListView;
	Button backbutton, btnbook;
	String Facility_Of_hotel = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.hotel_description);
		thiscontext = HotelDescription.this;
		Bundle bundle = getIntent().getExtras();
		newcontent();
		hotel_id = bundle.getString("hotel_id");
		String udata = "Address";
		SpannableString content = new SpannableString(udata);
		content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
		txthoteladdress.setText(content);
		new GetSearch().execute();
		/*
		 * ArrayList<String> facilitylist = new ArrayList<String>();
		 * facilitylist.addAll(Arrays.asList(Facility_Of_hotel));
		 */

		// Create ArrayAdapter using the planet list.

		/*
		 * listAdapter = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, Facility_Of_hotel);
		 * mainListView.setAdapter(listAdapter);
		 * backbutton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) {
		 * HotelDescription.this.finish(); } });
		 */
		txthoteladdress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent ihoyeldescrption = new Intent(HotelDescription.this,
						HotelAddressOnMap.class);
				ihoyeldescrption.putExtra("adrress", address);
				startActivity(ihoyeldescrption);
			}
		});

		btnbook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentbookdetails = new Intent(HotelDescription.this,
						RoomDetails.class);

				intentbookdetails.putExtra("hotel_id", hotel_id);
				startActivity(intentbookdetails);

			}
		});
		backbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HotelDescription.this.finish();
			}
		});
	}

	private void newcontent() {
		txthoteladdress = (TextView) findViewById(R.id.txthoteladdress);
		txtphone = (TextView) findViewById(R.id.txtphone);
		txthotelname = (TextView) findViewById(R.id.txthotelname);
		fulllimagehotel = (ImageView) findViewById(R.id.imghotel);
		txtemail = (TextView) findViewById(R.id.txtemail);
		backbutton = (Button) findViewById(R.id.iv_back);
		TextView tvHeader= (TextView) findViewById(R.id.headerText);
		tvHeader.setText("Hotel Discription");
		btnbook = (Button) findViewById(R.id.btnbooked);
		txtDetailsfacilities = (TextView) findViewById(R.id.txtDetailsfacilities);
		listView = (ListView) findViewById(R.id.listView1);
	}

	public class GetSearch extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(HotelDescription.this);
			pd.setMessage("Please wait...");
			pd.setCancelable(false);
			pd.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			ServiceHandler sh = new ServiceHandler();
			
			String jsonStr = sh.makeServiceCall(
//					http://dev.onjection.com/roc/api/get_hotels&id=38
					"http://www.roomoncall.in/api/get_hotels&id="
							+ hotel_id, ServiceHandler.GET);
			Log.d("Response: ", "> " + jsonStr);
			return jsonStr;
		}

		@Override
		protected void onPostExecute(String result) {
			if (pd.isShowing())
				pd.dismiss();
//			 Log.e("url", "http://dev.onjection.com/roc/api/get_hotels?id="
//						+ hotel_id);
			if (result != null) {
				@SuppressWarnings("unused")
				JSONArray jsonArray = null;
				try {
					jsonArray = new JSONArray(result);

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						hotel_image = obj.getString("hotel_image");
						phone = obj.getString("phone");
						email = obj.getString("email");
						stars = obj.getString("stars");
						address = obj.getString("address");
						lowest_price = obj.getString("lowest_price");
						name = obj.getString("name");
						agent_commision = obj.getString("agent_commision");

						JSONArray jsonfacility = obj.getJSONArray("facilities");
						for (int j = 0; j < jsonfacility.length(); j++) {
							JSONObject jsonfacilityobj = jsonfacility
									.getJSONObject(j);

							Facility_Of_hotel = Facility_Of_hotel + ", "
									+ jsonfacilityobj.getString("name");

							// Facility_Of_hotel[j] = jsonfacilityobj
							// .getString("name");
							// Log.d("Msg", Facility_Of_hotel[j]);
						}
						Log.e("facility", "" + Facility_Of_hotel);
						JSONArray roomdetalis = obj.getJSONArray("rooms");
						for (int k = 0; k < roomdetalis.length(); k++) {
							JSONObject roomdetalisobj = roomdetalis
									.getJSONObject(k);

							Roomdetails roommodel = new Roomdetails(
									obj.getString("id"),
									roomdetalisobj.getString("room_type"),
									roomdetalisobj.getString("room_count"),
									roomdetalisobj.getString("default_price"));
							roomdeatilsinfo.add(roommodel);
						}
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			ImageLoader im = new ImageLoader(HotelDescription.this);
			im.DisplayImage(hotel_image, fulllimagehotel);
			txthotelname.setText(name);
			txtphone.setText(phone);
			txtemail.setText(email);
			txtDetailsfacilities.setText(Facility_Of_hotel);
			txthoteladdress.setText(address);
			adapter = new hotelroomdetails(HotelDescription.this,
					roomdeatilsinfo);
			listView.setAdapter(adapter);
			/*
			 * adapter = new CustomListAdapter(HotelDescription.this,
			 * hoteldatalist); listView.setAdapter(adapter);
			 */

		}

	}

}
