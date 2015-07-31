package com.onjection.roomoncall;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import serverTask.ServerDownload;
import serverTask.ServerResponse;

import com.onjection.adapter.BookingHistoryAdpter;
import com.onjection.booking.BookingAllHistoryDetails;
import com.onjection.handler.User_Prefrences;
import com.onjection.model.BookingHistoryDetails;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MyAccount extends Fragment implements ServerResponse {
	Context thiscontext;
	Button btnfacebooklogin, btngoogleplus, btnroomancallacount;
	ProgressDialog pd;
	User_Prefrences user_Prefrences;
	private BookingHistoryAdpter adapter;
	private List<BookingHistoryDetails> bookinghistory = new ArrayList<BookingHistoryDetails>();
	ListView listhistorydetails;
	String HotelName, status, rooms, room_numbers, room_id, roomtype, price,
			payment_type, payment_sum, order_price, meal_plan_price,
			meal_plan_id, id, hotel_id, hotel, extras_fee, extras,
			extra_beds_charge, extra_beds, discount_percent, created_date,
			children, checkout, checkin, booking_number, adults;

	public MyAccount() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		thiscontext = container.getContext();
		View rootView = inflater.inflate(R.layout.myaccountfragment, container,
				false);
		user_Prefrences = new User_Prefrences(getActivity());
		listhistorydetails = (ListView) rootView
				.findViewById(R.id.listhistorydetails);
		if (user_Prefrences.getUser_id() != "") {
			pd = ProgressDialog.show(getActivity(), "", "Please Wait", false);
			ServerDownload download = new ServerDownload(getActivity(), "",
					this, "bookingDetails");
			download.execute(postBooking());
		} else {
			Toast.makeText(getActivity(), "Login First", 3).show();
		}

		return rootView;
	}

	@Override
	public void httpResponse(JSONObject jsonObject, String tag, int responceCode) {
		// TODO Auto-generated method stub
		if (pd.isShowing()) {
			pd.dismiss();
		}
		Log.e("My Account ", jsonObject.toString());

		if (jsonObject != null) {
			JSONArray jsonArray = null;
			try {
				jsonArray = jsonObject.getJSONArray("booking");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					BookingHistoryDetails bookinghistorydetails = new BookingHistoryDetails();
					bookinghistorydetails.setAdults(obj.getString("adults"));
					bookinghistorydetails.setBooking_number(obj
							.getString("booking_number"));
					bookinghistorydetails.setCheckin(obj.getString("checkin"));
					bookinghistorydetails
							.setCheckout(obj.getString("checkout"));
					bookinghistorydetails
							.setChildren(obj.getString("children"));
					bookinghistorydetails.setCreated_date(obj
							.getString("created_date"));
					bookinghistorydetails.setDiscount_percent(obj
							.getString("discount_percent"));
					bookinghistorydetails.setExtra_beds(obj
							.getString("extra_beds"));
					bookinghistorydetails.setExtra_beds_charge(obj
							.getString("extra_beds_charge"));
					bookinghistorydetails.setExtras(obj.getString("extras"));
					bookinghistorydetails.setExtras_fee(obj
							.getString("extras_fee"));
					bookinghistorydetails.setHotel(obj.getString("hotel"));
					bookinghistorydetails
							.setHotel_id(obj.getString("hotel_id"));
					bookinghistorydetails.setId(obj.getString("id"));
					bookinghistorydetails.setMeal_plan_id(obj
							.getString("meal_plan_id"));
					bookinghistorydetails.setMeal_plan_price(obj
							.getString("meal_plan_price"));
					bookinghistorydetails.setPayment_sum(obj
							.getString("payment_sum"));
					bookinghistorydetails.setPayment_type(obj
							.getString("payment_type"));
					bookinghistorydetails.setPrice(obj.getString("price"));
					bookinghistorydetails.setRoom(obj.getString("room"));
					bookinghistorydetails.setRoom_id(obj.getString("room_id"));
					bookinghistorydetails.setRoom_numbers(obj
							.getString("room_numbers"));
					bookinghistorydetails.setRooms(obj.getString("rooms"));
					bookinghistorydetails.setStatus(obj.getString("status"));
					bookinghistorydetails.setOrder_price(obj
							.getString("order_price"));
					bookinghistory.add(bookinghistorydetails);
				}
			} catch (JSONException e1) {
				e1.printStackTrace();

			}
		}

		adapter = new BookingHistoryAdpter(thiscontext, bookinghistory);
		listhistorydetails.setAdapter(adapter);
		listhistorydetails.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BookingHistoryDetails bookingparticularlisthistory = bookinghistory
						.get(position);
				HotelName = bookingparticularlisthistory.getHotel();
				adults = bookingparticularlisthistory.getAdults();
				booking_number = bookingparticularlisthistory
						.getBooking_number();
				checkin = bookingparticularlisthistory.getCheckin();
				checkout = bookingparticularlisthistory.getCheckout();
				children = bookingparticularlisthistory.getChildren();
				created_date = bookingparticularlisthistory.getCreated_date();
				discount_percent = bookingparticularlisthistory
						.getDiscount_percent();
				extra_beds = bookingparticularlisthistory.getExtra_beds();
				extra_beds_charge = bookingparticularlisthistory
						.getExtra_beds_charge();
				extras = bookingparticularlisthistory.getExtras();
				extras_fee = bookingparticularlisthistory.getExtras_fee();
				hotel_id = bookingparticularlisthistory.getHotel_id();
				meal_plan_id = bookingparticularlisthistory.getMeal_plan_id();
				meal_plan_price = bookingparticularlisthistory
						.getMeal_plan_price();
				order_price = bookingparticularlisthistory.getOrder_price();
				payment_sum = bookingparticularlisthistory.getPayment_sum();
				payment_type = bookingparticularlisthistory.getPayment_type();
				price = bookingparticularlisthistory.getPrice();
				roomtype = bookingparticularlisthistory.getRoom();
				rooms = bookingparticularlisthistory.getRooms();
				room_id = bookingparticularlisthistory.getRoom_id();
				room_numbers = bookingparticularlisthistory.getRoom_numbers();
				status = bookingparticularlisthistory.getStatus();

				Intent isendbookhistory = new Intent(thiscontext,
						BookingAllHistoryDetails.class);
				isendbookhistory.putExtra("HotelName", HotelName);
				isendbookhistory.putExtra("status", status);
				isendbookhistory.putExtra("room_numbers", room_numbers);
				isendbookhistory.putExtra("room_id", room_id);
				isendbookhistory.putExtra("rooms", rooms);
				isendbookhistory.putExtra("roomtype", roomtype);
				isendbookhistory.putExtra("price", price);
				isendbookhistory.putExtra("payment_type", payment_type);
				isendbookhistory.putExtra("payment_sum", payment_sum);
				isendbookhistory.putExtra("order_price", order_price);
				isendbookhistory.putExtra("meal_plan_price", meal_plan_price);
				isendbookhistory.putExtra("meal_plan_id", meal_plan_id);
				isendbookhistory.putExtra("hotel_id", hotel_id);
				isendbookhistory.putExtra("extras_fee", extras_fee);
				isendbookhistory.putExtra("extras", extras);
				isendbookhistory.putExtra("extra_beds_charge",
						extra_beds_charge);
				isendbookhistory.putExtra("discount_percent", discount_percent);
				isendbookhistory.putExtra("created_date", created_date);
				isendbookhistory.putExtra("children", children);
				isendbookhistory.putExtra("checkout", checkout);
				isendbookhistory.putExtra("checkin", checkin);
				isendbookhistory.putExtra("booking_number", booking_number);
				isendbookhistory.putExtra("adults", adults);
				startActivity(isendbookhistory);
				Toast.makeText(thiscontext, HotelName + "" + position, 5)
						.show();

			}
		});

	}

	String postBooking() {
		JSONObject postoObject = new JSONObject();
		try {

			postoObject.put("user_id", user_Prefrences.getUser_id());
			postoObject.put("password", user_Prefrences.getPassword());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("postBooking", postoObject.toString());
		return postoObject.toString();
	}

}
