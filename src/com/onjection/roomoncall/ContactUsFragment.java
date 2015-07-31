package com.onjection.roomoncall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ContactUsFragment extends android.support.v4.app.Fragment {
	Button btncontact;
	Context ctx;

	public ContactUsFragment() {
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.contactusfragment, container,
				false);
		ctx = container.getContext();
		btncontact = (Button) rootView.findViewById(R.id.btncontact);
		btncontact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentcontact = new Intent(ctx, ContactFeedBack.class);
				startActivity(intentcontact);
				//getActivity().finish();
			}
		});

		return rootView;
	}
}
