package com.tw.math.exceptions;

public class FourTimesRepetitionException extends RuntimeException {
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -5911578377487833261L;

	public FourTimesRepetitionException() {
		super("A Roman character was repeated more than 3 times consecutively");
	}

	public FourTimesRepetitionException(char pCharacter) {
		super("The character "+ pCharacter + " was repeated more than 3 times consecutively");
	}
	
}