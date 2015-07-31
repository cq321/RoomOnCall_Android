package com.onjection.model;

import java.util.ArrayList;

public class CityInfo {

	private String id;
	private String cityName;

	private ArrayList<LocationInfo> locationList;

	public CityInfo(String id, String cityName,
			ArrayList<LocationInfo> locationList) {

		this.id = id;
		this.cityName = cityName;
		this.locationList = locationList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public ArrayList<LocationInfo> getLocationList() {
		return locationList;
	}

	public void setLocationList(ArrayList<LocationInfo> locationList) {
		this.locationList = locationList;
	}

}
