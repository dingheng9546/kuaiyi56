package com.release.bean;

public class PubishInfo1 {
	/**
	 * @param mTime
	 * @param fromCity
	 * @param toCity
	 * @param mheavy
	 * @param truck_type
	 * @param total_pay
	 * @param charge_pay
	 */
	public String mTime;
	public String fromCity;
	public String toCity;
	public int mheavy;
	public String truck_type;
	public int total_pay;
	public int charge_pay;
	public PubishInfo1(String mTime, String fromCity, String toCity,
			int mheavy, String truck_type, int total_pay, int charge_pay) {
	
		this.mTime = mTime;
		this.fromCity = fromCity;
		this.toCity = toCity;
		this.mheavy = mheavy;
		this.truck_type = truck_type;
		this.total_pay = total_pay;
		this.charge_pay = charge_pay;
	}
	public PubishInfo1(){}



}
