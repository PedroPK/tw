package com.tw.math;

import static com.tw.utils.Utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	
	private static final String QUESTION_MARK = "?";
	private static final String MANY = "many";
	private static final String IS = "is";
	private static final String MUCH = "much";
	private static final String HOW = "how";
	private static final String CREDITS = "Credits";
	private static final String IS_VERB = IS;
	
	public static final char I = 'I';
	public static final char V = 'V';
	public static final char X = 'X';
	public static final char L = 'L';
	public static final char C = 'C';
	public static final char D = 'D';
	public static final char M = 'M';
	
	private Map<String, Character>	aUnitMapping;
	private Map<String, Integer>	aValuationMapping;
	
	public Converter() {
		instanciateUnitMapping();
		instanciateValuationMapping();
	}
	
	// TODO Complete this implementation
	public void addValuation(String pReadLine) {
		if ( this.isValuationSentence(pReadLine) ) {
			String romanMultiplier = this.convertOriginalMultiplierToRoman(pReadLine);
			int multiplier = convertRomanToArabic(romanMultiplier);
		}
	}
	
	/**
	 * Example of valid sentences
	 * 		glob glob Silver is 34 Credits
	 * 		glob prok Gold is 57800 Credits
	 * 		pish pish Iron is 3910 Credits
	 */
	public int getAttributedValue(String pReadLine) {
		int value = Integer.MIN_VALUE;
		
		if ( isValuationSentence(pReadLine) ) {
			List<String> terms = split(pReadLine);
			
			value = Integer.parseInt(terms.get(terms.size() -2));
		}
		
		return value;
	}
	
	/**
	 * Example of Valid Sentences
	 * 		how much is pish tegj glob glob ?
	 * 		how many Credits is glob prok Silver ?
	 * 		how many Credits is glob prok Gold ?
	 * 		how many Credits is glob prok Iron ?
	 * 
	 * @param		pReadLine
	 * 
	 * @return		boolean		Indicates if the 	pReadLine	is a Valid Sentence
	 */
	public boolean isHowMuchManySentenceValid(String pReadLine) {
		boolean response = false;
		
		List<String> terms = split(pReadLine);
		if (	terms.get(0).equalsIgnoreCase(HOW)						&&
				(
					(
						terms.get(1).equalsIgnoreCase(MUCH)		&&
						terms.get(2).equalsIgnoreCase(IS)
					) 													||
					(
						terms.get(1).equalsIgnoreCase(MANY)		&&
						terms.get(2).equalsIgnoreCase(CREDITS)	&&
						terms.get(3).equalsIgnoreCase(IS)
					)
				)															&&
				terms.get(terms.size() - 1).equalsIgnoreCase(QUESTION_MARK)
		) {
			boolean hasVariable = false;
			
			int finalMultipliersIndex = 0;
			int variableIndex = -1;
			if (
				terms.get(1).equalsIgnoreCase(MUCH)	&&
				terms.get(2).equalsIgnoreCase(IS)
			) {
				// From now on, only have Multipliers
				finalMultipliersIndex = terms.size() - 2;
			} else if (
				terms.get(1).equalsIgnoreCase(MANY)		&&
				terms.get(2).equalsIgnoreCase(CREDITS)	&&
				terms.get(3).equalsIgnoreCase(IS)
			) {
				// Here we should have Multiplier(s) and a Variable
				finalMultipliersIndex = terms.size() - 3;
				variableIndex = terms.size() - 2;
			}
			List<String> multipliers = new ArrayList<String>();
			for ( int index = 4; index < finalMultipliersIndex; index = index + 1 ) {
				multipliers.add(terms.get(index));
			}
			
			boolean areAllOriginalMultipliersValid = areAllOriginalMultipliersValid(multipliers);
			if (
					areAllOriginalMultipliersValid	&&
					(
						!hasVariable	||
						(	
							hasVariable			&&
							variableIndex > 0	
							// TODO Complete this with Variable checking &&
						)
					)
			) {
				response = true;
			}
		}
		
		return response;
	}
	
	// TODO Test this
	public String evaluateHowMuchManySentence(String pReadLine) {
		String response = null;
		
		List<String> sentenceTerms = split(pReadLine);
		
		
		
		return response;
	}
	
	public String convertOriginalMultiplierToRoman(String pReadLine) {
		String response = null;
		
		if ( this.isValuationSentence(pReadLine) ) {
			boolean areAllOriginalMultipliersValid = areAllOriginalMultipliersValid(pReadLine);
			
			if ( areAllOriginalMultipliersValid ) {
				List<String> originalMultipliers= 
					split(
						splitToGetOriginalMultiplierTerms(pReadLine)
					);
				StringBuffer sbMultipliers = new StringBuffer("");
				for (String multiplier : originalMultipliers) {
					Character romanMultiplier = this.aUnitMapping.get(multiplier);
					sbMultipliers = sbMultipliers.append(Character.toString(romanMultiplier));
				}
				response = sbMultipliers.toString();
				
			}
			
		}
		
		return response;
	}
	
	public boolean areAllOriginalMultipliersValid(String pResponse) {
		String variableName = getVariableName(pResponse);
		List<String> multiplierAndPredicateTerms = split(pResponse, variableName);
		List<String> multipliers = split(multiplierAndPredicateTerms.get(0));
		
		boolean areAllOriginalMultipliersValid = 
			areAllOriginalMultipliersValid(multipliers);
		return areAllOriginalMultipliersValid;
	}
	
	private boolean areAllOriginalMultipliersValid(List<String> pOriginalMultiplierTerms) {
		boolean areAllOriginalMultipliersValid = true;
		for ( String actualMultiplier : pOriginalMultiplierTerms ) {
			
			if ( !this.aUnitMapping.containsKey(actualMultiplier) ) {
				areAllOriginalMultipliersValid = false;
			}
		}
		return areAllOriginalMultipliersValid;
	}
	
	public String getSentenceOriginalMultiplier(String pReadLine) {
		String response = null;
		
		if ( this.isValuationSentence(pReadLine) ) {
			response = splitToGetOriginalMultiplierTerms(pReadLine);
		}
		
		return response;
	}
	
	public static String splitToGetOriginalMultiplierTerms(String pReadLine) {
		String variableTerm = getVariableName(pReadLine);
		
		List<String> sentenceTerms = split(pReadLine, " " + variableTerm);
		String response = sentenceTerms.get(0);
		return response;
	}
	
	public static String getVariableName(String pReadLine) {
		List<String> sentenceTerms = getSentenceTerms(pReadLine);
		
		String variableTerm	= getVariableName(sentenceTerms);
		return variableTerm;
	}
	/**
	 * Example sentences:
	 * 		glob glob Silver is 34 Credits
	 * 		glob prok Gold is 57800 Credits
	 * 		pish pish Iron is 3910 Credits
	 * 		
	 * @param		pReadLine
	 * 
	 * @return		boolean		Indicates if the 	pReadLine	is and ValuationSentence
	 */
	public boolean isValuationSentence(String pReadLine) {
		boolean isMappingSentence = false;
		
		if ( isStringValid(pReadLine) ) { 
			List<String> sentenceTerms = getSentenceTerms(pReadLine);
			
			String creditTerm	= sentenceTerms.get(sentenceTerms.size() - 1 );
			String numericTerm	= sentenceTerms.get(sentenceTerms.size() - 2 );
			String isVerbTerm	= sentenceTerms.get(sentenceTerms.size() - 3 );
			
			if (	sentenceTerms != null && sentenceTerms.size() >= 5 ) {
				if (	creditTerm.equals(CREDITS)		&&
						isNumeric(numericTerm)			&&
						isVerbTerm.equals(IS_VERB)		
				) {
					//String variableTerm	= getVariableName(sentenceTerms);
					
					for ( int index = sentenceTerms.size() - 5; index >= 0; index = index - 1 ) {
						String romanianTerm	= sentenceTerms.get( index );
						
						if ( !this.aUnitMapping.containsKey(romanianTerm) ) {
							break;	// Does not have at least one of this terms, so the response should be False.
						}
					}
					
					isMappingSentence = true;
				}
			}
		}
		
		return isMappingSentence;
	}
	
	private static String getVariableName(List<String> pSentenceTerms) {
		return pSentenceTerms.get( pSentenceTerms.size() - 4 );
	}
	
	public void addMapping(String pReadLine) {
		if ( isMappingSentence(pReadLine) ) {
			if ( this.aUnitMapping == null ) {
				instanciateUnitMapping();
			}
			
			List<String> sentenceTerms = getSentenceTerms(pReadLine);
			
			this.aUnitMapping.put( 
				sentenceTerms.get(0),			//	Variable
				//sentenceTerms.get(1)				is
				sentenceTerms.get(2).charAt(0)	//	Roman Numeral
			);
		}
	}
	
	private void instanciateUnitMapping() {
		this.aUnitMapping = new HashMap<String, Character>();
	}
	
	private void instanciateValuationMapping() {
		this.aValuationMapping = new HashMap<String, Integer>();
	}
	
	public char getMapping(String pVariable) {
		char response = ' ';
		
		if ( isStringValid(pVariable) && this.aUnitMapping != null) {
			response = this.aUnitMapping.get(pVariable);
		}
		
		return response;
	}
	
	private boolean isMappingSentence(String pReadLine) {
		List<String> sentenceTerms = getSentenceTerms(pReadLine);
		
		Set<Character> romanValues = getRomanNumerals();
		
		boolean isMappingSentence = false;
		if (	sentenceTerms != null && sentenceTerms.size() == 3		&&
				
				sentenceTerms.get(0).length() >= 0						&&
				
				sentenceTerms.get(1).equals(IS_VERB)					&&
				
				sentenceTerms.get(2).length() == 1 &&
				romanValues.contains( sentenceTerms.get(2).charAt(0) )
		) {
			isMappingSentence = true;
		}
		return isMappingSentence;
	}
	
	private Set<Character> getRomanNumerals() {
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
	
	private static List<String> getSentenceTerms(String pReadLine) {
		return split(pReadLine, " ");
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
	
	private static int getArabicValue(String pArabic) {
		int arabicValue = Integer.parseInt(pArabic);
		return arabicValue;
	}
	
	private static int getArabicDigit(char pArabic) {
		int arabicValue = 
			Integer.parseInt( 
				Character.toString( pArabic ) 
			);
		return arabicValue;
	}
	
	private static boolean isArabicValid(String pArabic) {
		boolean isValid = true;
		try {
			Integer.parseInt(pArabic);
		} catch ( NumberFormatException nfe ) {
			isValid = false;
		}
		return isValid;
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
		
		if ( has4ConsecutiveRepetitions(pRoman) ) {
			throw new FourTimesRepetitionException();
		}
		
		/*if ( !isValidDecrescentOrder(pRoman) ) {
			throw new InvalidRomanException(pRoman);
		}*/
		
		if ( hasInvalidInternalSubtraction(pRoman) ) {
			throw new InvalidRomanException(pRoman);
		}
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
	
	private static int getValue(String pRoman, int pIndex) {
		char actualCharacter = pRoman.charAt(pIndex);
		int actualValue = convertRomanToArabic(Character.toString(actualCharacter));
		return actualValue;
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
	
	public static StringBuffer getRomanValue(int pArabicDigit, int pMultiplier) {
		StringBuffer stringBuffer = new StringBuffer("");
		
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
		stringBuffer = mapArabitToRoman(pArabicDigit, stringBuffer, lessSignificant, meanSignificant, moreSignificant);
		
		String response = stringBuffer.toString();
		response = response.toString();
		
		return stringBuffer;
	}
	
	private static StringBuffer mapArabitToRoman(
		int				pArabicDigit, 
		StringBuffer	pStringBuffer, 
		char			pLessSignificant,
		char			pMeanSignificant,
		char			pMoreSignificant
	) {
		if (  pArabicDigit >= 1 && pArabicDigit < 4 ) {
			for ( int index = 0; index < pArabicDigit; index = index + 1 ) {
				pStringBuffer = pStringBuffer.append( Character.toString(pLessSignificant) );
			}
		} else if (  pArabicDigit >= 4 && pArabicDigit < 9  ) {
			if ( pArabicDigit == 4 ) {
				pStringBuffer = pStringBuffer.append( Character.toString(pLessSignificant) );
			}
			
			pStringBuffer = pStringBuffer.append( Character.toString(pMeanSignificant) );
			
			for ( int index = 6; index <= pArabicDigit; index = index + 1 ) {
				pStringBuffer = pStringBuffer.append( Character.toString(pLessSignificant) );
			}
		} else if (  pArabicDigit == 9  ) {
			if ( pArabicDigit == 9 ) {
				pStringBuffer = pStringBuffer.append( Character.toString(pLessSignificant) );
			}
			
			pStringBuffer = pStringBuffer.append( Character.toString(pMoreSignificant) );
		}
		return pStringBuffer;
	}
	
	private static int sumOrSubtract(int pResponseValue, boolean inShouldSubtract, int pToSumOrSubtractValue) {
		if ( !inShouldSubtract ) {
			pResponseValue = pResponseValue + pToSumOrSubtractValue;
		} else {
			pResponseValue = pResponseValue - pToSumOrSubtractValue;
		}
		return pResponseValue;
	}
	
	public static boolean has4ConsecutiveRepetitions(String pRoman) {
		boolean response = false;
		
		if ( isStringValid(pRoman) ) {
			int repetitions = 1;
			for ( int index = 0; index < pRoman.length(); index = index + 1 ) {
				if ( index > 0 && isCharacterEqualsPrevious(pRoman, index) ) {
					repetitions = repetitions + 1;
					
					if ( repetitions >= 4 ) {
						// It should break here, to avoid the case of a IIIV to be considered valid.
						break;
					}
				} else {
					repetitions = 1;
				}
			}
			
			if ( repetitions > 3 ) {
				response = true;
			}
		}
		
		return response;
	}
	
	private static boolean isCharacterEqualsPrevious(String pRoman, int pIndex) {
		return pRoman.charAt(pIndex) == pRoman.charAt(pIndex - 1);
	}
	
}