package com.trainspotting.hait.model;

public class RstrntDTO extends RstrntEntity {
	private String reset_pw;
	private int realtime_total;
	
	public String getReset_pw() {
		return reset_pw;
	}
	public void setReset_pw(String reset_pw) {
		this.reset_pw = reset_pw;
	}
	public int getRealtime_total() {
		return realtime_total;
	}
	public void setRealtime_total(int realtime_total) {
		this.realtime_total = realtime_total;
	}
}