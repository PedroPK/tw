package com.tw.math;

import static com.tw.utils.Constants.*;
import static com.tw.utils.Utils.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

import com.tw.math.exceptions.EmptyRomanException;
import com.tw.math.exceptions.FourTimesRepetitionException;
import com.tw.math.exceptions.InvalidArabicException;
import com.tw.math.exceptions.InvalidRomanException;

/**
 * Roman numerals are based on seven symbols:
 * 
 * Symbol		Value
 * I			1
 * V			5
 * X			10
 * L			50
 * C			100
 * D			500
 * M			1,000
 * 
 * Numbers are formed by combining symbols together and adding the values. For example, MMVI is 1000 + 1000 + 5 + 1 = 2006.
 * Generally, symbols are placed in order of value, starting with the largest values.
 * When smaller values precede larger values, the smaller values are subtracted from the larger values, and the result is added to the total.
 * 
 * For example MCMXLIV = 1000 + (1000 - 100) + (50 - 10) + (5 - 1) = 1944.
 * 
 * The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
 * (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
 * 
 * "I" can be subtracted from "V" and "X" only. 
 * "X" can be subtracted from "L" and "C" only. 
 * "C" can be subtracted from "D" and "M" only. 
 * "V", "L", and "D" can never be subtracted.
 * 
 * Only one small-value symbol may be subtracted from any large-value symbol.
 * 
 * A number written in [16]Arabic numerals can be broken into digits. 
 * 
 * For example, 1903 is composed of 1, 9, 0, and 3. 
 * 
 * To write the Roman numeral, each of the non-zero digits should be treated separately. 
 * In the above example, 1,000 = M, 900 = CM, and 3 = III. 
 * 
 * Therefore, 1903 = MCMIII.
 * 
 * @author pedroc.f.santos
 */
public class Converter {
	
	/**
	 * This method will remove the Decimal values from the		pFinalValue		if its a Integer only BigDecimal
	 * 
	 * @param pFinalValue
	 * 
	 * @return
	 */
	public static BigDecimal processDecimalValues(BigDecimal pFinalValue) {
		BigDecimal result = null;
		
		if ( pFinalValue != null ) {
			result = setScale5(pFinalValue);
			BigDecimal fraction = pFinalValue.remainder(ONE_BIG_DECIMAL);
			if ( equalsZero(fraction) ) {
				result = result.setScale(0, RoundingMode.HALF_EVEN);
			}
		}
		
		return result;
	}
	
	/**
	 * This method sets the Scale to 5, avoiding the application to manipulate worthless portions of BigDecimals
	 * 
	 * @param		pBigDecimal		BigDecimal to be Scaled to 5
	 * @return
	 */
	private static BigDecimal setScale5(BigDecimal pBigDecimal) {
		pBigDecimal = pBigDecimal.setScale(5, RoundingMode.HALF_EVEN);
		return pBigDecimal;
	}
	
	/**
	 * This method indicates if the parameter String contains a Unit to Roman Mapping Sentence
	 * 
	 * @param		pReadLine
	 * 
	 * @return		boolean		Indicates if the	pReadLine	contains a Unit to Roman Mapping Sentence
	 */
	public static boolean isMappingSentence(String pReadLine) {
		List<String> sentenceTerms = split(pReadLine);
		
		Set<Character> romanValues = getRomanNumerals();
		
		boolean isMappingSentence = false;
		if (	sentenceTerms != null && sentenceTerms.size() == 3		&&
				
				sentenceTerms.get(0).length() >= 0						&&
				
				sentenceTerms.get(1).equals(IS)						&&
				
				sentenceTerms.get(2).length() == 1 &&
				romanValues.contains( sentenceTerms.get(2).charAt(0) )
		) {
			isMappingSentence = true;
		}
		return isMappingSentence;
	}
	
	/**
	 * This method is responsible to convert a Roman number (ex: V) to an Arabic number (ex: 5)
	 * 
	 * @param		pRoman
	 * 
	 * @return		An Arabic Number
	 */
	public static int convertRomanToArabic(String pRoman) {
		validateRoman(pRoman);
		
		int response = 0;
		
		for ( int index = 0; index < pRoman.length(); index = index + 1 ) {
			boolean shouldSubtract = shouldSubtractValue(pRoman, index);
			
			char actualCharacter = pRoman.charAt(index);
			int actualValue = getArabicValue(actualCharacter);
			
			response = sumOrSubtract(response, shouldSubtract, actualValue);
		}
		
		return response;
	}
	
