package com.onjection.model;

public class LocationInfo {

	private String locationId;
	private String locationName;

	public LocationInfo(String locationId, String locationName) {

		this.locationId = locationId;
		this.locationName = locationName;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

}
