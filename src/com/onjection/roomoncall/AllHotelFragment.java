package com.onjection.roomoncall;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.onjection.adapter.AllHotelListAdapter;
import com.onjection.handler.ServiceHandler;
import com.onjection.model.HotelData;



import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AllHotelFragment extends Fragment {
	private ProgressDialog pd;
	private static String urlsearch = "http://www.roomoncall.in/api/get_hotels";
	private List<HotelData> hoteldatalist = new ArrayList<HotelData>();
	private ListView listView;
	Context thiscontext;
	private AllHotelListAdapter adapter;

	public AllHotelFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		thiscontext = container.getContext();
		View rootView = inflater.inflate(R.layout.all_hotel_fargement, container,
				false);
		listView = (ListView) rootView.findViewById(R.id.listView1);

		new GetSearch().execute();
		return rootView;
	}

	public class GetSearch extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pd = new ProgressDialog(thiscontext);
			pd.setMessage("Please wait...");
			pd.setCancelable(false);
			pd.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(urlsearch, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				JSONArray jsonArray = null;
				try {
					jsonArray = new JSONArray(jsonStr);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						HotelData hotel = new HotelData();
						hotel.setLowest_price(obj.getString("lowest_price"));
						hotel.setRating(obj.getString("stars"));
						hotel.setTxthotelname(obj.getString("name"));
						hotel.setHotelid(obj.getInt("id"));
						hotel.setHotelimage(obj.getString("hotel_image"));
						hoteldatalist.add(hotel);

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

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pd.isShowing())
				pd.dismiss();
			adapter = new AllHotelListAdapter(thiscontext, hoteldatalist);
			listView.setAdapter(adapter);
			/**
			 * Updating parsed JSON data into ListView
			 * */

		}

	}
}
