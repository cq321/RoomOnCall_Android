package com.onjection.myaccountDetails;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import serverTask.ConnectionDector;
import serverTask.ServerDownload;
import serverTask.ServerResponse;

import com.onjection.handler.User_Prefrences;
import com.onjection.handler.Utilz;
import com.onjection.roomoncall.MainSearchHotel;
import com.onjection.roomoncall.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class Login extends Activity implements OnClickListener, ServerResponse {
	Button btnLogin, btnregister;
	Context context;
	EditText edtLoginEmail, edtLoginPass;
	ProgressDialog pd;
	ConnectionDector connectionDector = new ConnectionDector(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.login);
		context = Login.this;

		// Login
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnregister = (Button) findViewById(R.id.btnregister);
		edtLoginEmail = (EditText) findViewById(R.id.edtLoginEmail);
		edtLoginPass = (EditText) findViewById(R.id.edtLoginPass);
		// Register

		btnLogin.setOnClickListener(this);
		btnregister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btnLogin:
			Utilz.hideKeyboard(Login.this);
			doNormalLogin();
			break;
		case R.id.btnregister:
			Intent iregister = new Intent(context, Registeration.class);
			startActivity(iregister);
			finish();
			break;

		}
	}

	private void doNormalLogin() {

		if (edtLoginEmail.getText().toString().length() <= 0) {
			Toast.makeText(context, "Provide email address.", Toast.LENGTH_LONG)
					.show();
		} /*
		 * else if (!Utilz.isValidEmail1(edtLoginEmail.getText().toString())) {
		 * Toast.makeText(context, "Provide valid email.", Toast.LENGTH_LONG)
		 * .show(); }
		 */else if (edtLoginPass.getText().toString().length() <= 0) {
			Toast.makeText(context, "Provide password.", Toast.LENGTH_LONG)
					.show();

		} else if (!Utilz.isInternetConnected(context)) {
			Toast.makeText(context, "Check internet connection.",
					Toast.LENGTH_LONG).show();
		} else {

			if (connectionDector.isConnectingToInternet()) {
				// Progress Dialog initalisation
				pd = ProgressDialog.show(this, "", "Login", false);
				ServerDownload download = new ServerDownload(Login.this, "",
						Login.this, "login");
				download.execute(postRequest());
				// Intent intent = new Intent(Login_Screen.this,
				// DrawerScreen.class);
				// startActivity(intent);
				// finish();

			} else {
				Toast.makeText(getApplicationContext(),
						"No Neteork Connection", Toast.LENGTH_SHORT).show();
			}
			// new ExecuteLogin().execute(Urls.NormalLoginUrl);
		}
	}

	private void forGetPassword() {

		// TODO Auto-generated method stub

		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.forgot_password, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText edtForgPass = (EditText) promptsView
				.findViewById(R.id.edtForgPass);

		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						if (!Utilz.isInternetConnected(context)) {
							Toast.makeText(context,
									"Check your internet connnection.",
									Toast.LENGTH_LONG).show();

							return;
						}

						else if (edtForgPass.getText().toString().trim()
								.length() < 1) {
							Toast.makeText(context,
									"Provide your registered emailId.",
									Toast.LENGTH_LONG).show();
						} else if (!Utilz.isValidEmail1(edtForgPass.getText()
								.toString())) {
							Toast.makeText(context,
									"Please enter valid emailId",
									Toast.LENGTH_LONG).show();
						} else if (containsWhiteSpace(edtForgPass.getText()
								.toString().trim())) {
							Toast.makeText(context,
									"Please enter ealid emailId",
									Toast.LENGTH_LONG).show();
						}

						else {
							// emailId = edtForgPass.getText().toString();
							new ForgotPassword().execute(edtForgPass.getText()
									.toString());

						}
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	private class ForgotPassword extends AsyncTask<String, Void, String> {

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(context, "Retriving password",
					"Wait...");
			progressDialog.setCancelable(false);
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = null;

			try {
				ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();
				arrayList
						.add(new BasicNameValuePair("method", "forgetPassword"));
				arrayList.add(new BasicNameValuePair("email", params[0]));

				// result = Utilz.executeHttpPost(Urls.ForgotPasswordUrl,
				// arrayList);
			} catch (Exception e) {
				// TODO: handle exception

			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (progressDialog != null) {
				progressDialog.dismiss();
			}

			// {"isSuccess":"true","success_msg":"Successfully password sent on your email. "}

			// {"isSuccess":"false","success_msg":"User is not exist!"}
			if (result != null) {
				try {
					JSONObject object = new JSONObject(result);
					String isSuccess = object.getString("isSuccess");
					String success_msg = object.getString("success_msg");

					Toast.makeText(context, success_msg, Toast.LENGTH_LONG)
							.show();

				} catch (Exception e) {

				}
			} else {
				// Toast.makeText(context, "Please try again!",
				// Toast.LENGTH_LONG)
				// .show();
			}
		}

	}

	boolean containsWhiteSpace(final String testCode) {
		if (testCode != null) {
			for (int i = 0; i < testCode.length(); i++) {
				if (Character.isWhitespace(testCode.charAt(i))) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressLint("ShowToast")
	@Override
	public void httpResponse(JSONObject jsonObject, String tag, int responceCode) {
		// TODO Auto-generated method stub
		pd.dismiss();
		Log.e("responce", "" + jsonObject);
		if (tag.equals("login")) {
			if (jsonObject != null) {
				if (jsonObject.optString("user_name").equals(
						edtLoginEmail.getText().toString())
						&& jsonObject.optString("user_password").equals(
								MD5Digits(edtLoginPass.getText().toString()))) {
//					Log.e("Password",  MD5Digits(edtLoginPass.getText()
//							.toString()));
//					Log.e("Password", edtLoginEmail.getText().toString());
//					Log.e("username", jsonObject.optString("user_password"));
//					Log.e("username", jsonObject.optString("user_name"));
					Toast.makeText(context, "Login Sucessfull",
							Toast.LENGTH_LONG).show();
					User_Prefrences userPrefrences = new User_Prefrences(
							Login.this);
					userPrefrences.setUser_firstName(jsonObject
							.optString("first_name"));
					userPrefrences.setUser_lastname(jsonObject
							.optString("last_name"));
					userPrefrences.setUser_email(jsonObject.optString("email"));
					userPrefrences.setUser_id(jsonObject.optString("id"));
					userPrefrences.setUserAdd(jsonObject.optString("b_address")
							+ "," + jsonObject.optString("b_address_2") + ","
							+ jsonObject.optString("b_city"));
					userPrefrences.setUserComp(jsonObject.optString("company"));
					userPrefrences.setUserMob(jsonObject.optString("phone"));
					userPrefrences.setPassword(edtLoginPass.getText()
							.toString());
					userPrefrences.setUserName(jsonObject
							.optString("first_name")
							+ " "
							+ jsonObject.optString("last_name"));
					Intent ihomeinetent = new Intent(Login.this,
							MainSearchHotel.class);
					startActivity(ihomeinetent);
					finish();
				} else {
					Toast.makeText(context, "Please Check UserName & Password",
							Toast.LENGTH_LONG).show();
				}

			} else {
				/*
				 * Toast.makeText(context,
				 * "Please Check Internet Connection Something Wrong",
				 * Toast.LENGTH_LONG).show();
				 */
				Utilz.showAlertActivity(
						"Please Check Internet Connection Something Wrong",
						context);
			}
		}
	}

	String postRequest() {

		String request = "{\"username\":\""
				+ edtLoginEmail.getText().toString() + "\",\"password\":\""
				+ edtLoginPass.getText().toString() + "\"}";

		return request;
	}

	String MD5Digits(String s) {
		String original = s;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md.update(original.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();

	}
}
