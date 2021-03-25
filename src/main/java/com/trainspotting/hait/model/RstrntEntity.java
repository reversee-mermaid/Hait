package com.trainspotting.hait.model;

public class RstrntEntity {
	private int pk;
	private int owner_pk;
	private int city_pk;
	private int state;
	private String nm;
	private String contact;
	private String location;
	private String more_info;
	private String profile_img;
	private String regdate;

	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public int getOwner_pk() {
		return owner_pk;
	}
	public void setOwner_pk(int owner_pk) {
		this.owner_pk = owner_pk;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getCity_pk() {
		return city_pk;
	}
	public void setCity_pk(int city_pk) {
		this.city_pk = city_pk;
	}
	public String getMore_info() {
		return more_info;
	}
	public void setMore_info(String more_info) {
		this.more_info = more_info;
	}
	public String getProfile_img() {
		return profile_img;
	}
	public void setProfile_img(String profile_img) {
		this.profile_img = profile_img;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
}
