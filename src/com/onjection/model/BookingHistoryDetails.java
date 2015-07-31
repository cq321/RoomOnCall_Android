package com.onjection.model;

public class BookingHistoryDetails {
	private String adults, booking_number, checkin, checkout, children,
			created_date, discount_percent, extra_beds, extra_beds_charge,
			extras, extras_fee, hotel, hotel_id, id, meal_plan_id,
			meal_plan_price, order_price, payment_sum, payment_type, price,
			room, room_id, room_numbers, rooms, status;
	

	/**
	 * @return the adults
	 */
	public String getAdults() {
		return adults;
	}

	/**
	 * @param adults
	 *            the adults to set
	 */
	public void setAdults(String adults) {
		this.adults = adults;
	}

	/**
	 * @return the booking_number
	 */
	public String getBooking_number() {
		return booking_number;
	}

	/**
	 * @param booking_number
	 *            the booking_number to set
	 */
	public void setBooking_number(String booking_number) {
		this.booking_number = booking_number;
	}

	/**
	 * @return the checkin
	 */
	public String getCheckin() {
		return checkin;
	}

	/**
	 * @param checkin
	 *            the checkin to set
	 */
	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	/**
	 * @return the checkout
	 */
	public String getCheckout() {
		return checkout;
	}

	/**
	 * @param checkout
	 *            the checkout to set
	 */
	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}

	/**
	 * @return the children
	 */
	public String getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(String children) {
		this.children = children;
	}

	/**
	 * @return the created_date
	 */
	public String getCreated_date() {
		return created_date;
	}

	/**
	 * @param adults
	 * @param booking_number
	 * @param checkin
	 * @param checkout
	 * @param children
	 * @param created_date
	 * @param discount_percent
	 * @param extra_beds
	 * @param extra_beds_charge
	 * @param extras
	 * @param extras_fee
	 * @param hotel
	 * @param hotel_id
	 * @param id
	 * @param meal_plan_id
	 * @param meal_plan_price
	 * @param order_price
	 * @param payment_sum
	 * @param payment_type
	 * @param price
	 * @param room
	 * @param room_id
	 * @param room_numbers
	 * @param rooms
	 * @param status
	 */
	public BookingHistoryDetails()
	{
		
	}
	public BookingHistoryDetails(String adults, String booking_number,
			String checkin, String checkout, String children,
			String created_date, String discount_percent, String extra_beds,
			String extra_beds_charge, String extras, String extras_fee,
			String hotel, String hotel_id, String id, String meal_plan_id,
			String meal_plan_price, String order_price, String payment_sum,
			String payment_type, String price, String room, String room_id,
			String room_numbers, String rooms, String status) {
		this.adults = adults;
		this.booking_number = booking_number;
		this.checkin = checkin;
		this.checkout = checkout;
		this.children = children;
		this.created_date = created_date;
		this.discount_percent = discount_percent;
		this.extra_beds = extra_beds;
		this.extra_beds_charge = extra_beds_charge;
		this.extras = extras;
		this.extras_fee = extras_fee;
		this.hotel = hotel;
		this.hotel_id = hotel_id;
		this.id = id;
		this.meal_plan_id = meal_plan_id;
		this.meal_plan_price = meal_plan_price;
		this.order_price = order_price;
		this.payment_sum = payment_sum;
		this.payment_type = payment_type;
		this.price = price;
		this.room = room;
		this.room_id = room_id;
		this.room_numbers = room_numbers;
		this.rooms = rooms;
		this.status = status;
	}

	/**
	 * @param created_date
	 *            the created_date to set
	 */
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	/**
	 * @return the discount_percent
	 */
	public String getDiscount_percent() {
		return discount_percent;
	}

	/**
	 * @param discount_percent
	 *            the discount_percent to set
	 */
	public void setDiscount_percent(String discount_percent) {
		this.discount_percent = discount_percent;
	}

	/**
	 * @return the extra_beds
	 */
	public String getExtra_beds() {
		return extra_beds;
	}

	/**
	 * @param extra_beds
	 *            the extra_beds to set
	 */
	public void setExtra_beds(String extra_beds) {
		this.extra_beds = extra_beds;
	}

	/**
	 * @return the extra_beds_charge
	 */
	public String getExtra_beds_charge() {
		return extra_beds_charge;
	}

	/**
	 * @param extra_beds_charge
	 *            the extra_beds_charge to set
	 */
	public void setExtra_beds_charge(String extra_beds_charge) {
		this.extra_beds_charge = extra_beds_charge;
	}

	/**
	 * @return the extras
	 */
	public String getExtras() {
		return extras;
	}

	/**
	 * @param extras
	 *            the extras to set
	 */
	public void setExtras(String extras) {
		this.extras = extras;
	}

	/**
	 * @return the extras_fee
	 */
	public String getExtras_fee() {
		return extras_fee;
	}

	/**
	 * @param extras_fee
	 *            the extras_fee to set
	 */
	public void setExtras_fee(String extras_fee) {
		this.extras_fee = extras_fee;
	}

	/**
	 * @return the hotel
	 */
	public String getHotel() {
		return hotel;
	}

	/**
	 * @param hotel
	 *            the hotel to set
	 */
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	/**
	 * @return the hotel_id
	 */
	public String getHotel_id() {
		return hotel_id;
	}

	/**
	 * @param hotel_id
	 *            the hotel_id to set
	 */
	public void setHotel_id(String hotel_id) {
		this.hotel_id = hotel_id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the meal_plan_id
	 */
	public String getMeal_plan_id() {
		return meal_plan_id;
	}

	/**
	 * @param meal_plan_id
	 *            the meal_plan_id to set
	 */
	public void setMeal_plan_id(String meal_plan_id) {
		this.meal_plan_id = meal_plan_id;
	}

	/**
	 * @return the meal_plan_price
	 */
	public String getMeal_plan_price() {
		return meal_plan_price;
	}

	/**
	 * @param meal_plan_price
	 *            the meal_plan_price to set
	 */
	public void setMeal_plan_price(String meal_plan_price) {
		this.meal_plan_price = meal_plan_price;
	}

	/**
	 * @return the order_price
	 */
	public String getOrder_price() {
		return order_price;
	}

	/**
	 * @param order_price
	 *            the order_price to set
	 */
	public void setOrder_price(String order_price) {
		this.order_price = order_price;
	}

	/**
	 * @return the payment_sum
	 */
	public String getPayment_sum() {
		return payment_sum;
	}

	/**
	 * @param payment_sum
	 *            the payment_sum to set
	 */
	public void setPayment_sum(String payment_sum) {
		this.payment_sum = payment_sum;
	}

	/**
	 * @return the payment_type
	 */
	public String getPayment_type() {
		return payment_type;
	}

	/**
	 * @param payment_type
	 *            the payment_type to set
	 */
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the room
	 */
	public String getRoom() {
		return room;
	}

	/**
	 * @param room
	 *            the room to set
	 */
	public void setRoom(String room) {
		this.room = room;
	}

	/**
	 * @return the room_id
	 */
	public String getRoom_id() {
		return room_id;
	}

	/**
	 * @param room_id
	 *            the room_id to set
	 */
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	/**
	 * @return the room_numbers
	 */
	public String getRoom_numbers() {
		return room_numbers;
	}

	/**
	 * @param room_numbers
	 *            the room_numbers to set
	 */
	public void setRoom_numbers(String room_numbers) {
		this.room_numbers = room_numbers;
	}

	/**
	 * @return the rooms
	 */
	public String getRooms() {
		return rooms;
	}

	/**
	 * @param rooms
	 *            the rooms to set
	 */
	public void setRooms(String rooms) {
		this.rooms = rooms;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
