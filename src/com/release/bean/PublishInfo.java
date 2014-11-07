package com.release.bean;

import java.util.ArrayList;

public class PublishInfo {

	
	/**
	 * @param mTime
	 * @param fromCity
	 * @param toCity
	 * @param mheavy
	 * @param truck_type
	 * @param total_pay
	 * @param charge_pay
	 * @param session_id
	 * @param phoneNum
	 */
	public PublishInfo(int mTime, String fromCity, String toCity,
			String mheavy, String truck_type, String total_pay, String charge_pay,
			String session_id, String phoneNum,String trucklength1,String trucklength2,String goodstype) {
		super();
		this.mTime = mTime;
		this.fromCity = fromCity;
		this.toCity = toCity;
		this.mheavy = mheavy;
		this.truck_type = truck_type;
		this.total_pay = total_pay;
		this.charge_pay = charge_pay;
		this.session_id = session_id;
		this.phoneNum = phoneNum;
		this.goodstype=goodstype;
		this.trucklength1=trucklength1;
		this.trucklength2=trucklength2;
	}
	public String getTrucklength1() {
		return trucklength1;
	}
	public void setTrucklength1(String trucklength1) {
		this.trucklength1 = trucklength1;
	}
	public String getTrucklength2() {
		return trucklength2;
	}
	public void setTrucklength2(String trucklength2) {
		this.trucklength2 = trucklength2;
	}
	public PublishInfo(){}

	private int mTime;
	private String fromCity;
	private String toCity;
	private String mheavy;
	private String truck_type;
	private String total_pay;
	private String charge_pay;
	private String session_id;
	private String phoneNum;
	private String goodstype;
	private String trucklength1;
	private String trucklength2;
	
	public String getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getmTime() {
		return mTime;
	}

	public int setmTime(int mTime) {
		return this.mTime = mTime;
	}

	public String getFromCity() {
		return fromCity;
	}

	public String setFromCity(String fromCity) {
		return this.fromCity = fromCity;
	}

	public String getToCity() {
		return toCity;
	}

	public String setToCity(String toCity) {
		return this.toCity = toCity;
	}

	public String getMheavy() {
		return mheavy;
	}

	public void setMheavy(String mheavy) {
		this.mheavy = mheavy;
	}

	public String getTruck_type() {
		return truck_type;
	}

	public String setTruck_type(String truck_type) {
		return this.truck_type = truck_type;
	}

	public String getTotal_pay() {
		return total_pay;
	}

	public void setTotal_pay(String total_pay) {
		this.total_pay = total_pay;
	}

	public String getCharge_pay() {
		return charge_pay;
	}

	public void setCharge_pay(String charge_pay) {
		this.charge_pay = charge_pay;
	}

}
