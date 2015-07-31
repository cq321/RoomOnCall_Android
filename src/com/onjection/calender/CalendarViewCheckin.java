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

public class CalendarViewCheckin extends DialogFragment { 
	private static final int DAYSLIMIT = 330;
	public GregorianCalendar month, itemmonth;// calendar instances.
	public GregorianCalendar todayCalender;// calendar instances. 
	public GregorianCalendar lastCalender;// calendar instances.
	public CalendarAdapterCheckin adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot
							// marker.
							// public ArrayList<String> items; // container to
							// store calendar items which
	// // needs showing the event marker
//	private static CalendarView dialogFragment;
	public static CalendarViewCheckin getInstance(CheckinDialogCallBack callBack) { 
//		if(dialogFragment == null){
//			dialogFragment= new CalendarView();
//		}
		CalendarViewCheckin dialogFragment = new CalendarViewCheckin();
		dialogFragment.setTargetFragment((SearchHotelFragment)callBack, 1);
		return dialogFragment;
	}
	private CalendarViewCheckin() {
		// TODO Auto-generated constructor stub
		initAdaptor();
	}
	
	public interface CheckinDialogCallBack {
		public void handleCallBack(String s);

	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		System.out.println("CALENDER MONTH onCreateDialog");
//		initDialog();
//		return dialog;		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); 
		builder.setView(getContentView());
		Dialog dialog = builder.create();
		return dialog;
	}
	private void initAdaptor(){
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		todayCalender = (GregorianCalendar) GregorianCalendar.getInstance();
		lastCalender = (GregorianCalendar) GregorianCalendar.getInstance();
		lastCalender.add(Calendar.DATE, DAYSLIMIT);		
		itemmonth = (GregorianCalendar) month.clone();
		adapter = new CalendarAdapterCheckin(getActivity(), month,todayCalender,lastCalender);
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
		RelativeLayout previous = (RelativeLayout) view.findViewById(R.id.previous);
		previous.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(month.compareTo(todayCalender)>0){
					setPreviousMonth();
					refreshCalendar(view);
				}				
			}
		});

		RelativeLayout next = (RelativeLayout) view.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("today calender "+todayCalender.getTime());
				System.out.println("last calender "+lastCalender.getTime());
				System.out.println("month.compareTo(lastCalender) "+month.compareTo(lastCalender));
				if(month.compareTo(lastCalender)<=0){
					setNextMonth();
					refreshCalendar(view);
				}
			}
		});
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				TextView tv = (TextView)v.findViewById(R.id.date);
				System.out.println("CALENDER MONTH tv "+tv.getTag());
				if(tv.getTag().equals(false)){					
					return;
				}
				((CalendarAdapterCheckin) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapterCheckin.dayString
						.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				// taking last part of date. ie; 2 from 2012-12-02.
				String gridvalueString = separatedTime[2].replaceFirst("^0*","");
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar(view);
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar(view);
				}
				((CalendarAdapterCheckin) parent.getAdapter()).setSelected(v);
				((CalendarAdapterCheckin) parent.getAdapter()).assignSelectedDateAsCurrentDate(position);
				//showToast(selectedGridDate);				
				SearchHotelFragment fragmentA = (SearchHotelFragment)getTargetFragment();
//				System.out.println("calender CalendarView.dateMode "+CalendarView.dateMode);
                fragmentA.handleCallBack(adapter.curentDateString);
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
//		System.out.println("CALENDER MONTH setPreviousMonth "+month);

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
 
