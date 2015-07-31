package com.onjection.handler;

import android.content.Context;
import android.content.SharedPreferences;

public class User_Prefrences {
	private static final String USER_NAME = "user_name";
	private static final String USER_ID = "user_id";
	private static final String USER_EMAIL = "user_email";
	private static final String USER_COMP = "user_comp";
	private static final String USER_ADD = "user_add";
	private static final String USER_MOB = "user_mob";
	private static final String USER_FIRSTNAME = "user_firstname";
	private static final String USER_LASTNAME = "user_lastname";
	private static final String USER_SELECTEDCITY="selectedcity";
	private static final String USER_PASS = "user_pass";

	String userName = "", user_id = "", user_email = "", user_comp = "",
			user_add = "", user_mob = "", user_lastname = "",
			user_firstname = "",City_id="", password="";

	SharedPreferences sharedPreferences;

	public User_Prefrences(Context context) {
		// TODO Auto-generated constructor stub
		sharedPreferences = context.getSharedPreferences("user_Prefrences",
				Context.MODE_PRIVATE);
	}

	
	public String getPassword() {
		password = sharedPreferences.getString(USER_PASS, "");
		return password;
	}

	public void setPassword(String password) {

		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(USER_PASS, password);
		editor.commit();

	}
	
	public String getCityID() {
		City_id = sharedPreferences.getString(USER_SELECTEDCITY, "");
		return City_id;
	}

	public void setCityID(String City_id) {

		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(USER_SELECTEDCITY, City_id);
		editor.commit();

	}
	
	
	
	
	public String getUserMob() {
		user_mob = sharedPreferences.getString(USER_MOB, "");
		return user_mob;
	}

	public void setUserMob(String user_mob) {

		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(USER_MOB, user_mob);
		editor.commit();

	}

	public String getUserAdd() {
		user_add = sharedPreferences.getString(USER_ADD, "");
		return user_add;
	}

	public void setUserAdd(String userAdd) {

		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(USER_ADD, userAdd);
		editor.commit();

	}

	public String getUserComp() {
		user_comp = sharedPreferences.getString(USER_COMP, "");
		return user_comp;
	}

	public void setUserComp(String userComp) {

		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(USER_COMP, userComp);
		editor.commit();

	}

	public String getUserName() {
		userName = sharedPreferences.getString(USER_NAME, "");
		return userName;
	}

	public void setUserName(String userName) {

		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(USER_NAME, userName);
		editor.commit();

	}

	public String getUser_id() {
		user_id = sharedPreferences.getString(USER_ID, "");
		return user_id;
	}

	public void setUser_id(String user_id) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(USER_ID, user_id);
		editor.commit();
	}

	public String getUser_email() {
		user_email = sharedPreferences.getString(USER_EMAIL, "");
		return user_email;
	}

	public String getUser_Firstname() {
		user_firstname = sharedPreferences.getString(USER_FIRSTNAME, "");
		return user_firstname;
	}

	public String getUser_Lastname() {
		user_lastname = sharedPreferences.getString(USER_LASTNAME, "");
		return user_lastname;
	}

	public void setUser_email(String user_email) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(USER_EMAIL, user_email);
		editor.commit();
	}

	public void setUser_firstName(String user_firstname) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(USER_FIRSTNAME, user_firstname);
		editor.commit();
	}

	public void setUser_lastname(String user_lastname) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(USER_LASTNAME, user_lastname);
		editor.commit();
	}

}
