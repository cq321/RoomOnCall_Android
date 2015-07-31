package com.onjection.model;

public class HotelData {
	 private String txthotelname, txtroomtype,txtAdult,txtchild,txtbed,lowest_price;
	    private int hotelid;
	    private String txtrate,hotelimage;
	    public HotelData() {
	    }
		public HotelData(String txthotelname, String txtroomtype,
				String txtAdult, String txtchild, String txtbed, int hotelid,
				String txtrate, String hotelimage) {
			this.txthotelname = txthotelname;
			this.txtroomtype = txtroomtype;
			this.txtAdult = txtAdult;
			this.txtchild = txtchild;
			this.txtbed = txtbed;
			this.hotelid = hotelid;
			this.txtrate = txtrate;
			this.hotelimage = hotelimage;
		}
		public String getTxthotelname() {
			return txthotelname;
		}
		public void setTxthotelname(String txthotelname) {
			this.txthotelname = txthotelname;
		}
		public String getTxtroomtype() {
			return txtroomtype;
		}
		public void setTxtroomtype(String txtroomtype) {
			this.txtroomtype = txtroomtype;
		}
		public String getTxtAdult() {
			return txtAdult;
		}
		public void setTxtAdult(String txtAdult) {
			this.txtAdult = txtAdult;
		}
		public String getTxtchild() {
			return txtchild;
		}
		public void setTxtchild(String txtchild) {
			this.txtchild = txtchild;
		}
		public String getTxtbed() {
			return txtbed;
		}
		public void setTxtbed(String txtbed) {
			this.txtbed = txtbed;
		}
		public String getRating() {
			return txtrate;
		}
		public void setRating(String string) {
			this.txtrate = string;
		}
		public int getHotelid() {
			return hotelid;
		}
		public void setHotelid(int hotelid) {
			this.hotelid = hotelid;
		}
		public String getHotelimage() {
			return hotelimage;
		}
		public void setHotelimage(String hotelimage) {
			this.hotelimage = hotelimage;
		}
		public void setRate(String lowest_price) {
			// TODO Auto-generated method stub
			
		}
		public String getLowest_price() {
			return lowest_price;
		}
		public void setLowest_price(String lowest_price) {
			this.lowest_price = lowest_price;
		}
}
