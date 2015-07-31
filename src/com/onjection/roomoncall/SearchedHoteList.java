package com.onjection.roomoncall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.onjection.adapter.CustomFilterHotel;
import com.onjection.handler.User_Prefrences;
import com.onjection.model.NewFilterhoteldetails;
import com.onjection.model.Roomdetails;
import com.onjection.model.Searchmodel;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchedHoteList extends Activity implements
		OnItemSelectedListener {
	String city_id, from_date, to_date, max_adults = "1", max_children = "0",
			hotel_sel_loc_id;
	ListView listView;
	private List<NewFilterhoteldetails> newfilterlist = new ArrayList<NewFilterhoteldetails>();
	private CustomFilterHotel adapter;
	Button backbutton;
	int position;
	private ProgressDialog pd;
	Spinner spinearcity;

	User_Prefrences user_Prefrences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.search_filter_hotels);
		user_Prefrences = new User_Prefrences(this);
		backbutton = (Button) findViewById(R.id.iv_back);
		TextView tvHeader = (TextView) findViewById(R.id.headerText);
		tvHeader.setText("Hotels");
		listView = (ListView) findViewById(R.id.listView1);

		Bundle bundle = getIntent().getExtras();

		from_date = bundle.getString("from_date");
		to_date = bundle.getString("to_date");
		city_id = bundle.getString("city_id");
		max_adults = bundle.getString("max_adults");
		max_children = bundle.getString("max_children");
		position = bundle.getInt("position");
		spinearcity = (Spinner) findViewById(R.id.spinearcity);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				SearchHotelFragment.arrCityName);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinearcity.setAdapter(dataAdapter);
		spinearcity.setSelection(position);
		spinearcity.setOnItemSelectedListener(this);
		// hotel_sel_loc_id = bundle.getString("hotel_sel_loc_id");
		new HttpAsyncTask().execute("http://www.roomoncall.in/api/search");
		backbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SearchedHoteList.this.finish();
			}
		});
	}

	public static String POST(String url, Searchmodel hotel) {
		InputStream inputStream = null;
		String result = "";
		try {

			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// 2. make POST request to the given URL
			HttpPost httpPost = new HttpPost(url);

			String json = "";

			// 3. build jsonObject
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("from_date", hotel.getFrom_date());
			jsonObject.accumulate("to_date", hotel.getTo_date());
			jsonObject.accumulate("city_id", hotel.getCity_id());
			// jsonObject.accumulate("max_children", hotel.getMax_children());
			//
			// jsonObject.accumulate("max_adults", hotel.getMax_children());

			/*
			 * jsonObject.accumulate("		hotel_sel_loc_id",
			 * hotel.getHotel_sel_loc_id());
			 */

			// 4. convert JSONObject to JSON to String
			json = jsonObject.toString();

			// ** Alternative way to convert Person object to JSON string usin
			// Jackson Lib
			// ObjectMapper mapper = new ObjectMapper();
			// json = mapper.writeValueAsString(person);

			// 5. set json to StringEntity
			StringEntity se = new StringEntity(json);

			// 6. set httpPost Entity
			httpPost.setEntity(se);

			// 7. Set some headers to inform server about the type of the
			// content
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpPost);

			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// 10. convert inputstream to string
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		// 11. return result
		return result;
	}

	public boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}

	class HttpAsyncTask extends AsyncTask<String, Void, String> {
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pd = new ProgressDialog(SearchedHoteList.this);
			pd.setMessage("Please wait...");
			pd.setCancelable(false);
			pd.show();

		}

		@Override
		protected String doInBackground(String... urls) {

			Searchmodel hotel = new Searchmodel();
			hotel.setCity_id(city_id);
			hotel.setFrom_date(from_date);
			hotel.setMax_adults(max_adults);
			/* hotel.setHotel_sel_loc_id(hotel_sel_loc_id); */
			hotel.setTo_date(to_date);
			hotel.setMax_children(max_children);

			return POST(urls[0], hotel);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			if (pd.isShowing())
				pd.dismiss();
			System.out.println(result);

			if (result != null) {
				JSONArray jsonArray = null;
				try {
					jsonArray = new JSONArray(result);
					if (jsonArray.length() != 0) {

						Log.e("JsonArry Length", jsonArray.length() + "");
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject obj = jsonArray.getJSONObject(i);

							NewFilterhoteldetails newfilter = new NewFilterhoteldetails(
									obj.getString("name"),
									obj.getString("hotel_image"),
									obj.getString("city"),
									obj.getString("phone"),
									obj.getString("email"), obj
											.getJSONArray("rooms")
											.getJSONObject(0)
											.getString("default_price"),
									obj.getString("address"),
									obj.getString("hotel_id"),
									obj.getString("location"));
							newfilterlist.add(newfilter);

						}
					} else {
						Toast.makeText(SearchedHoteList.this, "No Hotel Found",
								6).show();

					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			adapter = new CustomFilterHotel(SearchedHoteList.this,
					newfilterlist);
			listView.setAdapter(adapter);
		}
	}

	/*
	 * private boolean validate() { if
	 * (etName.getText().toString().trim().equals("")) return false; else if
	 * (etCountry.getText().toString().trim().equals("")) return false; else if
	 * (etTwitter.getText().toString().trim().equals("")) return false; else
	 * return true; }
	 */
	// public void populatespinear() {
	// final List<String> lables = new ArrayList<String>();
	//
	// for (int i = 0; i < citylist.size(); i++) {
	// lables.add(citylist.get(i).getCityName());
	// cityname.size();
	// }
	// lables.add("Select City");
	// Log.e("array Size", "" + lables.size());
	// ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
	// thiscontext, android.R.layout.simple_dropdown_item_1line,
	// lables) {
	// @Override
	// public View getDropDownView(int position, View convertView,
	// ViewGroup parent) {
	//
	// View v = null;
	//
	// if (position == citylist.size()) {
	// TextView tv = new TextView(getContext());
	// tv.setHeight(0);
	// tv.setPadding(15, 0, 0, 0);
	// tv.setVisibility(View.GONE);
	// v = tv;
	// } else {
	//
	// v = super.getDropDownView(position, null, parent);
	// }
	//
	// parent.setVerticalScrollBarEnabled(false);
	// return v;
	// }
	// };
	//
	// spinearcity.setAdapter(spinnerAdapter);
	// if (user_Prefrences.getUser_id() != "") {
	// if (user_Prefrences.getCityID() != "") {
	// spinearcity.setSelection(Integer.parseInt((user_Prefrences
	// .getCityID())));
	// } else {
	// spinearcity.setSelection(citylist.size());
	// }
	// } else {
	// spinearcity.setSelection(citylist.size());
	// }
	//
	// spinearcity.setOnItemSelectedListener(typeSelectedListener);
	// }

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (this.position != position) {
			if (newfilterlist.size() > 0) {
				newfilterlist.clear();
				this.position=9999999;
			}
			adapter.notifyDataSetChanged();
			city_id = SearchHotelFragment.arrCityID.get(position);
			new HttpAsyncTask().execute("http://www.roomoncall.in/api/search");
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}
