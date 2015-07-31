package com.onjection.PagerViewImageAdapter;

import java.util.ArrayList;

import com.onjection.booking.RoomDetails;
import com.onjection.lazylist.ImageLoader;
import com.onjection.roomoncall.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageAdapter extends PagerAdapter {
	Context context;
	public ArrayList<String> arrRoomURL = new ArrayList<String>();
	RoomDetails bookingDetails = new RoomDetails();
    private String[] GalImages = new String [] {
    		"http://dev.onjection.com/roomoncall/images/rooms_icons/double_icon.png",
    		"http://dev.onjection.com/roomoncall/images/rooms_icons/double_2.jpg",
    		"http://dev.onjection.com/roomoncall/images/rooms_icons/double_3.jpg"
    };
    
    public ImageAdapter(Context context, ArrayList<String> arrUrl){
    	this.context=context;
    	this.arrRoomURL=arrUrl;
    }
    @Override
    public int getCount() {
      return arrRoomURL.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      ImageView imageView = new ImageView(context);
      int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
//      imageView.setPadding(padding, padding, padding, padding);
      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
      ImageLoader im = new ImageLoader(context);
		im.DisplayImage(arrRoomURL.get(position), imageView);
//      imageView.setImageResource(GalImages[position]);
      ((ViewPager) container).addView(imageView, 0);
      return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      ((ViewPager) container).removeView((ImageView) object);
    }
  }
