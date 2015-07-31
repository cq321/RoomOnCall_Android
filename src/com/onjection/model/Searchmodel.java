package com.onjection.model;

public class Searchmodel {
	String city_id,from_date,to_date,max_adults="1",max_children="0",hotel_sel_loc_id;

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}

	public String getMax_adults() {
		return max_adults;
	}

	public void setMax_adults(String max_adults) {
		this.max_adults = max_adults;
	}

	public String getMax_children() {
		return max_children;
	}

	public void setMax_children(String max_children) {
		this.max_children = max_children;
	}

	public String getHotel_sel_loc_id() {
		return hotel_sel_loc_id;
	}

	public void setHotel_sel_loc_id(String hotel_sel_loc_id) {
		this.hotel_sel_loc_id = hotel_sel_loc_id;
	}

}
