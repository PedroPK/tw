package com.tw.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tw.utils.Constants.*;

/**
 * This class implements methods that are useful to many others in this application
 * 
 * @author pedro.c.f.santos
 */
public class Utils {
	
	/**
	 * This method test if the		pString		is a Not Null and Not Empty String, been useful to avoid exceptions like NullPointerException
	 * 
	 * @param	pString		String to be tested
	 * 
	 * @return	boolean		The answer if the	pString		is Not Null and Not Empty
	 */
	public static boolean isStringValid(String pString) {
		return pString != null && !pString.isEmpty();
	}
	
	/**
	 * This method verify if the	pNumericString	contains characters that can be converted to an Integer
	 * 
	 * @param	pNumericString	String to be tested in an Integer conversion
	 * 
	 * @return	boolean		The answer if the	pNumericString	can successfully be converted to an Integer
	 */
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
	
	/**
	 * This method receives a String, and split it by Space (" "), giving back a List with the resulting Strings
	 * 
	 * @param	pReadLine			String to be spliced by		pSeparatorValue
	 * @param	pSeparatorValue		String with the separator to be used to split	pReadLine
	 * 
	 * @return	List with the resulting Strings
	 */
	public static List<String> split(String pReadLine, String pSeparatorValue) {
		return Arrays.asList(pReadLine.split(pSeparatorValue));
	}
	
	/**
	 * This method receives a String, and split it by Space (" "), giving back a List with the resulting Strings
	 * 
	 * @param		pReadLine		String to be spliced by Space (" ")
	 * 
	 * @return		List with the resulting Strings
	 */
	public static List<String> split(String pReadLine) {
		List<String> result = null;
		
		if ( isStringValid(pReadLine) ) {
			result = split(pReadLine, " ");
		}
		
		return result;
	}
	
	/**
	 * This method will generate and return a Set collection, containing all the 7 Roman Numbers
	 * 
	 * @return	Set collection, containing all the 7 Roman Numbers
	 */
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
	
	/**
	 * This method will test if the		pDecimal	are, compared to Zero, equals, ignoring the Scale 
	 * 
	 * @param		pBigDecimal		The BigDecimal to be compared to Zero
	 * 
	 * @return		boolean			The answer if	pBigDecimal is equals to Zero.
	 */
	public static boolean equalsZero(BigDecimal pBigDecimal) {
		boolean response = false;
		
		if ( pBigDecimal != null ) {
			response = pBigDecimal.compareTo(BigDecimal.ZERO) == 0;
		}
		
		return response;
	}
	
	/**
	 * This method compares the parameter to the Question Mark (?)
	 * 
	 * @param pString
	 * @return
	 */
	public static boolean isEqualsQuestionMark(String pString) {
		boolean response = false;
		
		if ( isStringValid(pString) ) {
			response = 
				pString.equalsIgnoreCase(QUESTION_MARK);
		}
		
		return response;
	}
	
	/**
	 * This method compares the parameter to the "Is" String
	 * 
	 * @param pString
	 * @return
	 */
	public static boolean isEqualsIs(String pString) {
		boolean response = false;
		
		if ( isStringValid(pString) ) {
			response = pString.equalsIgnoreCase(IS);
		}
		
		return response;
	}
	
	/**
	 * This method compares the parameter to the "Much" String
	 * 
	 * @param pString
	 * @return
	 */
	public static boolean isEqualsMuch(String pString) {
		boolean response = false;
		
		if ( isStringValid(pString) ) {
			response = pString.equalsIgnoreCase(MUCH);
		}
		
		return response;
	}
	
	/**
	 * This method compares the parameter to the "How" String
	 * 
	 * @param pString
	 * @return
	 */
	public static boolean isEqualsHow(String pString) {
		boolean response = false;
		
		if ( isStringValid(pString) ) {
			response = pString.equalsIgnoreCase(HOW);
		}
		
		return response;
	}
	
	/**
	 * This method will compare the two BigDecimals, if they are not null
	 * 
	 * @param pFirstBigDecimal
	 * @param pSecondBigDecimal
	 * 
	 * @return	boolean		Indicates if the both BigDecimals are equals 
	 */
	public static boolean isEquals(BigDecimal pFirstBigDecimal, BigDecimal pSecondBigDecimal) {
		boolean response = false;
		
		if ( pFirstBigDecimal != null && pSecondBigDecimal != null ) {
			response = pFirstBigDecimal.compareTo(pSecondBigDecimal) == 0;
		}
		
		return response;
	}
	
	/**
	 * This method compares the BigDecimal with Zero
	 * 
	 * @param pBigDecimal
	 * 
	 * @return	boolean		Indicates if the BigDecimal is equals to Zero
	 */
	public static boolean isEqualsZero(BigDecimal pBigDecimal) {
		boolean response = false;
		
		if ( pBigDecimal != null ) {
			response = pBigDecimal.compareTo(BigDecimal.ZERO) == 0;
		}
		
		return response;
	}
	
}