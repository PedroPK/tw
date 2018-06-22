package com.tw.utils;

import java.util.Arrays;
import java.util.List;

public class Utils {
	
	public static boolean isStringValid(String pRoman) {
		return pRoman != null && !pRoman.isEmpty();
	}
	
	public static boolean isNumeric(String pNumericString) {
		boolean isNumeric = false;
		try {
			if ( isStringValid(pNumericString) ) {
				Integer.parseInt(pNumericString);
				isNumeric = true;
			}
		} catch ( NumberFormatException nfe ) {}
		
		return isNumeric;
	}
	
	public static List<String> split(String pReadLine, String separatorValue) {
		return Arrays.asList(pReadLine.split(separatorValue));
	}
	
	public static List<String> split(String pReadLine) {
		return split(pReadLine, " ");
	}
	
}