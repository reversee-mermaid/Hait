package com.trainspotting.hait.model;

public class ReservDTO extends ReservEntity {
	private RstrntDTO rstrnt;
	
	@Override
	public String toString() {
		return "ReservDTO [rstrnt=" + rstrnt + ", toString()=" + super.toString() + "]";
	}
	public RstrntDTO getRstrnt() {
		return rstrnt;
	}
	public void setRstrnt(RstrntDTO rstrnt) {
		this.rstrnt = rstrnt;
	}
}
