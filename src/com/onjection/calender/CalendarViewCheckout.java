package com.onjection.calender;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onjection.roomoncall.R;
import com.onjection.roomoncall.SearchHotelFragment;

public class CalendarViewCheckout extends DialogFragment {
	private static final int DAYSLIMIT = 330;
	public GregorianCalendar month, itemmonth;// calendar instances.
	private static GregorianCalendar departureCalender;
	public GregorianCalendar todayCalender;// calendar instances.
	public GregorianCalendar lastCalender;// calendar instances.
	public CalendarAdapterCheckout adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot
							// marker.
							// public ArrayList<String> items; // container to
							// store calendar items which

	// // needs showing the event marker
	// private static CalendarView dialogFragment;
	public static CalendarViewCheckout getInstance(CheckOutDialogCallBack callBack) {
		CalendarViewCheckout dialogFragment = new CalendarViewCheckout();
		dialogFragment.setTargetFragment((SearchHotelFragment) callBack, 1);
		return dialogFragment;
	}

	private CalendarViewCheckout() {
		// TODO Auto-generated constructor stub
		initAdaptor();
	}

	public interface CheckOutDialogCallBack {
		public void handleArrivalCallBack(String s);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(getContentView());
		Dialog dialog = builder.create();
		return dialog;
	}

	private void initAdaptor() {
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		todayCalender = (GregorianCalendar) GregorianCalendar.getInstance();
		lastCalender = (GregorianCalendar) GregorianCalendar.getInstance();
		lastCalender.add(Calendar.DATE, DAYSLIMIT);
		if (CalendarViewCheckout.departureCalender == null) {
			CalendarViewCheckout.departureCalender = (GregorianCalendar) GregorianCalendar
					.getInstance();
		}
		itemmonth = (GregorianCalendar) month.clone();
 
		adapter = new CalendarAdapterCheckout(getActivity(), month,todayCalender,lastCalender,departureCalender);
//		month.set(Calendar.MONTH,CalendarViewArrival.departureCalender.get(Calendar.MONTH));
//		month.set(Calendar.DAY_OF_MONTH,CalendarViewArrival.departureCalender.get(Calendar.DAY_OF_MONTH));
//		month.set(Calendar.YEAR,CalendarViewArrival.departureCalender.get(Calendar.YEAR));
 
	}

	public void setDepartureCalender(GregorianCalendar departureCalender) {
		CalendarViewCheckout.departureCalender = departureCalender;

		month.set(Calendar.MONTH,
				CalendarViewCheckout.departureCalender.get(Calendar.MONTH));
		month.set(Calendar.DAY_OF_MONTH, CalendarViewCheckout.departureCalender
				.get(Calendar.DAY_OF_MONTH));
		month.set(Calendar.YEAR,
				CalendarViewCheckout.departureCalender.get(Calendar.YEAR));
		adapter.notifyDataSetChanged();
	}

	private View getContentView() {
		System.out.println("CALENDER MONTH getContentView");
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View view = inflater.inflate(R.layout.calendar, null);

		Locale.setDefault(Locale.US);
		initAdaptor();
		GridView gridview = (GridView) view.findViewById(R.id.gridview);
		gridview.setAdapter(adapter);
		handler = new Handler();
		// handler.post(calendarUpdater);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
		RelativeLayout previous = (RelativeLayout) view
				.findViewById(R.id.previous);
		previous.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (month.compareTo(departureCalender) > 0) {
					setPreviousMonth();
					refreshCalendar(view);
				}
			}
		});

		RelativeLayout next = (RelativeLayout) view.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (month.compareTo(lastCalender) < 0) {
					setNextMonth();
					refreshCalendar(view);
				}
			}
		});
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				TextView tv = (TextView) v.findViewById(R.id.date);
				System.out.println("CALENDER MONTH tv " + tv.getTag());
				if (tv.getTag().equals(false)) {
					return;
				}
				((CalendarAdapterCheckout) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapterCheckout.dayString
						.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				// taking last part of date. ie; 2 from 2012-12-02.
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar(view);
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar(view);
				}
				((CalendarAdapterCheckout) parent.getAdapter()).setSelected(v);
				((CalendarAdapterCheckout) parent.getAdapter())
						.assignSelectedDateAsCurrentDate(position);
				// showToast(selectedGridDate);
				SearchHotelFragment fragmentA = (SearchHotelFragment) getTargetFragment();
				// System.out.println("calender CalendarView.dateMode "+CalendarView.dateMode);
				fragmentA.handleArrivalCallBack(adapter.curentDateString);
				getDialog().dismiss();
			}
		});
		return view;
	}

	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}
		// System.out.println("CALENDER MONTH setPreviousMonth "+month);

	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}

	}

	public void refreshCalendar(View view) {
		TextView title = (TextView) view.findViewById(R.id.title);
		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		// handler.post(calendarUpdater); // generate some calendar items
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}
}
