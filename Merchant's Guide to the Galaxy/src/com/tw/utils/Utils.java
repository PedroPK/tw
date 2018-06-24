package com.tw.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tw.utils.Constants.*;

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
		} catch ( NumberFormatException nfe ) {
			// The	pNumericString is not a Valid Integer
		}
		
		return isNumeric;
	}
	
	public static List<String> split(String pReadLine, String separatorValue) {
		return Arrays.asList(pReadLine.split(separatorValue));
	}
	
	public static List<String> split(String pReadLine) {
		return split(pReadLine, " ");
	}
	
	public static Set<Character> getRomanNumerals() {
		Set<Character> romanValues = new HashSet<Character>();
		romanValues.add(I);
		romanValues.add(V);
		romanValues.add(X);
		romanValues.add(L);
		romanValues.add(C);
		romanValues.add(D);
		romanValues.add(M);
		return romanValues;
	}
	
}