package com.tw.math.exceptions;

public class EmptyRomanException extends RuntimeException {
	
	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = 2701455229194246848L;
	
	public EmptyRomanException() {
		super("This is not a Roman character. Its empty");
	}
	
}