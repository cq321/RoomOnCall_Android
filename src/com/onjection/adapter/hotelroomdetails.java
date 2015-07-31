package com.onjection.adapter;

import java.util.List;

import com.onjection.model.HotelData;
import com.onjection.model.Roomdetails;
import com.onjection.roomoncall.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class hotelroomdetails extends BaseAdapter {
	private Activity ctx;
	private LayoutInflater inflater;
	private List<Roomdetails> roomdetails;

	public hotelroomdetails(Activity ctx, List<Roomdetails> roomdetails) {
		super();
		this.ctx = ctx;
		this.roomdetails = roomdetails;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return roomdetails.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return roomdetails.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (inflater == null)
			inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.hotelroomdetails, null);
		TextView txtroomtype = (TextView) convertView
				.findViewById(R.id.txtroomtype);
		TextView txtroomavilable = (TextView) convertView
				.findViewById(R.id.txtroomavilable);
		TextView txtdefaultrate = (TextView) convertView
				.findViewById(R.id.txtdefaultrate);
		final Roomdetails roomdetalsitem = roomdetails.get(position);
		txtroomtype.setText(roomdetalsitem.getRoom_type());
		txtroomavilable.setText(roomdetalsitem.getAvailable_rooms());
		txtdefaultrate.setText(roomdetalsitem.getDefault_price());
		txtroomtype.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		return convertView;

	}
}
