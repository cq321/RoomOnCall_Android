package com.onjection.model;

public class Roomdetails {
String default_price,id;
String room_type;
String room_count;
public String getDefault_price() {
	return default_price;
}
public void setDefault_price(String default_price) {
	this.default_price = default_price;
}
public String getRoom_type() {
	return room_type;
}
public void setRoom_type(String room_type) {
	this.room_type = room_type;
}
public String getAvailable_rooms() {
	return room_count;
}
public void setAvailable_rooms(String available_rooms) {
	this.room_count = available_rooms;
}
public Roomdetails(String id,String room_type, String room_count,
		String default_price) {
	this.id=id;
	this.default_price = default_price;
	this.room_type = room_type;
	this.room_count = room_count;
}

}