	/**
	 * This method will evaluate if the Character at Index Position is Smaller then the next one.
	 * If this is true, the first should be subtracted from the second.
	 * Otherwise, they should be summed
	 * 
	 * @param pRoman
	 * @param pIndex
	 * 
	 * @return
	 */
	private static boolean shouldSubtractValue(String pRoman, int pIndex) {
		boolean shouldSubtract = false;
		
		char actualCharacter = pRoman.charAt(pIndex);
		
		if ( pIndex + 1 < pRoman.length() ) {
			char nextCharacter = pRoman.charAt(pIndex + 1);
			
			if ( convertRomanToArabic(Character.toString(actualCharacter)) < convertRomanToArabic(Character.toString(nextCharacter)) ) {
				shouldSubtract = true;
			}
		}
		return shouldSubtract;
	}
	
	/**
	 * This method converts a Roman character to an Integer
	 * 
	 * @param pActualCharacter
	 * @return
	 */
	private static int getArabicValue(char pActualCharacter) {
		int resposeValue = 0;
		
		if (  pActualCharacter == I  ) {
			resposeValue = 1;
		} else if (  pActualCharacter == V  ) {
			resposeValue = 5;
		} else if (  pActualCharacter == X  ) {
			resposeValue = 10;
		} else if (  pActualCharacter == L  ) {
			resposeValue = 50;
		} else if (  pActualCharacter == C  ) {
			resposeValue = 100;
		} else if (  pActualCharacter == D  ) {
			resposeValue = 500;
		} else if (  pActualCharacter == M  ) {
			resposeValue = 1000;
		}
		
		return resposeValue;
	}
	
	/**
	 * This method its goind to do or a Sum or a Subtraction of		pResponseValue		and		pToSumOrSubtractValue		, based on the boolean		pInShouldSubtract
	 * 
	 * @param pResponseValue
	 * @param pInShouldSubtract
	 * @param pToSumOrSubtractValue
	 * @return
	 */
	private static int sumOrSubtract(int pResponseValue, boolean pInShouldSubtract, int pToSumOrSubtractValue) {
		if ( !pInShouldSubtract ) {
			pResponseValue = pResponseValue + pToSumOrSubtractValue;
		} else {
			pResponseValue = pResponseValue - pToSumOrSubtractValue;
		}
		return pResponseValue;
	}
	
	/**
	 * This method is responsible to convert an Arabic number (ex: 5) to a Roman number (ex: V)
	 * 
	 * @param	pArabic
	 * 
	 * @return	A Roman Number
	 */
	public static String convertArabicToRoman(String pArabic) {
		StringBuffer stringBuffer = new StringBuffer("");
		
		validateArabitToRomanConvertion(pArabic);
		
		// Starting from Right to the Left
		int multiplier = 1;
		for ( int index = pArabic.length() -1; index >= 0; index = index - 1 ) {
			char actualCharacter = pArabic.charAt(index);
			
			int actualValue = getArabicDigit(actualCharacter);
			
			stringBuffer = getRomanValue(actualValue, multiplier).append(stringBuffer); 
			
			multiplier = multiplier * 10;
		}
		
		String romanResponse = stringBuffer.toString();
		romanResponse = romanResponse.trim();
		
		validateRoman(romanResponse);
		
		return romanResponse;
	}
	
	/**
	 * This method is responsible to do all the necessary validations on the	pArabic		string, to assure that its convertible to a Roman Number
	 * 
	 * @param	pArabic		A String that is supposed to contain an Arabic Number that we want to convert to an Roman Number
	 */
	private static void validateArabitToRomanConvertion(String pArabic) {
		if ( !isStringValid(pArabic) ) {
			throw new InvalidArabicException();
		}
		
		if ( !isArabicValid(pArabic) ) {
			throw new InvalidArabicException(pArabic);
		}
		
		int arabicValue = getArabicValue(pArabic);
		if ( arabicValue == 0 ) {
			throw new InvalidRomanException("The Arabic Zero (0) number cannot be represented in Roman numbers");
		}
	}
	
