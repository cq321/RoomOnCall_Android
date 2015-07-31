package com.onjection.roomoncall;

import org.json.JSONObject;

import serverTask.ConnectionDector;
import serverTask.ServerDownload;
import serverTask.ServerResponse;

import com.onjection.handler.User_Prefrences;
import com.onjection.handler.Utilz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactFeedBack extends Fragment implements ServerResponse {
	Button btnSubmit;
	EditText edtDescrp, edtsubject, edtphoneno, edtedmailaddress, edtlastname,
			edtfirstname;
	User_Prefrences user_Prefrences;
	ProgressDialog pd;
	ConnectionDector connectionDector;
	Context ctx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.contact_feedback, container,
				false);
		ctx = getActivity();
		connectionDector = new ConnectionDector(ctx);
		user_Prefrences = new User_Prefrences(getActivity());
		GetAlluserId(rootView);

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (edtDescrp.getText().toString().length() <= 0) {
					Toast.makeText(ctx, "Please insert at least one value!",
							Toast.LENGTH_LONG).show();
				} else if (edtsubject.getText().toString().length() <= 0) {
					Toast.makeText(ctx, "Please Provide Subject.",
							Toast.LENGTH_LONG).show();
				} else if (edtedmailaddress.getText().toString().length() <= 0) {
					Toast.makeText(ctx, "Please Provide EmailId.",
							Toast.LENGTH_LONG).show();
				} else if (!Utilz.isValidEmail1(edtedmailaddress.getText()
						.toString())) {
					Toast.makeText(ctx, "Provide valid email.",
							Toast.LENGTH_LONG).show();
				} else if (edtfirstname.getText().toString().length() <= 0) {
					Toast.makeText(ctx, "Please Provide FirstName.",
							Toast.LENGTH_LONG).show();
				} else if (edtlastname.getText().toString().length() <= 0) {
					Toast.makeText(ctx, "Please Provide LastName.",
							Toast.LENGTH_LONG).show();
				} else if (edtphoneno.getText().toString().length() <= 0
						&& edtphoneno.getText().toString().length() >= 10) {
					Toast.makeText(getActivity(),
							"Please Provide Correct Mobile NO.",
							Toast.LENGTH_LONG).show();
				} else {
					if (connectionDector.isConnectingToInternet()) {
						// Progress Dialog initalisation
						pd = ProgressDialog.show(getActivity(), "",
								"Sending...", false);
						ServerDownload download = new ServerDownload(
								getActivity(), "", ContactFeedBack.this,
								"contact");
						download.execute(postRequestOfContact());

					} else {
						Toast.makeText(getActivity(), "No Neteork Connection",
								Toast.LENGTH_SHORT).show();
					}

				}
			}

		});
		return rootView;
	}

	private String postRequestOfContact() {
		String request = "{\"firstname\":\""
				+ edtfirstname.getText().toString() + "\",\"lastname\":\""
				+ edtlastname.getText().toString() + "\",\"phone\":\""
				+ edtphoneno.getText().toString() + "\",\"email\":\""
				+ edtedmailaddress.getText().toString() + "\",\"Subject\":\""
				+ edtsubject.getText().toString() + "\",\"Message\":\""
				+ edtDescrp.getText().toString() + "\"}";

		return request;
	}

	private void GetAlluserId(View rootView) {
		edtDescrp = (EditText) rootView.findViewById(R.id.edtDescrp);
		edtsubject = (EditText) rootView.findViewById(R.id.edtsubject);
		edtphoneno = (EditText) rootView.findViewById(R.id.edtphoneno);
		edtedmailaddress = (EditText) rootView
				.findViewById(R.id.edtedmailaddress);
		edtlastname = (EditText) rootView.findViewById(R.id.edtlastname);
		edtfirstname = (EditText) rootView.findViewById(R.id.edtfirstname);
		btnSubmit = (Button) rootView.findViewById(R.id.btnSubmit);
		if (new User_Prefrences(getActivity()).getUser_id() != "") {
			edtedmailaddress.setText(user_Prefrences.getUser_email());
			edtfirstname.setText(user_Prefrences.getUser_Firstname());
			edtlastname.setText(user_Prefrences.getUser_Lastname());
			edtphoneno.setText(user_Prefrences.getUserMob());
		} else {

		}
	}

	@Override
	public void httpResponse(JSONObject jsonObject, String tag, int responceCode) {
		pd.dismiss();
		Log.e("responce", "" + jsonObject);
		if (tag.equals("contact")) {
			Utilz.showAlertActivityWithTitle("  Contact Us  ",
					"Your Message Sucessfull Send!", ctx);

		} else {
			Utilz.showAlertActivityWithTitle(
					"  ContactUs  ",
					"Message Sending failed ..Please Check Internet Connection && Resend",
					ctx);
		}

	}

}
