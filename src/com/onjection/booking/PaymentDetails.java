package com.onjection.booking;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onjection.roomoncall.R;
import com.payu.sdk.Constants;
import com.payu.sdk.PayU;

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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentDetails extends Activity {

	ProgressDialog mProgressDialog;

	String totalamout, bookingID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mProgressDialog = new ProgressDialog(PaymentDetails.this);

		if (savedInstanceState == null) {
			setContentView(R.layout.activity_main);
		}
		Bundle bundle = getIntent().getExtras();
		bookingID = bundle.getString("bookingID");
		totalamout = bundle.getString("TotalAmount");
		Log.e("Amount pay", totalamout);
		// findViewById(R.id.add).setOnClickListener(new View.OnClickListener()
		// {
		// @Override
		// public void onClick(View view) {
		// LinearLayout linearLayout = (LinearLayout) findViewById(R.id.params);
		// linearLayout.addView(
		// getLayoutInflater().inflate(R.layout.param, null),
		// linearLayout.getChildCount() - 2);
		// }
		// });

		findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LinearLayout linearLayout = (LinearLayout) findViewById(R.id.params);
				final HashMap<String, String> params = new HashMap<String, String>();
				double amount = 10;
				for (int i = 0; i < linearLayout.getChildCount() - 2; i++) {
					LinearLayout param = (LinearLayout) linearLayout
							.getChildAt(i);
					if (((TextView) param.getChildAt(0)).getText().toString()
							.equals("amount")) {
						amount = Double.parseDouble("" + totalamout);
					}
					params.put(((TextView) param.getChildAt(0)).getText()
							.toString(), ((EditText) param.getChildAt(1))
							.getText().toString());
				}
				// String hash = calculateHash(params);
				params.remove("amount");
				// params.put("hash", hash);

				if (!Constants.SDK_HASH_GENERATION) { // cool we gotta fetch
														// hash from the server.

					mProgressDialog.setCancelable(false);
					mProgressDialog
							.setMessage("Calculating Hash. please wait..");
					mProgressDialog.show();

					final double finalAmount = Double.parseDouble(totalamout);
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
								mProgressDialog.dismiss();

								// PayU.getInstance(MainActivity.this).startPaymentProcess(finalAmount,
								// params);
								PayU.getInstance(PaymentDetails.this)
										.startPaymentProcess(
												finalAmount,
												params,
												new PayU.PaymentMode[] {
														PayU.PaymentMode.CC,
														PayU.PaymentMode.NB });

							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
								/*
								 * if(mProgressDialog.isShowing())
								 * mProgressDialog.dismiss();
								 */
								Toast.makeText(PaymentDetails.this,
										e.getMessage(), Toast.LENGTH_LONG)
										.show();
							} catch (ClientProtocolException e) {
								e.printStackTrace();
								/*
								 * if(mProgressDialog.isShowing())
								 * mProgressDialog.dismiss();
								 */
								Toast.makeText(PaymentDetails.this,
										e.getMessage(), Toast.LENGTH_LONG)
										.show();
							} catch (JSONException e) {
								if (mProgressDialog.isShowing())
									mProgressDialog.dismiss();
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
								/*
								 * if(mProgressDialog.isShowing())
								 * mProgressDialog.dismiss();
								 */
								Toast.makeText(PaymentDetails.this,
										e.getMessage(), Toast.LENGTH_LONG)
										.show();
							} catch (PackageManager.NameNotFoundException e) {
								e.printStackTrace();
								/*
								 * if(mProgressDialog.isShowing())
								 * mProgressDialog.dismiss();
								 */
								Toast.makeText(PaymentDetails.this,
										e.getMessage(), Toast.LENGTH_LONG)
										.show();
							}
							return null;
						}
					}.execute();
				} else {
					PayU.getInstance(PaymentDetails.this).startPaymentProcess(
							amount, params);
					// PayU.getInstance(MainActivity.this).startPaymentProcess(amount,
					// params, new PayU.PaymentMode[]{PayU.PaymentMode.CC,
					// PayU.PaymentMode.NB});
				}

			}
		});

		((EditText) findViewById(R.id.txn)).setText(String.valueOf(System
				.currentTimeMillis()));

		String version = "version 2.1 sms";

		Toast.makeText(
				PaymentDetails.this,
				Constants.DEBUG ? "Debug build " + version
						: "Production build " + version, Toast.LENGTH_SHORT)
				.show();
		Toast.makeText(
				PaymentDetails.this,
				Constants.SDK_HASH_GENERATION ? "SDK is generating Hash "
						: "SDK fetches hash form API", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PayU.RESULT) {
			if (resultCode == RESULT_OK) {
				// success
				if (data != null)
					Toast.makeText(this,
							"Success:  " + data.getStringExtra("result"),
							Toast.LENGTH_LONG).show();
				Log.e("payment Sucessfull",
						"Success:  " + data.getStringExtra("result"));
			}
			if (resultCode == RESULT_CANCELED) {
				// failed
				if (data != null)
					Toast.makeText(this,
							"Failed:  " + data.getStringExtra("result"),
							Toast.LENGTH_LONG).show();
			}
		}
	}
}