	/**
	 * This method converts the		pArabic		string into an Integer, its this is possible.
	 * Otherwise it will return the Minimum value possible for an Integer
	 * 
	 * @param		pArabic		A String that should contain an Integer value
	 * 
	 * @return		int			An Integer, with the equivalent content of the	pArabic		string
	 */
	public static int getArabicValue(String pArabic) {
		int response = Integer.MIN_VALUE;
		
		if ( isStringValid(pArabic) ) {
			try {
				response = Integer.parseInt(pArabic);
			} catch ( NumberFormatException nfe ) {
				// The pArabic cannot be converted to an Integer
			}
		}
		
		return response;
	}
	
	/**
	 * This method is going to test if the		pArabic		string can be converted into an Integer
	 * 
	 * @param pArabic
	 * @return
	 */
	public static boolean isArabicValid(String pArabic) {
		boolean isValid = true;
		try {
			Integer.parseInt(pArabic);
		} catch ( NumberFormatException nfe ) {
			isValid = false;
		}
		return isValid;
	}
	
	/**
	 * This method converts the		pArabic		string into an Integer, its this is possible.
	 * Otherwise it will return the Minimum value possible for an Integer
	 * 
	 * @param		pArabic		A Character that should contain an Integer value
	 * 
	 * @return		int			An Integer, with the equivalent content of the	pArabic		character
	 */
	public static int getArabicDigit(char pArabic) {
		int response = Integer.MIN_VALUE;
		
		try {
			response = Integer.parseInt(
				Character.toString(pArabic)
			);
		} catch ( NumberFormatException nfe ) {
			// The pArabic cannot be converted to an Integer
		}
		
		return response;
	}
	
	/**
	 * This method is responsible to do all the necessary validations in a Roman Number
	 * 
	 * @param pRoman
	 */
	private static void validateRoman(String pRoman) {
		// This validation should be the first to be invoked, to prevent NullPointerExceptions
		if ( !isStringValid(pRoman) ) {
			throw new EmptyRomanException();
		}
		
		if ( hasInvalidConsecutiveRepetitions(pRoman) ) {
			throw new FourTimesRepetitionException();
		}
		
		if ( hasInvalidInternalSubtraction(pRoman) ) {
			throw new InvalidRomanException(pRoman);
		}
	}
	
