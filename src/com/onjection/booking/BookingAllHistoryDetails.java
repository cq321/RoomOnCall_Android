package com.onjection.booking;

import com.onjection.roomoncall.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BookingAllHistoryDetails extends Activity {
	String HotelName, status, rooms, room_numbers, room_id, roomtype, price,
			payment_type, payment_sum, order_price, meal_plan_price,
			meal_plan_id, id, hotel_id, hotel, extras_fee, extras,
			extra_beds_charge, extra_beds, discount_percent, created_date,
			children, checkout, checkin, booking_number, adults;
	TextView txtHotelName, txtHotelID, txtRoomType, txtroomID, txtNoOfRoom,
			txBookingId, txtTotalAmount, txtFromDate, txtTodate, txtAdults,
			txtChildren, txtExtraBed;
	Button backbutton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.user_booking_details);
		initiateElements();
		Bundle bundle = getIntent().getExtras();
		HotelName = bundle.getString("HotelName");
		status = bundle.getString("status");
		rooms = bundle.getString("rooms");
		room_numbers = bundle.getString("room_numbers");
		room_id = bundle.getString("room_id");
		roomtype = bundle.getString("roomtype");
		price = bundle.getString("price");
		payment_type = bundle.getString("payment_type");
		payment_sum = bundle.getString("payment_sum");
		order_price = bundle.getString("order_price");
		meal_plan_price = bundle.getString("meal_plan_price");
		meal_plan_id = bundle.getString("meal_plan_id");
		hotel_id = bundle.getString("hotel_id");
		extras_fee = bundle.getString("extras_fee");
		extras = bundle.getString("extras");
		extra_beds_charge = bundle.getString("extra_beds_charge");
		extra_beds = bundle.getString("extra_beds");
		discount_percent = bundle.getString("discount_percent");
		created_date = bundle.getString("created_date");
		children = bundle.getString("children");
		checkout = bundle.getString("checkout");
		checkin = bundle.getString("checkin");
		booking_number = bundle.getString("booking_number");
		adults = bundle.getString("adults");

		txtExtraBed.setText(extra_beds);
		txtHotelID.setText(hotel_id);
		txtRoomType.setText(roomtype);
		txtroomID.setText(room_id);
		txtNoOfRoom.setText(room_numbers);
		txBookingId.setText(booking_number);
		txtFromDate.setText(checkin);
		txtTodate.setText(checkout);
		txtAdults.setText(adults);
		txtChildren.setText(children);
		txtHotelName.setText(HotelName);
		txtTotalAmount.setText(payment_sum);

	}

	void initiateElements() {
		txtExtraBed = (TextView) findViewById(R.id.txtExtraBed);
		txtHotelID = (TextView) findViewById(R.id.txtHotelID);
		txtRoomType = (TextView) findViewById(R.id.txtRoomType);
		txtroomID = (TextView) findViewById(R.id.txtroomID);
		txtHotelName = (TextView) findViewById(R.id.txtHotelName);
		txtNoOfRoom = (TextView) findViewById(R.id.txtNoOfRoom);
		txBookingId = (TextView) findViewById(R.id.txBookingId);
		txtTotalAmount = (TextView) findViewById(R.id.txtTotalAmount);
		txtFromDate = (TextView) findViewById(R.id.txtFromDate);
		txtTodate = (TextView) findViewById(R.id.txtTodate);
		txtAdults = (TextView) findViewById(R.id.txtAdults);
		txtChildren = (TextView) findViewById(R.id.txtChildren);
		backbutton = (Button) findViewById(R.id.iv_back);
		backbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BookingAllHistoryDetails.this.finish();

			}
		});
		TextView tvHeader = (TextView) findViewById(R.id.headerText);
		tvHeader.setText("Booking Details");

	}

}
