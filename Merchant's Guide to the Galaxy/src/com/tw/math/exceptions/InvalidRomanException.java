package com.tw.math.exceptions;

public class InvalidRomanException extends RuntimeException {
	
	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = 2239025558190842260L;
	
	public InvalidRomanException() {
		super("This is not a valid Roman number.");
	}
	
	public InvalidRomanException(String pRoman) {
		
		super("This (" + pRoman + ") is not a valid Roman number.");
	}
	
}