package com.onjection.roomoncall;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import serverTask.ConnectionDector;

import com.onjection.calender.CalendarViewCheckin;
import com.onjection.calender.CalendarViewCheckin.CheckinDialogCallBack;
import com.onjection.calender.CalendarViewCheckout;
import com.onjection.calender.CalendarViewCheckout.CheckOutDialogCallBack;
import com.onjection.handler.ServiceHandler;
import com.onjection.handler.User_Prefrences;
import com.onjection.handler.Utilz;
import com.onjection.model.CityInfo;
import com.onjection.model.LocationInfo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class SearchHotelFragment extends Fragment implements OnClickListener,
		CheckOutDialogCallBack, CheckinDialogCallBack {
	private ProgressDialog pd;
	Context thiscontext;
	TextView txtchkout, txtchkin;
	TextView txtdatemonthyearchkin, txtdatemonthyearchkout;
	Spinner spinearchild, spinearAdult, spinearArea, spinearcity;
	static List<String> arrCityName = new ArrayList<String>();
	static List<String> arrCityID = new ArrayList<String>();
	private CalendarViewCheckin calenderCheckin;
	private CalendarViewCheckout calenderCheckout;
	private String checkinDate, checkoutDate;
	User_Prefrences user_Prefrences;
	int SelectedPosition;
	String jsonStr;
	Button btnchild, btncity, btnSearch;
	String[] Adults = new String[] { "1", "2", "3" };
	String[] Child = new String[] { "1", "2", "3" };
	String[] localArea = new String[] { "sector 31", "sector 30", "Rajiv Chowk" };
	String city_id, from_date, to_date;
	String max_children = "0";
	String max_adults = "1";
	private static String urlsearch = "https://www.roomoncall.in/api/get_cities";
	@SuppressWarnings("unused")
	private List<CityInfo> citylist = new ArrayList<CityInfo>();
	ArrayList<String> cityname = new ArrayList<String>();
	ArrayList<String> cityArea = new ArrayList<String>();
	LinearLayout llchkIn, llChkOut;

	public SearchHotelFragment() {
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		thiscontext = container.getContext();
		initCalender();
		View rootView = inflater.inflate(R.layout.searchhotelfragment,
				container, false);
		user_Prefrences = new User_Prefrences(getActivity());
		btnSearch = (Button) rootView.findViewById(R.id.btnSearch);
		txtchkin = (TextView) rootView.findViewById(R.id.txtchkin);
		txtchkout = (TextView) rootView.findViewById(R.id.txtchkout);

		llchkIn = (LinearLayout) rootView.findViewById(R.id.ll_chkIn);
		llChkOut = (LinearLayout) rootView.findViewById(R.id.ll_chkOut);
		txtdatemonthyearchkin = (TextView) rootView
				.findViewById(R.id.txtdatemonthyearchkin);
		txtdatemonthyearchkout = (TextView) rootView
				.findViewById(R.id.txtdatemonthyearchkout);
		spinearchild = (Spinner) rootView.findViewById(R.id.spinearchild);
		spinearAdult = (Spinner) rootView.findViewById(R.id.spinearAdult);
		// spinearArea = (Spinner) rootView.findViewById(R.id.spinearArea);
		spinearcity = (Spinner) rootView.findViewById(R.id.spinearcity);
		if (Utilz.isInternetConnected(thiscontext)) {
			new GetSearchdetails().execute();
		} else {
			Utilz.showAlertActivity("No Internet Connection", thiscontext);

		}
		// new GetSearchdetails().execute();
		spinearAdultmethod();
		spinearchildmethod();
		// Calendar calendar = Calendar.getInstance();
		/*
		 * Date today = calendar.getTime();
		 * 
		 * calendar.add(Calendar.DAY_OF_YEAR, 1); Date tomorrow =
		 * calendar.getTime(); DateFormat dateFormat = new
		 * SimpleDateFormat("yyyy-MM-dd");
		 */

		// String todayAsString = dateFormat.format(today);
		// String tomorrowAsString = dateFormat.format(tomorrow);
		// txtdatemonthyearchkin.setText(todayAsString);
		// txtdatemonthyearchkout.setText(tomorrowAsString);

		checkinDate = calenderCheckin.adapter.curentDateString;

		Log.e("Chkin Date Dflt", checkinDate);
		txtdatemonthyearchkin.setText(dateFormate(checkinDate));
		checkoutDate = calenderCheckout.adapter.curentDateString;

		Log.e("Current date", checkoutDate);
		String dt = checkoutDate; // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dt));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.add(Calendar.DATE, 1); // number of days to add, can also use
									// Calendar.DAY_OF_MONTH in place of
									// Calendar.DATE
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String output = sdf1.format(c.getTime());
		checkoutDate = output;
		Log.e("Chk out Date Dflt", checkoutDate);
		Log.e("Current date", output);
		txtdatemonthyearchkout.setText(dateFormate(output));

		btnSearch.setOnClickListener(this);
		txtchkin.setOnClickListener(this);
		txtchkout.setOnClickListener(this);
		llchkIn.setOnClickListener(this);
		llChkOut.setOnClickListener(this);

		return rootView;
	}

	private void initCalender() {
		if (calenderCheckin == null) {
			calenderCheckin = CalendarViewCheckin
					.getInstance(SearchHotelFragment.this);
		}
		if (calenderCheckout == null) {
			calenderCheckout = CalendarViewCheckout
					.getInstance(SearchHotelFragment.this);
		}
	}

	public class GetSearchdetails extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pd = new ProgressDialog(thiscontext);
			pd.setMessage("Please wait...");
			pd.setCancelable(false);
			pd.show();

		}

		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			jsonStr = sh.makeServiceCall(urlsearch, ServiceHandler.GET);

			Log.e("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				JSONArray jsonArray = null;
				try {
					jsonArray = new JSONArray(jsonStr);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject obj = jsonArray.getJSONObject(i);

						JSONArray locationsubarray = obj
								.getJSONArray("location");

						ArrayList<LocationInfo> locationInfos = new ArrayList<LocationInfo>();

						for (int j = 0; j < locationsubarray.length(); j++) {
							JSONObject jsonlocation = locationsubarray
									.getJSONObject(j);

							LocationInfo locationInfo = new LocationInfo(
									jsonlocation.getString("id"),
									jsonlocation.getString("name"));
							
							

							locationInfos.add(locationInfo);
						}

						CityInfo cityInfo = new CityInfo(obj.getString("id"),
								obj.getString("name"), locationInfos);

						citylist.add(cityInfo);
						cityname.add(obj.optString("name"));
						
						arrCityID.add(obj.getString("id"));
						arrCityName.add(obj.getString("name"));
						Log.e("City", obj.optString("name"));
						Log.e("City", obj.getString("id"));
						// citylist.add(arg0)

					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			// new GetLocation().execute();
			
			if (pd.isShowing()){
				pd.dismiss();
			}
			if (! new ConnectionDector(getActivity()).isNetError) {
				
			 
			if (jsonStr != null) {
				populatespinear();

			} else {
				Toast.makeText(getActivity(),
						"Somting Went worg , Check internet connection", 3)
						.show();
			}
			}else{
				Utilz.showAlertActivityWithTitle("Sorry", "slow internet", getActivity());
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.ll_chkIn:
			calenderCheckin.show(getFragmentManager(), "Test");

			break;
		case R.id.ll_chkOut:
			calenderCheckout.show(getFragmentManager(), "Test");
			// Intent ichkout = new Intent(thiscontext, CalenderView.class);
			// ichkout.putExtra("txtchkin", 2);
			//
			// startActivity(ichkout);

			break;
		case R.id.btnSearch:
			if (jsonStr != null) {
				if (spinearcity.getSelectedItem().toString() == "Select City") {
					Toast.makeText(thiscontext, "Please Select City",
							Toast.LENGTH_SHORT).show();
				} else {
					// from_date = txtdatemonthyearchkin.getText().toString();
					from_date = checkinDate;
					// from_date
					// =parseDateTo_yyyy_MM_dd(txtdatemonthyearchkin.getText().toString());
					to_date = checkoutDate;

					// to_date =
					// parseDateTo_yyyy_MM_dd(txtdatemonthyearchkout.getText().toString());
					Intent intent = new Intent(thiscontext,
							SearchedHoteList.class);
					intent.putExtra("from_date", from_date);
					intent.putExtra("to_date", to_date);
					intent.putExtra("city_id", city_id);
					intent.putExtra("max_adults", max_adults);
					intent.putExtra("max_children", max_children);
					intent.putExtra("position", SelectedPosition );
					System.out.println(from_date + " " + to_date + " "
							+ city_id + " " + max_adults + " " + max_children
							+ " ");
					startActivity(intent);
				}
			} else {
				Toast.makeText(thiscontext,
						"Somting Went worg , Check internet connection", 5)
						.show();
			}
			break;
		default:
			break;
		}

	}

	public void populatespinear() {
		final List<String> lables = new ArrayList<String>();

		for (int i = 0; i < citylist.size(); i++) {
			lables.add(citylist.get(i).getCityName());
			cityname.size();
		}
		lables.add("Select City");
		Log.e("array Size", "" + lables.size());
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
				thiscontext, android.R.layout.simple_dropdown_item_1line,
				lables) {
			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {

				View v = null;

				if (position == citylist.size()) {
					TextView tv = new TextView(getContext());
					tv.setHeight(0);
					tv.setPadding(15, 0, 0, 0);
					tv.setVisibility(View.GONE);
					v = tv;
				} else {

					v = super.getDropDownView(position, null, parent);
				}

				parent.setVerticalScrollBarEnabled(false);
				return v;
			}
		};

		spinearcity.setAdapter(spinnerAdapter);
		if (user_Prefrences.getUser_id() != "") {
			if (user_Prefrences.getCityID() != "") {
				spinearcity.setSelection(Integer.parseInt((user_Prefrences
						.getCityID())));
			} else {
				spinearcity.setSelection(citylist.size());
			}
		} else {
			spinearcity.setSelection(citylist.size());
		}

		spinearcity.setOnItemSelectedListener(typeSelectedListener);
	}

	private OnItemSelectedListener typeSelectedListener = new OnItemSelectedListener() {
		@SuppressWarnings("null")
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			Log.e("Selected pos", "" + position);
			SelectedPosition=position;
			user_Prefrences.setCityID("" + position);
			// Toast.makeText(thiscontext, ""+position, 3).show();
			if (position == citylist.size()) {

			} else {
				city_id = citylist.get(position).getId();
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	};

	// Spinear Adult
	private void spinearAdultmethod() {

		List<String> lables = new ArrayList<String>();
		lables.add("Adults");
		for (int i = 0; i < Adults.length; i++) {
			lables.add(Adults[i]);
		}

		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
				thiscontext, android.R.layout.simple_dropdown_item_1line,
				lables) {
			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {

				View v = null;

				if (position == 0) {
					TextView tv = new TextView(thiscontext);
					tv.setHeight(0);
					tv.setVisibility(View.GONE);
					v = tv;
				} else {

					v = super.getDropDownView(position, null, parent);
				}

				parent.setVerticalScrollBarEnabled(false);
				return v;
			}
		};

		spinearAdult.setAdapter(spinnerAdapter);
		spinearAdult.setOnItemSelectedListener(typeadultlistnear);

	}

	private OnItemSelectedListener typeadultlistnear = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if (position == 0) {
				max_adults = "" + 1;
			} else {
				max_adults = spinearAdult.getItemAtPosition(position)
						.toString();
			}
			System.out.println(max_adults);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	};

	// Spinear Child
	private void spinearchildmethod() {

		List<String> lables = new ArrayList<String>();
		lables.add("Child");
		for (int i = 0; i < Child.length; i++) {
			lables.add(Child[i]);
		}

		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
				thiscontext, android.R.layout.simple_dropdown_item_1line,
				lables) {
			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {

				View v = null;

				if (position == 0) {
					TextView tv = new TextView(thiscontext);
					tv.setHeight(0);
					tv.setVisibility(View.GONE);
					v = tv;
				} else {

					v = super.getDropDownView(position, null, parent);
				}

				parent.setVerticalScrollBarEnabled(false);
				return v;
			}
		};

		spinearchild.setAdapter(spinnerAdapter);
		spinearchild.setOnItemSelectedListener(typeChildlistnear);
	}

	private OnItemSelectedListener typeChildlistnear = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if (position == 0) {
				max_children = "" + 0;
			} else {
				max_children = spinearchild.getItemAtPosition(position)
						.toString();
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	};

	public void handleCallBack(String selectedDate) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		GregorianCalendar departureCal = getCalender(selectedDate);
		calenderCheckout.setDepartureCalender(departureCal);

		checkinDate = selectedDate;
		Log.e("chkin changed", "" + checkoutDate);
		String sTemp = dateFormate(checkinDate);
		Log.e("Date here", "" + sTemp);
		txtdatemonthyearchkin.setText(sTemp);
		// if (isRoundTrip) {
		// manageArrivalDateAsPerDeparture();
		// }
	}

	public void handleArrivalCallBack(String s) {
		// TODO Auto-generated method stub
		checkoutDate = s;
		Log.e("chkout changed", "" + checkoutDate);
		txtdatemonthyearchkout.setText(dateFormate(checkoutDate));
		// manageArrivalDateAsPerDeparture();

	}

	private GregorianCalendar getCalender(String date) {
		// TODO Auto-generated method stub
		String[] sTemp = date.split("-");
		GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(sTemp[0]));
		cal.set(Calendar.MONTH, (Integer.parseInt(sTemp[1])) - 1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sTemp[2]));
		return cal;
	}

	private String dateFormate(String date) {
		// TODO Auto-generated method stub
		try {
			String[] sTemp = date.split("-");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(sTemp[0]));
			cal.set(Calendar.MONTH, (Integer.parseInt(sTemp[1])) - 1);
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sTemp[2]));
			SimpleDateFormat sdf = new SimpleDateFormat();
			String DATE_FORMAT = "EEE, dd MMM"; // yyyy-MM-dd
			sdf.applyPattern(DATE_FORMAT);
			return sdf.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
	}
	// public String parseDateTo_yyyy_MM_dd (String dateStr) {
	// String inputPattern = "EEE, dd MMM";
	// String outputPattern = "yy-MM-dd";
	// SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
	// SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
	//
	// Date date = null;
	// String str = null;
	//
	// try {
	// date = inputFormat.parse(dateStr);
	// str = outputFormat.format(date);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// Log.e("Parsed date", str);
	//
	// return str;
	// }
}
