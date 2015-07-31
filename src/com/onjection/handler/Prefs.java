package com.onjection.handler;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
	static Context mContext;

	public static void setPreferences(Context context, String key, String value) {
		mContext = context;
		SharedPreferences.Editor editor = mContext.getSharedPreferences(
				"ROOMANCALL", Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getPreferences(Context context, String key) {
		mContext = context;
		SharedPreferences prefs = mContext.getSharedPreferences("ROOMANCALL",
				Context.MODE_PRIVATE);
		String position = prefs.getString(key, "");
		return position;
	}

	public static void setBadgePreferences(Context context, String key,
			int value) {
		mContext = context;
		SharedPreferences.Editor editor = mContext.getSharedPreferences(
				"ROOMANCALL", Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getBadgePreferences(Context context, String key) {
		mContext = context;
		SharedPreferences prefs = mContext.getSharedPreferences("ROOMANCALL",
				Context.MODE_PRIVATE);
		int position = prefs.getInt(key, 0);
		return position;
	}

	public static void setUpdatePreferences(Context context, String key,
			Long value) {
		mContext = context;
		SharedPreferences.Editor editor = mContext.getSharedPreferences(
				"ROOMANCALL", Context.MODE_PRIVATE).edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static Long getUpdatePreferences(Context context, String key) {
		mContext = context;
		SharedPreferences prefs = mContext.getSharedPreferences("ROOMANCALL",
				Context.MODE_PRIVATE);
		Long position = prefs.getLong(key, 0);
		return position;
	}

}
