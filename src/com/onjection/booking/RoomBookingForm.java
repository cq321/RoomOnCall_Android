package com.onjection.booking;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onjection.PagerViewImageAdapter.ImageAdapter;
import com.onjection.booking.RoomDetails.GetRoomDetails;
import com.onjection.handler.ServiceHandler;
import com.onjection.handler.User_Prefrences;
import com.onjection.handler.Utilz;
import com.onjection.lazylist.ImageLoader;
import com.onjection.roomoncall.R;

@SuppressLint("ShowToast")
public class RoomBookingForm extends Activity implements OnClickListener {
	String hotelName, avilRooms, hotel_image, roomType, roomrate, numExtraBed,
			roomExtBedCharge, hotel_id, roomId;
	ImageView ivHotelImg;
	TextView tvHotelName, tvRoomType, tvRoomAvil, tvAdlts, tvRAte, tvExtraBed;
	ImageView imgplus, imgminus, imgplusAdlts, imgminusadlts, imgplusExtrabed,
			imgminusExtraBed;
	Button btnbooked, backbutton;
	Context ctx;
	ProgressDialog pd;
	String roomFacility;
	public ArrayList<String> arrRoomURL = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_room_booking_form);
		Bundle bundle = getIntent().getExtras();

		hotel_id = bundle.getString("hotel_id");
		Log.e("Hotel ID ", hotel_id);
		ctx = RoomBookingForm.this;

		/*
		 * hotelName = bundle.getString("hotelName"); avilRooms =
		 * bundle.getString("avilRooms"); hotel_image =
		 * bundle.getString("hotel_image"); roomType =
		 * bundle.getString("roomType"); numExtraBed =
		 * bundle.getString("numExtraBed"); roomExtBedCharge =
		 * bundle.getString("roomExtBedCharge"); roomrate =
		 * bundle.getString("roomrate"); roomId = bundle.getString("roomId");
		 */

		ivHotelImg = (ImageView) findViewById(R.id.imgHotel);
		imgplus = (ImageView) findViewById(R.id.imgplus);
		imgminus = (ImageView) findViewById(R.id.imgminus);
		imgplusAdlts = (ImageView) findViewById(R.id.imgplusAdlts);
		imgminusadlts = (ImageView) findViewById(R.id.imgminusAdlt);
		imgplusExtrabed = (ImageView) findViewById(R.id.imgplusExtBEd);
		imgminusExtraBed = (ImageView) findViewById(R.id.imgminusExtBEd);
		tvExtraBed = (TextView) findViewById(R.id.tvExtBed);
		tvAdlts = (TextView) findViewById(R.id.tvAdlts);
		tvHotelName = (TextView) findViewById(R.id.tvHotelName);
		tvRoomType = (TextView) findViewById(R.id.tv_RoomType);
		tvRoomAvil = (TextView) findViewById(R.id.tvRoomCount);
		tvRAte = (TextView) findViewById(R.id.tv_total);

		new GetRoomDetails().execute();

		imgplus.setOnClickListener(this);
		imgminus.setOnClickListener(this);
		imgplusAdlts.setOnClickListener(this);
		imgminusadlts.setOnClickListener(this);
		imgplusExtrabed.setOnClickListener(this);
		imgminusExtraBed.setOnClickListener(this);
		btnbooked = (Button) findViewById(R.id.btnbooked);
		backbutton = (Button) findViewById(R.id.iv_back);
		TextView tvHeader= (TextView) findViewById(R.id.headerText);
		tvHeader.setText("Booking Form");
		btnbooked.setOnClickListener(this);
		backbutton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.imgplusExtBEd:
			if (Integer.parseInt(tvExtraBed.getText().toString()) < Integer
					.parseInt(numExtraBed)) {
				tvExtraBed
						.setText(""
								+ (Integer.parseInt(tvExtraBed.getText()
										.toString()) + 1));
				tvRAte.setText(""
						+ (Float.parseFloat(roomrate)
								* (Integer.parseInt(tvRoomAvil.getText()
										.toString())) + (Float
								.parseFloat(roomExtBedCharge) * (Integer
								.parseInt(tvExtraBed.getText().toString())))));
			}

			break;
		case R.id.imgminusExtBEd:
			if (Integer.parseInt(tvExtraBed.getText().toString()) > 0) {
				tvExtraBed
						.setText(""
								+ (Integer.parseInt(tvExtraBed.getText()
										.toString()) - 1));
				tvRAte.setText(""
						+ (Float.parseFloat(roomrate)
								* (Integer.parseInt(tvRoomAvil.getText()
										.toString())) + (Float
								.parseFloat(roomExtBedCharge) * (Integer
								.parseInt(tvExtraBed.getText().toString())))));
			}

			break;

		case R.id.imgplusAdlts:
			if (Integer.parseInt(tvAdlts.getText().toString()) < 2) {
				tvAdlts.setText(""
						+ (Integer.parseInt(tvAdlts.getText().toString()) + 1));
			}

			break;
		case R.id.imgminusAdlt:
			if (Integer.parseInt(tvAdlts.getText().toString()) > 1) {
				tvAdlts.setText(""
						+ (Integer.parseInt(tvAdlts.getText().toString()) - 1));
			}

			break;
		case R.id.imgplus:
			if (Integer.parseInt(tvRoomAvil.getText().toString()) < Integer
					.parseInt(avilRooms)) {
				tvRoomAvil
						.setText(""
								+ (Integer.parseInt(tvRoomAvil.getText()
										.toString()) + 1));
				tvRAte.setText(""
						+ (Float.parseFloat(roomrate)
								* (Integer.parseInt(tvRoomAvil.getText()
										.toString())) + (Float
								.parseFloat(roomExtBedCharge) * (Integer
								.parseInt(tvExtraBed.getText().toString())))));
			}

			break;
		case R.id.imgminus:
			if (Integer.parseInt(tvRoomAvil.getText().toString()) > 1) {
				tvRoomAvil
						.setText(""
								+ (Integer.parseInt(tvRoomAvil.getText()
										.toString()) - 1));
				tvRAte.setText(""
						+ (Float.parseFloat(roomrate)
								* (Integer.parseInt(tvRoomAvil.getText()
										.toString())) + (Float
								.parseFloat(roomExtBedCharge) * (Integer
								.parseInt(tvExtraBed.getText().toString())))));
			}

			break;
		case R.id.btnbooked:

			if (new User_Prefrences(this).getUser_id() != "") {
				Intent intent = new Intent(RoomBookingForm.this,
						UserPaymentDetails.class);
				intent.putExtra("hotel_id", hotel_id);
				intent.putExtra("hotel_name", hotelName);
				intent.putExtra("extra_beds", tvExtraBed.getText().toString());
				intent.putExtra("roomtype", roomType);
				intent.putExtra("adults", tvAdlts.getText().toString());
				intent.putExtra("rooms", tvRoomAvil.getText().toString());
				intent.putExtra("Amount", tvRAte.getText().toString());
				intent.putExtra("roomId", roomId);
				startActivity(intent);

			} else {
				Toast.makeText(this, "Login Plz", 3).show();
			}

			break;
		case R.id.iv_back:
			RoomBookingForm.this.finish();

			break;

		default:
			break;
		}

	}

	public class GetRoomDetails extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(RoomBookingForm.this);
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
			Log.e("URL ", "http://www.roomoncall.in/api/get_hotels?id="
					+ hotel_id);
			Log.e("Response: booking ", "> " + jsonStr);
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

						hotelName = obj.getString("name");

						/*JSONArray jsonfacility = obj.getJSONArray("facilities");
						for (int j = 0; j < jsonfacility.length(); j++) {
							JSONObject jsonfacilityobj = jsonfacility
									.getJSONObject(j);

							roomFacility = roomFacility + ", "
									+ jsonfacilityobj.getString("name");

						}
						Log.e("room Facility", "" + roomFacility);*/
						JSONArray roomdetalis = obj.getJSONArray("rooms");
						if (roomdetalis.length() > 0) {
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
								avilRooms = roomdetalisobj
										.optString("room_count");
								roomType = roomdetalisobj
										.optString("room_type");
								roomrate = roomdetalisobj
										.optString("default_price");
								roomExtBedCharge = roomdetalisobj
										.optString("extra_bed_charge");
								/*
								 * tvExBed.setText(roomdetalisobj
								 * .optString("max_extra_beds"));
								 * tvExtBedRate.setText(roomdetalisobj
								 * .optString("extra_bed_charge") + "/Night");
								 * tvFaclity.setText(roomFacility);
								 * tvmaxAdult.setText(roomdetalisobj
								 * .optString("max_adults"));
								 * tvMaxChld.setText(roomdetalisobj
								 * .optString("max_children"));
								 * tvRate.setText(roomdetalisobj
								 * .optString("default_price") + "/Night");
								 */
								tvRoomType.setText(roomdetalisobj
										.optString("room_type"));
								/*
								 * maxRoomAvl = Integer.parseInt(roomdetalisobj
								 * .optString("room_count"));
								 * EdtRoomCount.setText("" + maxRoomAvl);
								 */
								roomId = roomdetalisobj.optString("id");

							}
						} else {
							Utilz.showAlertActivityWithTitleFinish(
									"RoomBooking", "Sorry No Room Available ",
									RoomBookingForm.this);

						}
					}
					ImageLoader imageLoader = new ImageLoader(
							RoomBookingForm.this);
					imageLoader.DisplayImage(hotel_image, ivHotelImg);
					tvRAte.setText(roomrate);
					tvRoomType.setText(roomType);
					tvHotelName.setText(hotelName);
					tvExtraBed.setText("" + 0);
					/*
					 * ImageAdapter adapter = new
					 * ImageAdapter(RoomBookingForm.this);
					 * viewPager.setAdapter(adapter);
					 */
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

		}

	}
}
