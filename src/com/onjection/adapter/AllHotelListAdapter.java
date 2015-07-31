package com.onjection.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.onjection.booking.RoomBookingForm;
import com.onjection.booking.RoomDetails;
import com.onjection.handler.User_Prefrences;
//import com.onjection.lazylist.ImageLoader;
import com.onjection.model.HotelData;
import com.onjection.myaccountDetails.Login;
import com.onjection.roomoncall.BookedHotel;
import com.onjection.roomoncall.HotelDescription;
import com.onjection.roomoncall.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InflateParams")
@SuppressWarnings("unused")
public class AllHotelListAdapter extends BaseAdapter {
	private Context ctx;
	private LayoutInflater inflater;
	private List<HotelData> hotelItems;
	DisplayImageOptions options;
	ImageLoader imageLoader;
	public AllHotelListAdapter(Context ctx, List<HotelData> roomItems) {
		this.ctx = ctx;
		this.hotelItems = roomItems;
	}

	@Override
	public int getCount() {

		return hotelItems.size();
	}

	@Override
	public Object getItem(int position) {

		return hotelItems.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (inflater == null)
			inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.hotelsearchlistitem, null);

		ImageView hotelimage = (ImageView) convertView
				.findViewById(R.id.imghotelimg);
		TextView txthotelname = (TextView) convertView
				.findViewById(R.id.txthotelname);
		TextView txthotelrate = (TextView) convertView
				.findViewById(R.id.txthotelrate);
//		Button btnbook = (Button) convertView.findViewById(R.id.btnbook);

		final HotelData hoteldetails = hotelItems.get(position);

		txthotelname.setText(hoteldetails.getTxthotelname());
		txthotelrate.setText("Rs " +hoteldetails.getLowest_price());
		imageLoader = ImageLoader.getInstance();
	 	imageLoader.init(ImageLoaderConfiguration.createDefault(ctx));
			options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.loading)
			.showImageForEmptyUri(R.drawable.loading)
			.showImageOnFail(R.drawable.loading).cacheInMemory(true)
					.cacheOnDisk(true).considerExifParams(true)
					.imageScaleType(ImageScaleType.EXACTLY)
					.bitmapConfig(Bitmap.Config.ARGB_8888)
					.build();
			imageLoader. displayImage(
					hoteldetails.getHotelimage(), hotelimage, options);
//		ImageLoader imgloder = new ImageLoader(ctx);
//		imgloder.DisplayImage(hoteldetails.getHotelimage(), hotelimage);

		txthotelname.setOnClickListener(new OnClickListener() {

			   @Override
			   public void onClick(View v) {
			    int hotelid = hoteldetails.getHotelid();
			    String hotel_id = String.valueOf(hotelid);
			    Intent ihoteldescription = new Intent(ctx,
			      HotelDescription.class);
			    ihoteldescription.putExtra("hotel_id", hotel_id);
			    ctx.startActivity(ihoteldescription);
			   }
			  });
		
		hotelimage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (new User_Prefrences(ctx).getUser_id() != "") {
					int hotel_id = hoteldetails.getHotelid();
					Intent intentbookdetails = new Intent(ctx,
							RoomBookingForm.class);
					intentbookdetails.putExtra("hotel_id", hotel_id + "");
					ctx.startActivity(intentbookdetails);
				} else {
					Intent ilogin = new Intent(ctx, Login.class);
					ctx.startActivity(ilogin);

				}
			}
		});

		return convertView;

	}

}
