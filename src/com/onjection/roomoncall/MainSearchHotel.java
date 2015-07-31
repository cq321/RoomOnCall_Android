package com.onjection.roomoncall;

import java.util.ArrayList;

import com.onjection.adapter.NavDrawerListAdapter;
import com.onjection.handler.User_Prefrences;
import com.onjection.model.NavDrawerItem;
import com.onjection.myaccountDetails.Login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "Recycle", "ShowToast" })
public class MainSearchHotel extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	Context ctx;
	// nav drawer title
	private CharSequence mDrawerTitle;

	private CharSequence mTitle;
	private TypedArray navMenuIcons;
	// slide menu items
	private String[] navMenuTitles;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	User_Prefrences user_Prefrences;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_search_hotel);
		user_Prefrences = new User_Prefrences(this);
		ctx = MainSearchHotel.this;
		mTitle = mDrawerTitle = getTitle();
		
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		if (new User_Prefrences(this).getUser_id() != "") {
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
					.getResourceId(4, -1)));
		} else {
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
					.getResourceId(5, -1)));
		}
		// Pages

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.slide_menu_down, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		
		    menu.findItem(R.id.action_settings).setEnabled(false);
		
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		MenuItem item3  = menu.findItem(R.id.action_settings);
	    item3.setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new SearchHotelFragment();
			break;
		case 1:
			fragment = new AllHotelFragment();
			break;
		case 2:
			fragment = new ContactFeedBack();
			break;
		case 3:
			fragment = new MyAccount();
			break;

		case 4:
			mDrawerLayout.closeDrawers();
			if (new User_Prefrences(this).getUser_id() != "") {
				openAskPopUp();
			} else {
				Intent ilogin = new Intent(MainSearchHotel.this, Login.class);
				startActivity(ilogin);
			}

			break;

		/*
		 * case 5: fragment = new WhatsHotFragment(); break;
		 */

		default:
			break;
		}

		if (fragment != null) {

			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.add(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	private void openAskPopUp() {
		final Dialog dialog = new Dialog(ctx);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setContentView(R.layout.alert_dialog);
		dialog.setCancelable(true);
		TextView txtTitle = (TextView) dialog.findViewById(R.id.txtTitle);
		txtTitle.setText("Do you really want to signout?");

		TextView txtDescription = (TextView) dialog
				.findViewById(R.id.txtDescription);
		txtDescription.setVisibility(View.GONE);

		Button btnOk = (Button) dialog.findViewById(R.id.buttonOk);
		btnOk.setText("Yes");
		btnOk.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				user_Prefrences.setUser_id("");
				user_Prefrences.setUser_email("");
				user_Prefrences.setUserAdd("");
				user_Prefrences.setUserMob("");
				user_Prefrences.setUserName("");
				user_Prefrences.setUserComp("");

				// displayView(0);

				dialog.dismiss();
				Toast.makeText(ctx, "SignOut SuccessFull", 5).show();
				finish();
				Intent ihome = new Intent(MainSearchHotel.this,
						MainSearchHotel.class);
				startActivity(ihome);
			}
		});

		Button btnCancel = (Button) dialog.findViewById(R.id.buttonCancel);
		btnCancel.setText("No");
		btnCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// StudentDetail coloursModel = colourList.get(positionUpdated);

				dialog.dismiss();
			}
		});

		dialog.show();

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
