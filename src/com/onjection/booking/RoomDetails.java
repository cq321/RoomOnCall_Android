package com.onjection.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.onjection.PagerViewImageAdapter.ImageAdapter;
import com.onjection.handler.ServiceHandler;
import com.onjection.handler.User_Prefrences;
import com.onjection.roomoncall.R;

public class RoomDetails extends Activity implements OnClickListener {
	ImageView imgplus, imgminus, imghotel ;
	TextView EdtRoomCount, tvmaxAdult, tvMaxChld, tvExBed, tvRoomType, tvRate,
			tvExtBedRate, tvFaclity;
	Spinner SpinearRoomTypes;
	Button btnbooked, backbutton;
	String hotel_id;
	Timer swipeTime;
	Context ctx;
	int currentPage;
	String hotelName, avilRooms, hotel_image, roomType, roomrate, numExtraBed,
			roomExtBedCharge, roomId;
	int maxRoomAvl = 1;
	String roomFacility;
	public ArrayList<String> arrRoomURL = new ArrayList<String>();
	String[] roomtype = new String[] { "Double", "Single", "Luxury",
			"Standard Room" };
	ProgressDialog pd;
	ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.room_details);
		Bundle bundle = getIntent().getExtras();

		hotel_id = bundle.getString("hotel_id");
		Log.e("Hotel ID ", hotel_id);
		ctx = RoomDetails.this;
		imgplus = (ImageView) findViewById(R.id.imgplus);
		imgminus = (ImageView) findViewById(R.id.imgminus);
		// imghotel = (ImageView) findViewById(R.id.imghotel);
		viewPager = (ViewPager) findViewById(R.id.view_pager);

		EdtRoomCount = (TextView) findViewById(R.id.EdtRoomCount);
		// SpinearRoomType = (Spinner) findViewById(R.id.SpinearRoomType);
		btnbooked = (Button) findViewById(R.id.btnbooked);
		backbutton = (Button) findViewById(R.id.iv_back);
		TextView tvHeader= (TextView) findViewById(R.id.headerText);
		tvHeader.setText("Room Details");
		spinearRoomType();
		imgplus.setOnClickListener(this);
		imgminus.setOnClickListener(this);
		btnbooked.setOnClickListener(this);
		backbutton.setOnClickListener(this);
		tvExBed = (TextView) findViewById(R.id.tv_extBed);
		tvExtBedRate = (TextView) findViewById(R.id.tv_extBedCharge);
		tvFaclity = (TextView) findViewById(R.id.tvFacilities);
		tvmaxAdult = (TextView) findViewById(R.id.tv_maxAdlt);
		tvMaxChld = (TextView) findViewById(R.id.tv_maxChld);
		tvRate = (TextView) findViewById(R.id.tv_Rate);
		tvRoomType = (TextView) findViewById(R.id.tv_RoomType);
		new GetRoomDetails().execute();
		final Handler handler = new Handler();

        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == arrRoomURL.size() ) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        swipeTime = new Timer();
        swipeTime.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 5000);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgplus:
			if (Integer.parseInt(EdtRoomCount.getText().toString()) < maxRoomAvl) {
				EdtRoomCount
						.setText(""
								+ (Integer.parseInt(EdtRoomCount.getText()
										.toString()) + 1));
			}

			break;
		case R.id.imgminus:
			if (Integer.parseInt(EdtRoomCount.getText().toString()) > 1) {
				EdtRoomCount
						.setText(""
								+ (Integer.parseInt(EdtRoomCount.getText()
										.toString()) - 1));
			}

			break;
		case R.id.btnbooked:

			if (new User_Prefrences(this).getUser_id() != "") {
				Intent intent = new Intent(RoomDetails.this,
						RoomBookingForm.class);
//				hotelName, avilRooms, hotel_image, roomType, roomrate, numExtraBed,
//				roomExtBedCharge;
				intent.putExtra("hotel_id", hotel_id);
				intent.putExtra("hotelName", hotelName);
				intent.putExtra("avilRooms", avilRooms);
				intent.putExtra("hotel_image", hotel_image);
				intent.putExtra("roomType", roomType);
				intent.putExtra("roomrate", roomrate); 
				intent.putExtra("numExtraBed", numExtraBed);
				intent.putExtra("roomExtBedCharge", roomExtBedCharge);
				intent.putExtra("roomId",roomId);
				 startActivity(intent);

				// Intent intentbooked = new Intent(this,
				// UserPaymentDetails.class);
				// startActivity(intentbooked);
			} else {
				Toast.makeText(this, "Login Plz", 3).show();
			}

			break;
		case R.id.iv_back:
			RoomDetails.this.finish();

			break;

		default:
			break;
		}
		// TODO Auto-generated method stub

	}

	private void spinearRoomType() {

		List<String> lables = new ArrayList<String>();
		lables.add("RoomType");
		for (int i = 0; i < roomtype.length; i++) {
			lables.add(roomtype[i]);
		}

		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ctx,
				android.R.layout.simple_dropdown_item_1line, lables) {
			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {

				View v = null;

				if (position == 0) {
					TextView tv = new TextView(ctx);
					tv.setHeight(0);
					tv.setVisibility(View.GONE);
					v = tv;
				} else {

					v = super.getDropDownView(position, null, parent);
				}

				parent.setVerticalScrollBarEnabled(false);
				return v;
			}
		};

		// SpinearRoomType.setAdapter(spinnerAdapter);
		// SpinearRoomType.setOnItemSelectedListener(typeadultlistnear);

	}

	private OnItemSelectedListener typeadultlistnear = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	};

	public class GetRoomDetails extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(RoomDetails.this);
			pd.setMessage("Please wait...");
			pd.setCancelable(false);
			pd.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			ServiceHandler sh = new ServiceHandler();
			String jsonStr = sh.makeServiceCall(
					"http://www.roomoncall.in/api/get_hotels?id="
							+ hotel_id, ServiceHandler.GET);
			Log.e("URL", "http://www.roomoncall.in/api/get_hotels?id="
					+ hotel_id);
			Log.e("Response: Room details booking ", "> " + jsonStr);
			return jsonStr;
		}

		@Override
		protected void onPostExecute(String result) {
			if (pd.isShowing())
				pd.dismiss();
			if (result != null) {
				@SuppressWarnings("unused")
				JSONArray jsonArray = null;
				try {
					jsonArray = new JSONArray(result);

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						hotel_image = obj.optString("hotel_image");
						// phone = obj.getString("phone");
						// email = obj.getString("email");
						// stars = obj.getString("stars");
						// address = obj.getString("address");
						// lowest_price = obj.getString("lowest_price");
						hotelName = obj.getString("name");
						// agent_commision = obj.getString("agent_commision");

						JSONArray jsonfacility = obj.getJSONArray("facilities");
						for (int j = 0; j < jsonfacility.length(); j++) {
							JSONObject jsonfacilityobj = jsonfacility
									.getJSONObject(j);

							roomFacility = roomFacility + ", "
									+ jsonfacilityobj.getString("name");

							// Facility_Of_hotel[j] = jsonfacilityobj
							// .getString("name");
							// Log.d("Msg", Facility_Of_hotel[j]);
						}
						Log.e("room Facility", "" + roomFacility);
						JSONArray roomdetalis = obj.getJSONArray("rooms");
						for (int k = 0; k < roomdetalis.length(); k++) {
							JSONObject roomdetalisobj = roomdetalis
									.getJSONObject(k);
							arrRoomURL.add(roomdetalisobj
									.optString("room_picture_1"));
							arrRoomURL.add(roomdetalisobj
									.optString("room_picture_2"));
							arrRoomURL.add(roomdetalisobj
									.optString("room_picture_3"));
						 
							numExtraBed = roomdetalisobj
									.optString("max_extra_beds");
							avilRooms = roomdetalisobj.optString("room_count");
							roomType = roomdetalisobj.optString("room_type");
							roomrate = roomdetalisobj
									.optString("default_price");
							roomExtBedCharge = roomdetalisobj
									.optString("extra_bed_charge");
							tvExBed.setText(roomdetalisobj
									.optString("max_extra_beds"));
							tvExtBedRate.setText(roomdetalisobj
									.optString("extra_bed_charge") + "/Night");
							tvFaclity.setText(roomFacility);
							tvmaxAdult.setText(roomdetalisobj
									.optString("max_adults"));
							tvMaxChld.setText(roomdetalisobj
									.optString("max_children"));
							tvRate.setText(roomdetalisobj
									.optString("default_price") + "/Night");
							tvRoomType.setText(roomdetalisobj
									.optString("room_type"));
							maxRoomAvl = Integer.parseInt(roomdetalisobj
									.optString("room_count"));
							EdtRoomCount.setText("" + maxRoomAvl);
							roomId=roomdetalisobj
							.optString("id");
							// Roomdetails roommodel = new Roomdetails(
							// obj.getString("id"),
							// roomdetalisobj.getString("room_type"),
							// roomdetalisobj.getString("room_count"),
							// roomdetalisobj.getString("default_price"));
							// roomdeatilsinfo.add(roommodel);
						}
					}
					ImageAdapter adapter = new ImageAdapter(RoomDetails.this, arrRoomURL);
					viewPager.setAdapter(adapter);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			// ImageLoader im = new ImageLoader(HotelDescription.this);
			// im.DisplayImage(hotel_image, fulllimagehotel);
			// txthotelname.setText(name);
			// txthoteldefaultrate.setText(lowest_price);
			// txtphone.setText(phone);
			// txtemail.setText(email);
			// txtDetailsfacilities.setText(Facility_Of_hotel);
			// txthotelagent_commision.setText(agent_commision);
			// adapter = new hotelroomdetails(HotelDescription.this,
			// roomdeatilsinfo);
			// listView.setAdapter(adapter);
			/*
			 * adapter = new CustomListAdapter(HotelDescription.this,
			 * hoteldatalist); listView.setAdapter(adapter);
			 */

		}

	}
}
