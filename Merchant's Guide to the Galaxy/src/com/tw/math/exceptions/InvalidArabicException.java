package com.tw.math.exceptions;

public class InvalidArabicException extends RuntimeException {
	
	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = -6978883603925142731L;
	
	public InvalidArabicException() {
		super("This is not a valid Arabic number.");
	}
	
	public InvalidArabicException(String pArabic) {
		
		super("This (" + pArabic + ") is not a valid Arabic number.");
	}
	
}