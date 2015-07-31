package com.onjection.adapter;

import java.util.List;

import com.onjection.booking.RoomBookingForm;
import com.onjection.handler.User_Prefrences;
import com.onjection.lazylist.ImageLoader;
import com.onjection.model.BookingHistoryDetails;
import com.onjection.model.HotelData;
import com.onjection.myaccountDetails.Login;
import com.onjection.roomoncall.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BookingHistoryAdpter extends BaseAdapter {
	private Context ctx;
	private LayoutInflater inflater;
	private List<BookingHistoryDetails> bookinghistory;

	public BookingHistoryAdpter(Context ctx,
			List<BookingHistoryDetails> bookinghistory) {
		this.ctx = ctx;
		this.bookinghistory = bookinghistory;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bookinghistory.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bookinghistory.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (inflater == null)
			inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.booking_history_item, null);
		TextView tvHotelName = (TextView) convertView
				.findViewById(R.id.tvHotelName);
		TextView tvFromDate = (TextView) convertView
				.findViewById(R.id.tvFromDate);
		TextView tvToDate = (TextView) convertView.findViewById(R.id.tvToDate);
		TextView RoomType = (TextView) convertView.findViewById(R.id.RoomType);
		TextView tvRoomPrice = (TextView) convertView
				.findViewById(R.id.tvRoomPrice);

		final BookingHistoryDetails bookingparticularlisthistory = bookinghistory
				.get(position);
		tvHotelName.setText(bookingparticularlisthistory.getHotel());
		tvFromDate.setText(bookingparticularlisthistory.getCheckin());
		tvToDate.setText(bookingparticularlisthistory.getCheckout());
		RoomType.setText(bookingparticularlisthistory.getRoom());
		tvRoomPrice.setText(bookingparticularlisthistory.getPayment_sum());
		return convertView;

	}

}
