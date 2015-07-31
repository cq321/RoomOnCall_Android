package com.onjection.model;

import java.util.ArrayList;

public class NewFilterhoteldetails {
	String txthotelname, txtcity, txtemail, txtphone, txtRate, address,
			hotel_id, location, imghotelimg;
	@SuppressWarnings("unused")
	private ArrayList<Roomdetails> roomdeailslist;

	public NewFilterhoteldetails() {
		// TODO Auto-generated constructor stub
	}

	

	public NewFilterhoteldetails(String txthotelname, String imghotelimg,
			String txtcity, String txtphone, String txtemail, String txtstar,
			String address, String hotel_id, String location
			) {
		this.txthotelname = txthotelname;
		this.txtcity = txtcity;
		this.txtemail = txtemail;
		this.txtphone = txtphone;
		this.txtRate = txtstar;
		this.address = address;
		this.imghotelimg = imghotelimg;
		this.hotel_id = hotel_id;
		this.location = location;

	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHotel_id() {
		return hotel_id;
	}

	public void setHotel_id(String hotel_id) {
		this.hotel_id = hotel_id;
	}

	public String getTxthotelname() {
		return txthotelname;
	}

	public void setTxthotelname(String txthotelname) {
		this.txthotelname = txthotelname;
	}

	public String getTxtcity() {
		return txtcity;
	}

	public void setTxtcity(String txtcity) {
		this.txtcity = txtcity;
	}

	public String getTxtfax() {
		return address;
	}

	public void setTxtfax(String address) {
		this.address = address;
	}

	public ArrayList<Roomdetails> getRoomdeailslist() {
		return roomdeailslist;
	}

	public void setRoomdeailslist(ArrayList<Roomdetails> roomdeailslist) {
		this.roomdeailslist = roomdeailslist;
	}

	public String getTxtemail() {
		return txtemail;
	}

	public void setTxtemail(String txtemail) {
		this.txtemail = txtemail;
	}

	public String getTxtphone() {
		return txtphone;
	}

	public void setTxtphone(String txtphone) {
		this.txtphone = txtphone;
	}

	public String getTxtstar() {
		return txtRate;
	}

	public void setTxtstar(String txtstar) {
		this.txtRate = txtstar;
	}

	public String getImghotelimg() {
		return imghotelimg;
	}

	public void setImghotelimg(String imghotelimg) {
		this.imghotelimg = imghotelimg;
	}

}
