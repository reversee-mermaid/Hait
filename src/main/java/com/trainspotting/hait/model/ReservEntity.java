package com.trainspotting.hait.model;

public class ReservEntity {
	private int pk;
	private int seq;
	private int rstrnt_pk;
	private int headcount;
	private int process_status;
	private String contact;
	private String regdate;
	private String last_update;
	
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getRstrnt_pk() {
		return rstrnt_pk;
	}
	public void setRstrnt_pk(int rstrnt_pk) {
		this.rstrnt_pk = rstrnt_pk;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public int getHeadcount() {
		return headcount;
	}
	public void setHeadcount(int headcount) {
		this.headcount = headcount;
	}
	public int getProcess_status() {
		return process_status;
	}
	public void setProcess_status(int process_status) {
		this.process_status = process_status;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getLast_update() {
		return last_update;
	}
	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
}