	/**
	 * This method implements this rules:
	 * 
	 * 		- The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * 		- (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 * 
	 * @param pRoman
	 * @return
	 */
	public static boolean hasInvalidConsecutiveRepetitions(String pRoman) {
		boolean response = false;
		
		if ( isStringValid(pRoman) ) {
			int repetitions = 1;
			char lastRomanCharacterAnalised = ' ';
			for ( int index = 0; index < pRoman.length(); index = index + 1 ) {
				lastRomanCharacterAnalised = pRoman.charAt(index);
				if ( index > 0 && isCharacterEqualsPrevious(pRoman, index) ) {
					repetitions = repetitions + 1;
					
					if (	
							repetitions >= 2	&&
							(
								lastRomanCharacterAnalised == V	||
								lastRomanCharacterAnalised == L	||
								lastRomanCharacterAnalised == D
							)
					) {
						break;
					}
					
					if ( repetitions >= 4 ) {
						// It should break here, to avoid the case of a IIIV to be considered valid.
						break;
					}
				} else {
					repetitions = 1;
				}
			}
			
			if (
					repetitions > 3 ||
					(
						repetitions >= 2	&&
						(
							lastRomanCharacterAnalised == V	||
							lastRomanCharacterAnalised == L	||
							lastRomanCharacterAnalised == D
						)
					)
			) {
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * This method is responsible to validate this following rules
	 * 		"I" can be subtracted from "V" and "X" only.
	 * 		"X" can be subtracted from "L" and "C" only.
	 * 		"C" can be subtracted from "D" and "M" only.
	 * 		"V", "L", and "D" can never be subtracted.
	 * 
	 * @param		pRoman		A Roman number
	 * 
	 * @return		boolean		indicates if the pRoman has an internal Invalid Subtraction
	 */
	private static boolean hasInvalidInternalSubtraction(String pRoman) {
		boolean response = false;
		
		/* index < pRoman.length() - 1
		 * Make this iteration to stop in the Penultimate character
		 */
		for ( int index = 0; index < pRoman.length() - 1; index = index + 1 ) {
			char actualCharacter	= pRoman.charAt(index		);
			char nextCharacter		= pRoman.charAt(index  + 1	);
			
			int actualValue			= getValue(pRoman, index		);
			int nextValue			= getValue(pRoman, index + 1	);
			
			if ( 
					actualValue < nextValue &&
					(
						(actualCharacter == I && (nextCharacter != V && nextCharacter != X))	|| 
						(actualCharacter == X && (nextCharacter != L && nextCharacter != C))	||
						(actualCharacter == C && (nextCharacter != D && nextCharacter != M))	||
						(actualCharacter == V && (nextCharacter != V && nextCharacter != X))
					)
			) {
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * This method will extrat from		pRoman		a Character from specific	pIndex		and converts this to an Integer
	 * 
	 * @param pRoman
	 * @param pIndex
	 * 
	 * @return
	 */
	private static int getValue(String pRoman, int pIndex) {
		char actualCharacter = pRoman.charAt(pIndex);
		int actualValue = convertRomanToArabic(Character.toString(actualCharacter));
		return actualValue;
	}
	
	/**
	 * This method are goind to receive an Arabic digit and its Multiplier (that indicates in which digit its used to be place. Ex: unity, tens, hundreds, thousands, etc)
	 * and converts it to a Roman character
	 * 
	 * @param pArabicDigit
	 * @param pMultiplier
	 * 
	 * @return
	 */
	public static StringBuffer getRomanValue(int pArabicDigit, int pMultiplier) {
		StringBuffer romanNumberSB = new StringBuffer("");
		
		char lessSignificant = ' ';
		char meanSignificant = ' ';
		char moreSignificant = ' ';
		if ( pMultiplier == 1 ) {
			lessSignificant = I;
			meanSignificant = V;
			moreSignificant = X;
		} else {
			if ( pMultiplier == 10 ) {
				lessSignificant = X;
				meanSignificant = L;
				moreSignificant = C;
			} else {
				if ( pMultiplier == 100 ) {
					lessSignificant = C;
					meanSignificant = D;
					moreSignificant = M;
				} else  if ( pMultiplier == 1000 ) {
					lessSignificant = M;
				}
			}
		}
		romanNumberSB = mapArabitToRoman(pArabicDigit, romanNumberSB, lessSignificant, meanSignificant, moreSignificant);
		
		String response = romanNumberSB.toString();
		response = response.toString();
		
		return romanNumberSB;
	}
	
	/**
	 * This method does an intricate process, to convert Arabic Numbers in Roman numbers, taking in consideration an Arabic, and already know Roman peaces of information, based on external know multipliers
	 * 
	 * @param pArabicDigit
	 * @param pRomanNumberSB
	 * @param pLessSignificant
	 * @param pMeanSignificant
	 * @param pMoreSignificant
	 * 
	 * @return
	 */
	private static StringBuffer mapArabitToRoman(
		int				pArabicDigit, 
		StringBuffer	pRomanNumberSB, 
		char			pLessSignificant,
		char			pMeanSignificant,
		char			pMoreSignificant
	) {
		if (  pArabicDigit >= 1 && pArabicDigit < 4 ) {
			for ( int index = 0; index < pArabicDigit; index = index + 1 ) {
				pRomanNumberSB = pRomanNumberSB.append( Character.toString(pLessSignificant) );
			}
		} else if (  pArabicDigit >= 4 && pArabicDigit < 9  ) {
			if ( pArabicDigit == 4 ) {
				pRomanNumberSB = pRomanNumberSB.append( Character.toString(pLessSignificant) );
			}
			
			pRomanNumberSB = pRomanNumberSB.append( Character.toString(pMeanSignificant) );
			
			for ( int index = 6; index <= pArabicDigit; index = index + 1 ) {
				pRomanNumberSB = pRomanNumberSB.append( Character.toString(pLessSignificant) );
			}
		} else if (  pArabicDigit == 9  ) {
			if ( pArabicDigit == 9 ) {
				pRomanNumberSB = pRomanNumberSB.append( Character.toString(pLessSignificant) );
			}
			
			pRomanNumberSB = pRomanNumberSB.append( Character.toString(pMoreSignificant) );
		}
		return pRomanNumberSB;
	}
	
	private static boolean isCharacterEqualsPrevious(String pRoman, int pIndex) {
		return pRoman.charAt(pIndex) == pRoman.charAt(pIndex - 1);
	}
	
}