package com.onjection.myaccountDetails;

import org.json.JSONObject;

import serverTask.ConnectionDector;
import serverTask.ServerDownload;
import serverTask.ServerResponse;

import com.onjection.handler.Utilz;
import com.onjection.roomoncall.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registeration extends Activity implements ServerResponse {
	EditText edtLoginEmail, edtLoginPass, edtRegisterEmail,
			edtRegisterUsername, edtRegisterPass, edtRegisterFirstNm,
			edtRegisterLastNm, edtRegisteraddress, edtRegisteraddress2,
			edtRegistercity, edtRegisterstate, edtRegisterzipcode,
			edtRegisterContNo;
	ProgressDialog pd;
	Button btnRegister;

	Context context;
	ConnectionDector connectionDector = new ConnectionDector(this);
	TextView txtSignIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.register);
		context = Registeration.this;
		txtSignIn = (TextView) findViewById(R.id.txtSignIn);

		edtRegisterEmail = (EditText) findViewById(R.id.edtRegisterEmail);
		edtRegisterUsername = (EditText) findViewById(R.id.edtRegisterUsername);
		edtRegisterPass = (EditText) findViewById(R.id.edtRegisterPass);
		edtRegisterFirstNm = (EditText) findViewById(R.id.edtRegisterFirstNm);
		edtRegisteraddress = (EditText) findViewById(R.id.edtRegisteraddress);
		edtRegisterLastNm = (EditText) findViewById(R.id.edtRegisterLastNm);
		edtRegisteraddress2 = (EditText) findViewById(R.id.edtRegisteraddress2);
		edtRegisterstate = (EditText) findViewById(R.id.edtRegisterstate);
		edtRegisterzipcode = (EditText) findViewById(R.id.edtRegisterzipcode);
		edtRegistercity = (EditText) findViewById(R.id.edtRegistercity);
		edtRegisterContNo = (EditText) findViewById(R.id.edtRegisterContNo);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		txtSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent iregister = new Intent(context, Login.class);
				startActivity(iregister);
				finish();

			}
		});
		btnRegister.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Utilz.hideKeyboard(Registeration.this);
				doRegister();
			}

			private void doRegister() {

				if (edtRegisterEmail.getText().toString().length() <= 0) {
					Toast.makeText(context, "Provide email address.",
							Toast.LENGTH_LONG).show();
				} else if (!Utilz.isValidEmail1(edtRegisterEmail.getText()
						.toString())) {
					Toast.makeText(context, "Provide valid email.",
							Toast.LENGTH_LONG).show();
				} else if (edtRegisterPass.getText().toString().length() <= 0) {
					Toast.makeText(context, "Provide password.",
							Toast.LENGTH_LONG).show();

				} else if (edtRegisteraddress.getText().toString().length() <= 0) {
					Toast.makeText(context, "Provide Address",
							Toast.LENGTH_LONG).show();
				} else if (edtRegisteraddress2.getText().toString().length() <= 0) {
					Toast.makeText(context, "Provide Address2",
							Toast.LENGTH_LONG).show();

				} else if (edtRegisterFirstNm.getText().toString().length() <= 0) {
					Toast.makeText(context, "Provide first name.",
							Toast.LENGTH_LONG).show();

				} else if (edtRegisterLastNm.getText().toString().length() <= 0) {
					Toast.makeText(context, "Provide last name.",
							Toast.LENGTH_LONG).show();

				} else if (edtRegistercity.getText().toString().length() <= 0) {
					Toast.makeText(context, "Provide city.", Toast.LENGTH_LONG)
							.show();

				} else if (edtRegisterContNo.getText().toString().length() <= 0) {
					Toast.makeText(context, "Provide ContactNo.",
							Toast.LENGTH_LONG).show();

				} else if (edtRegisterstate.getText().toString().length() <= 0) {
					Toast.makeText(context, "Provide State.", Toast.LENGTH_LONG)
							.show();

				} else if (edtRegisterUsername.getText().toString().length() <= 0) {
					Toast.makeText(context, "Provide UserName",
							Toast.LENGTH_LONG).show();

				} else if (edtRegisterzipcode.getText().toString().length() <= 0) {
					Toast.makeText(context, "Provide ZipCode.",
							Toast.LENGTH_LONG).show();

				} else if (!Utilz.isInternetConnected(context)) {
					Toast.makeText(context, "Check internet connection.",
							Toast.LENGTH_LONG).show();
				} else {

					if (connectionDector.isConnectingToInternet()) {
						// Progress Dialog initalisation
						pd = ProgressDialog
								.show(context, "", "Register", false);
						ServerDownload download = new ServerDownload(
								Registeration.this, "", Registeration.this,
								"register");
						download.execute(postRequestOfRegister());

					} else {
						Utilz.showAlertActivity("No Neteork Connection",
								context);
						/*
						 * Toast.makeText(getApplicationContext(),
						 * "No Neteork Connection", Toast.LENGTH_SHORT).show();
						 */
					}

				}
			}
		});

	}

	String postRequestOfRegister() {

		String request = "{\"firstname\":\""
				+ edtRegisterFirstNm.getText().toString()
				+ "\",\"lastname\":\"" + edtRegisterLastNm.getText().toString()
				+ "\",\"address\":\"" + edtRegisteraddress.getText().toString()
				+ "\",\"address2\":\""
				+ edtRegisteraddress2.getText().toString() + "\",\"city\":\""
				+ edtRegistercity.getText().toString() + "\",\"state\":\""
				+ edtRegisterstate.getText().toString() + "\",\"zipcode\":\""
				+ edtRegisterzipcode.getText().toString() + "\",\"phone\":\""
				+ edtRegisterContNo.getText().toString() + "\",\"email\":\""
				+ edtRegisterEmail.getText().toString() + "\",\"username\":\""
				+ edtRegisterUsername.getText().toString()
				+ "\",\"password\":\"" + edtRegisterPass.getText().toString()
				+ "\"}";

		return request;
	}

	@Override
	public void httpResponse(JSONObject jsonObject, String tag, int responceCode) {
		pd.dismiss();
		if (tag.equals("register")) {
			if (jsonObject != null) {
//				05-16 12:31:22.977: E/JSON(30534): {"status":"User already Exist"}
//				05-16 12:45:05.625: E/JSON(3960): {"status":"success","customer_id":82}


				if (jsonObject.optString("status").equals("success")) {
					Toast.makeText(context, "Registartion Successfull", 5).show();
					Intent iregister = new Intent(context, Login.class);
					startActivity(iregister);
					finish();	
				}else{
					Utilz.showAlertActivity(jsonObject.optString("status"), Registeration.this);
				}

			} else {

			}

		}

	}

}
