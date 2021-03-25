package com.trainspotting.hait.exception;

@SuppressWarnings("serial")
public class ReservDuplicatedException extends RuntimeException {
	
	public ReservDuplicatedException() {
		super("DUPLICATED");
	}
}

