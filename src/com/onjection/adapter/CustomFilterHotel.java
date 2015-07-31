package com.onjection.adapter;

import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.onjection.booking.RoomBookingForm;
import com.onjection.booking.RoomDetails;
import com.onjection.handler.User_Prefrences;
//import com.onjection.lazylist.ImageLoader;
import com.onjection.model.NewFilterhoteldetails;
import com.onjection.myaccountDetails.Login;
import com.onjection.roomoncall.HotelDescription;
import com.onjection.roomoncall.R;

public class CustomFilterHotel extends BaseAdapter  {
	private Activity activity;
	private LayoutInflater inflater;
	private List<NewFilterhoteldetails> newfilterdata;
	DisplayImageOptions options;
	ImageLoader imageLoader;

	public CustomFilterHotel(Activity activity,
			List<NewFilterhoteldetails> newfilterdata) {
		this.activity = activity;
		this.newfilterdata = newfilterdata;
		
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newfilterdata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return newfilterdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.hotelsearchlistitem, null);

		ImageView imghotelimg = (ImageView) convertView
				.findViewById(R.id.imghotelimg);

		TextView txthotelname = (TextView) convertView
				.findViewById(R.id.txthotelname);

		TextView txtlocallocation = (TextView) convertView
				.findViewById(R.id.txthotelAdd);
		TextView txthotelrate = (TextView) convertView
				.findViewById(R.id.txthotelrate);
//		Button btnbook = (Button) convertView.findViewById(R.id.btnbook);
		final NewFilterhoteldetails filterhoteldetails = newfilterdata
				.get(position);

		/* txtroomtype.setText(hoteldetails.getTxtroomtype()); */
		txthotelname.setText(filterhoteldetails.getTxthotelname()+"");
		txtlocallocation.setText(filterhoteldetails.getLocation()+"");
		txthotelrate.setText("Rs " + filterhoteldetails.getTxtstar()+"");
		
		Log.e("Sop ", filterhoteldetails.getTxthotelname()   +" "+ filterhoteldetails.getLocation()+"" + filterhoteldetails.getTxtstar());

//		ImageLoader imgloder = new ImageLoader(activity);
//		imgloder.DisplayImage(filterhoteldetails.getImghotelimg(), imghotelimg);
		
 	imageLoader = ImageLoader.getInstance();
 	imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.loading)
		.showImageForEmptyUri(R.drawable.loading)
		.showImageOnFail(R.drawable.loading).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.ARGB_8888)
				.build();
		imageLoader. displayImage(
				filterhoteldetails.getImghotelimg(), imghotelimg, options);

		imghotelimg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (new User_Prefrences(activity).getUser_id() != "") {
					String hotel_id = filterhoteldetails.getHotel_id();
					Intent intentbookdetails = new Intent(activity,
							RoomBookingForm.class);
					intentbookdetails.putExtra("hotel_id", hotel_id);
					activity.startActivity(intentbookdetails);

				} else {
					Intent ilogin = new Intent(activity, Login.class);
					activity.startActivity(ilogin);

				}
			}
		});
		txthotelname.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String hotel_id = filterhoteldetails.getHotel_id();
				Intent ihoteldescription = new Intent(activity,
						HotelDescription.class);
				ihoteldescription.putExtra("hotel_id", hotel_id);
				activity.startActivity(ihoteldescription);

			}
		});
		return convertView;

	}
}
