package com.onjection.booking;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import serverTask.ServerDownload;
import serverTask.ServerResponse;

import com.onjection.handler.User_Prefrences;
import com.onjection.myaccountDetails.Login;
import com.onjection.roomoncall.R;
import com.onjection.roomoncall.ThankYou;
import com.payu.sdk.Constants;
import com.payu.sdk.PayU;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserPaymentDetails extends Activity implements ServerResponse {
	TextView txtUserName, txtuserId, txtUserEnail, TxtuserCompny,
			TxtUserAddress, txtUserMob, txtNoOfRoom, txtroomtype, tvExtBed,
			tvAdlts, tvChild, tvTotal;
	EditText edtCoupon;
	Button btnpaynow, backbutton;
	ProgressDialog pd;
	String bookingID;
    boolean sucessfullPayment=false;
	String hotelId,hotelNmae, extraBed, adults, rooms, Amount, amountRes , roomtype, roomId;
	User_Prefrences user_Prefrences;
	// <input type=hidden name=surl
	// value='http://www.roomoncall.in/dev/index.php?page=booking_notify_payu'/><input
	// type=hidden name=furl
	// value='http://www.roomoncall.in/dev/index.php?page=booking_return'/><input
	// type=hidden name=curl
	// value='http://www.roomoncall.in/dev/index.php?page=booking_cancel'/>
	// String surl =
	// "http://www.roomoncall.in/dev/index.php?page=booking_notify_payu";09
	// String furl =
	// "http://www.roomoncall.in/dev/index.php?page=booking_return";
	// String curl =
	// "http://www.roomoncall.in/dev/index.php?page=booking_cancel";
	String surl = "https://dl.dropboxusercontent.com/s/dtnvwz5p4uymjvg/success.html";
	String furl = "https://dl.dropboxusercontent.com/s/z69y7fupciqzr7x/furlWithParams.html";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.user_payment_details);
		user_Prefrences = new User_Prefrences(this);
		Bundle bundle = getIntent().getExtras();
		hotelId = bundle.getString("hotel_id");
		extraBed = bundle.getString("extra_beds");
		hotelNmae=bundle.getString("hotel_name");
		adults = bundle.getString("adults");
		rooms = bundle.getString("rooms");
		roomtype = bundle.getString("roomtype");
		Amount = bundle.getString("Amount");
		roomId = bundle.getString("roomId");
		GetAlluserId();
		btnpaynow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(UserPaymentDetails.this, "In Progress", 3)
						.show();

				pd = ProgressDialog.show(UserPaymentDetails.this, "",
						"Please Wait", false);
				ServerDownload download = new ServerDownload(
						UserPaymentDetails.this, "", UserPaymentDetails.this,
						"reservation");
				download.execute(postRequestForReservation());

			}
		});
		backbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UserPaymentDetails.this.finish();
			}
		});
	}

	private void GetAlluserId() {
		txtUserName = (TextView) findViewById(R.id.txtUserName);

		txtuserId = (TextView) findViewById(R.id.txtuserId);

		txtUserEnail = (TextView) findViewById(R.id.txtUserEnail);
		TxtuserCompny = (TextView) findViewById(R.id.TxtuserCompny);
		TxtUserAddress = (TextView) findViewById(R.id.TxtUserAddress);
		txtUserMob = (TextView) findViewById(R.id.txtUserMob);
		txtNoOfRoom = (TextView) findViewById(R.id.txtNoOfRoom);
		txtroomtype = (TextView) findViewById(R.id.txtroomtype);
		tvAdlts = (TextView) findViewById(R.id.txtAdults);
		tvChild = (TextView) findViewById(R.id.txtChild);
		tvExtBed = (TextView) findViewById(R.id.txtExtBed);
		tvTotal = (TextView) findViewById(R.id.txtTotal);
		edtCoupon = (EditText) findViewById(R.id.edtCupon);

		btnpaynow = (Button) findViewById(R.id.btnpaynow);
		backbutton = (Button) findViewById(R.id.iv_back);
		TextView tvHeader= (TextView) findViewById(R.id.headerText);
		tvHeader.setText("Payment Details");

		txtNoOfRoom.setText(rooms);
		txtroomtype.setText(roomtype);
		txtUserEnail.setText(user_Prefrences.getUser_email());
		txtuserId.setText(user_Prefrences.getUser_id());
		txtUserMob.setText(user_Prefrences.getUserMob());
		txtUserName.setText(user_Prefrences.getUserName());
		TxtuserCompny.setText(user_Prefrences.getUserComp());
		TxtUserAddress.setText(user_Prefrences.getUserAdd());
		tvAdlts.setText(adults);
		tvChild.setText(0 + "");
		tvExtBed.setText(extraBed);
//		string showStrAmount = 
		tvTotal.setText(""+(Double .parseDouble(Amount)+(Double .parseDouble(Amount)*.1236))+"  service tax added");

	}

	@Override
	public void httpResponse(JSONObject jsonObject, String tag, int responceCode) {
		// TODO Auto-generated method stub

		if (tag.equals("reservation")) {

			if (jsonObject != null) {
				if (jsonObject.opt("status").equals("success")) {
					bookingID = jsonObject.optString("booking_id");
					amountRes = jsonObject.optString("amount");
					Log.e("booking", bookingID);
					Log.e("Amount", Amount);

				} else {
					Toast.makeText(UserPaymentDetails.this,
							"" + jsonObject.opt("status"), 3).show();
				}

				LinearLayout linearLayout = (LinearLayout) findViewById(R.id.params);
				final HashMap<String, String> params = new HashMap<String, String>();
				double amount = Double.parseDouble(amountRes);
				// for (int i = 0; i < linearLayout.getChildCount() - 2; i++) {
				// LinearLayout param = (LinearLayout) linearLayout
				// .getChildAt(i);
				// if (((TextView) param.getChildAt(0)).getText().toString()
				// .equals("amount")) {
				// amount = Double.parseDouble("" + amount);
				// }
				params.put("amount", amountRes);
				params.put("surl", surl);
				params.put("furl", furl);
				params.put("productinfo", "RoomOnCall");
				params.put("txnid", bookingID);
				// }
				// String hash = calculateHash(params);
				// params.put(((TextView) param.getChildAt(0)).getText()
				// .toString(), ((EditText) param.getChildAt(1))
				// .getText().toString());
				params.remove("amount");
				// params.put("hash", hash);

				if (!Constants.SDK_HASH_GENERATION) { // cool we gotta fetch
														// hash from the server.

					pd.setCancelable(false);
					pd.setMessage("Calculating Hash. please wait..");
					pd.show();

					final double finalAmount = Double.parseDouble(amountRes);
					new AsyncTask<Void, Void, Void>() {
						@Override
						protected Void doInBackground(Void... voids) {
							try {
								HttpClient httpclient = new DefaultHttpClient();
								HttpPost httppost = new HttpPost(
										Constants.FETCH_DATA_URL);
								List<NameValuePair> postParams = new ArrayList<NameValuePair>(
										5);
								postParams.add(new BasicNameValuePair(
										"command", "mobileHashTestWs"));
								postParams
										.add(new BasicNameValuePair(
												"key",
												(getPackageManager()
														.getApplicationInfo(
																getPackageName(),
																PackageManager.GET_META_DATA).metaData)
														.getString("payu_merchant_id")));
								postParams.add(new BasicNameValuePair("var1",
										params.get("txnid")));
								postParams.add(new BasicNameValuePair("var2",
										String.valueOf(finalAmount)));
								postParams.add(new BasicNameValuePair("var3",
										params.get("productinfo")));
								postParams.add(new BasicNameValuePair("var4",
										params.get("user_credentials")));
								postParams.add(new BasicNameValuePair("hash",
										"helo"));
								httppost.setEntity(new UrlEncodedFormEntity(
										postParams));
								JSONObject response = new JSONObject(
										EntityUtils.toString(httpclient
												.execute(httppost).getEntity()));

								// set the hash values here.

								if (response.has("result")) {
									PayU.merchantCodesHash = response
											.getJSONObject("result").getString(
													"merchantCodesHash");
									PayU.paymentHash = response.getJSONObject(
											"result").getString("paymentHash");
									PayU.vasHash = response.getJSONObject(
											"result").getString("mobileSdk");
									PayU.ibiboCodeHash = response
											.getJSONObject("result").getString(
													"detailsForMobileSdk");

									if (response.getJSONObject("result").has(
											"deleteHash")) {
										PayU.deleteCardHash = response
												.getJSONObject("result")
												.getString("deleteHash");
										PayU.getUserCardHash = response
												.getJSONObject("result")
												.getString("getUserCardHash");
										PayU.editUserCardHash = response
												.getJSONObject("result")
												.getString("editUserCardHash");
										PayU.saveUserCardHash = response
												.getJSONObject("result")
												.getString("saveUserCardHash");
									}

								}
								pd.dismiss();

								// PayU.getInstance(MainActivity.this).startPaymentProcess(finalAmount,
								// params);
								PayU.getInstance(UserPaymentDetails.this)
										.startPaymentProcess(
												finalAmount,
												params,
												new PayU.PaymentMode[] {
														PayU.PaymentMode.CC,
														PayU.PaymentMode.NB });

							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
								/*
								 * if(pd.isShowing()) pd.dismiss();
								 */
								Toast.makeText(UserPaymentDetails.this,
										e.getMessage(), Toast.LENGTH_LONG)
										.show();
							} catch (ClientProtocolException e) {
								e.printStackTrace();
								/*
								 * if(pd.isShowing()) pd.dismiss();
								 */
								Toast.makeText(UserPaymentDetails.this,
										e.getMessage(), Toast.LENGTH_LONG)
										.show();
							} catch (JSONException e) {
								if (pd.isShowing())
									pd.dismiss();
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
								/*
								 * if(pd.isShowing()) pd.dismiss();
								 */
								Toast.makeText(UserPaymentDetails.this,
										e.getMessage(), Toast.LENGTH_LONG)
										.show();
							} catch (PackageManager.NameNotFoundException e) {
								e.printStackTrace();
								/*
								 * if(pd.isShowing()) pd.dismiss();
								 */
								Toast.makeText(UserPaymentDetails.this,
										e.getMessage(), Toast.LENGTH_LONG)
										.show();
							}
							return null;
						}
					}.execute();
				} else {
					PayU.getInstance(UserPaymentDetails.this)
							.startPaymentProcess(amount, params);
					// PayU.getInstance(MainActivity.this).startPaymentProcess(amount,
					// params, new PayU.PaymentMode[]{PayU.PaymentMode.CC,
					// PayU.PaymentMode.NB});
				}

			}
		} else if (tag.equals("confirm_reservation")) {
			if (pd.isShowing()) {
				pd.dismiss();
				if (jsonObject != null) {
					if(sucessfullPayment){
						Log.e("Sucess full", "Payment and confirm");
						Log.e("jsonObject full", "   " + jsonObject);
						Intent intent =new Intent(UserPaymentDetails.this, ThankYou.class);
						intent.putExtra("Hotel", hotelNmae);
						intent.putExtra("HotelID", hotelId);
						intent.putExtra("bookingid", bookingID);
						intent.putExtra("Amount", amountRes);
						startActivity(intent);
					}else {
						Toast.makeText(UserPaymentDetails.this, "Payment failure Try Again", 3).show();
					}
					

				}
			}

		}

	}

	// {
	// "room_id":"5",
	// "hotel_id":"12",
	// "from_date":"2015-04-20",
	// "to_date":"2015-04-30",
	// "adults":"2",
	// "children":"0",
	// "rooms":"2",
	// "extra_beds":"0",
	// "meal_plan_id":"",
	// "vat_percent":"12.36",
	// "customer_id":"2",
	// "coupon_code":"DQVW-891R-NR8F-4R1X"
	// }

	String postRequestForReservation() {
		JSONObject postoObject = new JSONObject();
		try {

			postoObject.put("hotel_id", hotelId);
			postoObject.put("from_date", "2015-06-20");
			postoObject.put("to_date", "2015-06-22");
			postoObject.put("adults", adults);
			postoObject.put("children", "0");
			postoObject.put("rooms", rooms);
			postoObject.put("room_id", roomId);
			postoObject.put("extra_beds", extraBed);
			postoObject.put("meal_plan_id", "");
			postoObject.put("vat_percent", "12.36");
			postoObject.put("customer_id", txtuserId.getText().toString());
			postoObject.put("coupon_code", "");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("postRequestForReservation", postoObject.toString());
		return postoObject.toString();
	}

	// {
	// "booking_id":"CWSKV9775T",
	// "customer_id":"2",
	// "pre_payment_type":"full",
	// "pre_payment_value":"148",
	// "payment_sum":"148",
	// "status":"3",
	// "payment_type":"6",
	// "payment_method":"3",
	// "transaction_number":"ABCD7692"
	// }

	// faliure =5 , sucess= 7
	String postRequestForConfirmReservation() {
		JSONObject postoObject = new JSONObject();
		try {

			postoObject.put("booking_id", bookingID);
			postoObject.put("pre_payment_type", "full price");
			postoObject.put("pre_payment_value", "" + amountRes);
			postoObject.put("payment_sum", "" + amountRes);
			postoObject.put("status", "7");
			postoObject.put("payment_type", "6");
			postoObject.put("payment_method", "3");
			postoObject.put("transaction_number", bookingID);
			postoObject.put("customer_id", txtuserId.getText().toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("postRequestForConfirm", postoObject.toString());
		return postoObject.toString();
	}

	String postRequestForFailedReservation() {
		JSONObject postoObject = new JSONObject();
		try {

			postoObject.put("booking_id", bookingID);
			postoObject.put("pre_payment_type", "full price");
			postoObject.put("pre_payment_value", "" + 00);
			postoObject.put("payment_sum", "" + 00);
			postoObject.put("status", "5");
			postoObject.put("payment_type", "6");
			postoObject.put("payment_method", "3");
			postoObject.put("transaction_number", bookingID);
			postoObject.put("customer_id", txtuserId.getText().toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("postRequestForPayU", postoObject.toString());
		return postoObject.toString();
	}

	// void ShowDiologe(String Str){
	// // Create custom dialog object
	// final Dialog dialog = new Dialog(UserPaymentDetails.this);
	// // Include dialog.xml file
	// dialog.setContentView(R.layout.progressdialog);
	// // Set dialog title
	// dialog.setTitle("Room On Call");
	//
	// // set values for custom dialog components - text, image and button
	// TextView text = (TextView) dialog.findViewById(R.id.textDialog);
	// text.setText(s);
	// ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
	// image.setImageResource(R.drawable.image0);
	//
	// dialog.show();
	//
	// Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
	// // if decline button is clicked, close the custom dialog
	// declineButton.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// // Close dialog
	// dialog.dismiss();
	// }
	// });
	//
	//
	// }
	// }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == PayU.RESULT) {
			if (resultCode == RESULT_OK) {
				sucessfullPayment =true;
				// success
				if (data != null)
					Toast.makeText(
							this,
							"Payment Successfull:  "
									+ data.getStringExtra("result"),
							Toast.LENGTH_LONG).show();
				Log.e("payment Sucessfull",
						"Success:  " + data.getStringExtra("result"));
				ServerDownload download = new ServerDownload(
						UserPaymentDetails.this, "", UserPaymentDetails.this,
						"confirm_reservation");
				download.execute(postRequestForConfirmReservation());

			}
			if (resultCode == RESULT_CANCELED) {
				// failed
				sucessfullPayment =false;
				if (data != null)
					Log.e("payment Fail",
							"Success:  " + data.getStringExtra("result"));
					Toast.makeText(this,
							"Failed:  " + data.getStringExtra("result"),
							Toast.LENGTH_LONG).show();
				ServerDownload download = new ServerDownload(
						UserPaymentDetails.this, "", UserPaymentDetails.this,
						"confirm_reservation");
				download.execute(postRequestForFailedReservation());

			}
		}
	}
}
