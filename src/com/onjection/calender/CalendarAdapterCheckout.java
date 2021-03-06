package com.onjection.calender;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onjection.roomoncall.R;

public class CalendarAdapterCheckout extends BaseAdapter {
	private Context mContext;

	private java.util.Calendar month, todayCalender, lastCalender;
	public GregorianCalendar pmonth; // calendar instance for previous month
	/**
	 * calendar instance for previous month for getting complete view
	 */
	public GregorianCalendar pmonthmaxset;
	private GregorianCalendar selectedDate;
	private GregorianCalendar calenderDeparture;
	int firstDay;
	int maxWeeknumber;
	int maxP;
	int calMaxP;
	int lastWeekDay;
	int leftDays;
	int mnthlength;
	public String itemvalue, curentDateString;
	DateFormat df;
	private ArrayList<String> items;
	public static List<String> dayString;
	private View previousView;

	public CalendarAdapterCheckout(Context c, GregorianCalendar monthCalendar,
			GregorianCalendar todayCalender, GregorianCalendar lastCalender,
			GregorianCalendar calenderDeparture) {
		CalendarAdapterCheckout.dayString = new ArrayList<String>();
		Locale.setDefault(Locale.US);
		month = monthCalendar;
		this.todayCalender = todayCalender;
		this.lastCalender = lastCalender;
		this.calenderDeparture = calenderDeparture;
		selectedDate = (GregorianCalendar) monthCalendar.clone();
		month.set(Calendar.MONTH, calenderDeparture.get(Calendar.MONTH));
		month.set(Calendar.DAY_OF_MONTH,
				calenderDeparture.get(Calendar.DAY_OF_MONTH));
		month.set(Calendar.YEAR, calenderDeparture.get(Calendar.YEAR));
		mContext = c;
		month.set(GregorianCalendar.DAY_OF_MONTH, 1);
		this.items = new ArrayList<String>();
		df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		curentDateString = df.format(selectedDate.getTime());
		System.out.println("**** curentDateString " + curentDateString);
		refreshDays();
	}

	public void setCalenderDeparture(GregorianCalendar calenderDeparture) {
		this.calenderDeparture = calenderDeparture;
	}

	public void setItems(ArrayList<String> items) {
		for (int i = 0; i != items.size(); i++) {
			if (items.get(i).length() == 1) {
				items.set(i, "0" + items.get(i));
			}
		}
		this.items = items;
	}

	public int getCount() {
		return dayString.size();
	}

	public Object getItem(int position) {
		return dayString.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new view for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		TextView dayView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.calendar_item, null);

		}
		dayView = (TextView) v.findViewById(R.id.date);
		// separates daystring into parts.
		String[] separatedTime = dayString.get(position).split("-");
		// taking last part of date. ie; 2 from 2012-12-02
		String gridvalue = separatedTime[2].replaceFirst("^0*", "");
		// checking whether the day is in current month or not.
		if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
			// setting offdays to white color.
			dayView.setTextColor(Color.GRAY);
			dayView.setClickable(false);
			dayView.setFocusable(false);
			dayView.setTag(false);

		} else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
			dayView.setTextColor(Color.GRAY);
			dayView.setClickable(false);
			dayView.setFocusable(false);
			dayView.setTag(false);
		} else {
			// setting curent month's days in blue color.
			dayView.setTextColor(Color.BLACK);
			dayView.setTag(true);
		}
		// if block added by binit to disable date less than today date
		GregorianCalendar temp1 = (GregorianCalendar) month.clone();
		temp1.set(Calendar.DAY_OF_MONTH, (Integer.parseInt(gridvalue)));
		if (temp1.compareTo(calenderDeparture) < 0) {
			dayView.setTextColor(Color.GRAY);
			dayView.setClickable(false);
			dayView.setFocusable(false);
			dayView.setFocusableInTouchMode(false);
			dayView.setTag(false);
		}
		// if((Integer.parseInt(gridvalue)<(calenderDeparture.get(GregorianCalendar.DAY_OF_MONTH)))&&
		// (calenderDeparture.get(GregorianCalendar.MONTH)==month.get(GregorianCalendar.MONTH))&&
		// (calenderDeparture.get(GregorianCalendar.YEAR)==month.get(GregorianCalendar.YEAR))){
		// dayView.setTextColor(Color.GRAY);
		// dayView.setClickable(false);
		// dayView.setFocusable(false);
		// dayView.setFocusableInTouchMode(false);
		// dayView.setTag(false);
		// }

		GregorianCalendar temp = (GregorianCalendar) month.clone();
		temp.set(Calendar.DAY_OF_MONTH, (Integer.parseInt(gridvalue)));
		if (temp.compareTo(lastCalender) > 0) {
			dayView.setTextColor(Color.GRAY);
			dayView.setClickable(false);
			dayView.setFocusable(false);
			dayView.setFocusableInTouchMode(false);
			dayView.setTag(false);
		}

		if (dayString.get(position).equals(curentDateString)) {
			setSelected(v);
			previousView = v;
		} else {
			v.setBackgroundResource(R.drawable.calender_item_background);
		}
		dayView.setText(gridvalue);
		// create date string for comparison
		String date = dayString.get(position);
		if (date.length() == 1) {
			date = "0" + date;
		}
		String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
		if (monthStr.length() == 1) {
			monthStr = "0" + monthStr;
		}
		// show icon if date is not empty and it exists in the items array
		ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
		if (date.length() > 0 && items != null && items.contains(date)) {
			iw.setVisibility(View.VISIBLE);
		} else {
			iw.setVisibility(View.INVISIBLE);
		}
		return v;
	}

	public View setSelected(View view) {
		if (previousView != null) {
			previousView
					.setBackgroundResource(R.drawable.calender_item_background);
		}
		previousView = view;
		view.setBackgroundResource(R.drawable.calendar_cel_selectl);
		return view;
	}

	public void refreshDays() {
		// clear items
		items.clear();
		dayString.clear();
		Locale.setDefault(Locale.US);
		pmonth = (GregorianCalendar) month.clone();
		// month start day. ie; sun, mon, etc
		firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
		// finding number of weeks in current month.
		maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		// allocating maximum row number for the gridview.
		mnthlength = maxWeeknumber * 7;
		maxP = getMaxP(); // previous month maximum day 31,30....
		calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
		/**
		 * Calendar instance for getting a complete gridview including the three
		 * month's (previous,current,next) dates.
		 */
		pmonthmaxset = (GregorianCalendar) pmonth.clone();
		/**
		 * setting the start date as previous month's required date.
		 */
		pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

		/**
		 * filling calendar gridview.
		 */
		for (int n = 0; n < mnthlength; n++) {
			itemvalue = df.format(pmonthmaxset.getTime());
			pmonthmaxset.add(GregorianCalendar.DATE, 1);
			dayString.add(itemvalue);
		}
	}

	private int getMaxP() {
		int maxP;
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			pmonth.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}
		maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		return maxP;
	}

	public void assignSelectedDateAsCurrentDate(int position) {
		curentDateString = dayString.get(position);
	}

}