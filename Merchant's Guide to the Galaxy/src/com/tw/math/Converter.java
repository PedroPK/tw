package com.tw.math;

import static com.tw.utils.Constants.*;
import static com.tw.utils.Utils.*;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	private Map<String, Character>	aUnitMap;
	private Map<String, Double>	aVariableMap;
	
	public Converter() {
		instanciateUnitMapping();
		instanciateValuationMapping();
	}
	
	/**
	 * Main method, that reads a How Much/Many sentence, process it, and gives a response;
	 * 
	 * @param pReadLine
	 * @return
	 */
	public String processHowSentence(String pReadLine) {
		StringBuffer response = new StringBuffer();
		
		if ( isHowMuchManySentenceValid(pReadLine) ) {
			StringBuffer variable = new StringBuffer();
			StringBuffer credits = new StringBuffer();
			
			List<String> multipliers = new ArrayList<String>();
			if ( isHowMuchSentenceValid(pReadLine) ) {
				multipliers = getMultipliersFromHowMuchSentence(pReadLine);
			} else if ( isHowManySentenceValid(pReadLine) ) {
				multipliers = getMultipliersFromHowManySentence(pReadLine);
				
				List<String> terms = split(pReadLine);
				variable = variable.append(terms.get( terms.size() -2 )).append(" ");
				
				credits = new StringBuffer(" Credits");
			}
			
			// Repeating the Multipliers
			StringBuffer sbMultipliers = new StringBuffer();
			for (String multiplier: multipliers) {
				sbMultipliers = sbMultipliers.append(multiplier).append(" ");
			}
			
			response = response.append(sbMultipliers);
			
			// Append Variable name, if its a How Many sentence
			response = response.append(variable);
					
			
			// Put the IS verb
			response = response.append(IS);
			
			// Append the Numerical Value;
			String romanMultipliers = convertMultipliersToRoman(sbMultipliers.toString());
			int arabibNumer = convertRomanToArabic(romanMultipliers);
			
			int finalValue = arabibNumer;
			
			if (	isStringValid( variable.toString() )	) {
				String variableString = variable.toString().trim();
				double variableValue = this.aVariableMap.get(variableString);
				finalValue = (int) (finalValue * variableValue);
			}
			
			// Do the Math
			response = response.append(" ").append(finalValue);
			
			// Append Credits, if its a How Many sentence
			response = response.append(credits);
		} else {
			response = new StringBuffer("I have no idea what you are talking about");
		}
		
		return response.toString();
	}
	
	public double getVariableValue(String pVariableName) {
		double response = Integer.MIN_VALUE;
		
		response = this.aVariableMap.get(pVariableName);
		
		return response;
	}
	
	public void addValuation(String pReadLine) {
		if ( this.isValuationSentence(pReadLine) ) {
			String romanMultiplier = this.extratMultipliersAndConvertToRoman(pReadLine);
			int multiplier		= convertRomanToArabic(romanMultiplier);
			String variable		= getVariableName(pReadLine);
			int value			= getAttributedValue(pReadLine);
			
			BigDecimal dividend = new BigDecimal(value);
			BigDecimal divisor = new BigDecimal(multiplier);
			double variableValue = dividend.divide(divisor).doubleValue();
			
			this.aVariableMap.put(variable, variableValue);
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
				terms.get(terms.size() - 1).equalsIgnoreCase(QUESTION_MARK)
		) {
			response = 
				isHowMuchSentenceValid(pReadLine)	||
				isHowManySentenceValid(pReadLine);
		}
		
		return response;
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
	 * 
	 * TODO Implement this Method
	 */
	public boolean isHowMuchSentenceValid(String pReadLine) {
		boolean response = false;
		
		List<String> terms		= split(pReadLine);
		
		if (	isEqualsHow(			terms.get(0))						&&
				isEqualsMuch(			terms.get(1))						&&
				isEqualsIs(				terms.get(2))						&&
				isEqualsQuestionMark(	terms.get(terms.size() - 1))
		) {
			// From now on, only have Multipliers
			List<String> multipliers = getMultipliersFromHowMuchSentence(terms);
			
			if (	areAllOriginalMultipliersValid(multipliers)		) {
				response = true;
			}
		}
		
		return response;
	}
	
	private List<String> getMultipliersFromHowMuchSentence(String pReadLine) {
		List<String> terms		= split(pReadLine);
		
		int finalMultipliersIndex = terms.size() - 2;
		List<String> multipliers = new ArrayList<String>();
		for ( int index = 3; index <= finalMultipliersIndex; index = index + 1 ) {
			multipliers.add(terms.get(index));
		}
		return multipliers;
	}
	
	private List<String> getMultipliersFromHowMuchSentence(List<String> pTerms) {
		int finalMultipliersIndex = pTerms.size() - 2;
		List<String> multipliers = new ArrayList<String>();
		for ( int index = 4; index < finalMultipliersIndex; index = index + 1 ) {
			multipliers.add(pTerms.get(index));
		}
		return multipliers;
	}
	
	private boolean isEqualsQuestionMark(String pString) {
		return pString.equalsIgnoreCase(QUESTION_MARK);
	}
	
	private boolean isEqualsIs(String pString) {
		return pString.equalsIgnoreCase(IS);
	}
	
	private boolean isEqualsMuch(String pString) {
		return pString.equalsIgnoreCase(MUCH);
	}
	
	private boolean isEqualsHow(String pString) {
		return pString.equalsIgnoreCase(HOW);
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
	 * 
	 * TODO Implement this method
	 */
	public boolean isHowManySentenceValid(String pReadLine) {
		boolean response = false;
		
		List<String> terms = split(pReadLine);
		if (	terms.get(0).equalsIgnoreCase(HOW)								&&
				terms.get(1).equalsIgnoreCase(MANY)								&&
				terms.get(2).equalsIgnoreCase(CREDITS)							&&
				terms.get(3).equalsIgnoreCase(IS)								&&
				terms.get(terms.size() - 1).equalsIgnoreCase(QUESTION_MARK)
		) {
			// Here we should have Multiplier(s) and a Variable
			int variableIndex = terms.size() - 2;
			
			List<String> multipliers = getMultipliersFromHowManySentence(terms);
			
			boolean areAllOriginalMultipliersValid = areAllOriginalMultipliersValid(multipliers);
			boolean isVariableValid = this.aVariableMap.containsKey(terms.get(variableIndex));
			
			if (
					areAllOriginalMultipliersValid	&&
					isVariableValid
			) {
				response = true;
			}
		}
		
		return response;
	}
	
	private List<String> getMultipliersFromHowManySentence(String pReadLine) {
		List<String> terms		= split(pReadLine);
		
		int finalMultipliersIndex = terms.size() - 3;
		List<String> multipliers = new ArrayList<String>();
		for ( int index = 4; index <= finalMultipliersIndex; index = index + 1 ) {
			multipliers.add(terms.get(index));
		}
		return multipliers;
	}
	
	private List<String> getMultipliersFromHowManySentence(List<String> pTerms) {
		int finalMultipliersIndex = pTerms.size() - 3;
		List<String> multipliers = new ArrayList<String>();
		for ( int index = 4; index < finalMultipliersIndex; index = index + 1 ) {
			multipliers.add(pTerms.get(index));
		}
		return multipliers;
	}
	
	// TODO Test this
	public String evaluateHowMuchManySentence(String pReadLine) {
		String response = null;
		
		List<String> sentenceTerms = split(pReadLine);
		
		
		
		return response;
	}
	
	public String extratMultipliersAndConvertToRoman(String pReadLine) {
		String response = null;
		
		if ( 
				isStringValid(pReadLine)
				//this.isValuationSentence(pReadLine) 
		) {
			boolean areAllOriginalMultipliersValid = areAllOriginalMultipliersValid(pReadLine);
			
			if ( areAllOriginalMultipliersValid ) {
				List<String> originalMultipliers= 
					split(
						splitToGetOriginalMultiplierTerms(pReadLine)
					);
				StringBuffer sbMultipliers = new StringBuffer("");
				for (String multiplier : originalMultipliers) {
					Character romanMultiplier = this.aUnitMap.get(multiplier);
					sbMultipliers = sbMultipliers.append(Character.toString(romanMultiplier));
				}
				response = sbMultipliers.toString();
				
			}
			
		}
		
		return response;
	}
	
	// TODO New Method
	public String convertMultipliersToRoman(String pMultipliers) {
		String response = null;
		
		if ( 
				isStringValid(pMultipliers)
		) {
			List<String> originalMultipliers= split( pMultipliers );
			
			StringBuffer sbMultipliers = new StringBuffer("");
			for (String multiplier : originalMultipliers) {
				Character romanMultiplier = this.aUnitMap.get(multiplier);
				sbMultipliers = sbMultipliers.append(Character.toString(romanMultiplier));
			}
			response = sbMultipliers.toString();
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
			
			if ( !this.aUnitMap.containsKey(actualMultiplier) ) {
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
						isVerbTerm.equals(IS)		
				) {
					//String variableTerm	= getVariableName(sentenceTerms);
					
					for ( int index = sentenceTerms.size() - 5; index >= 0; index = index - 1 ) {
						String romanianTerm	= sentenceTerms.get( index );
						
						if ( !this.aUnitMap.containsKey(romanianTerm) ) {
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
			if ( this.aUnitMap == null ) {
				instanciateUnitMapping();
			}
			
			List<String> sentenceTerms = getSentenceTerms(pReadLine);
			
			this.aUnitMap.put( 
				sentenceTerms.get(0),			//	Variable
				//sentenceTerms.get(1)				is
				sentenceTerms.get(2).charAt(0)	//	Roman Numeral
			);
		}
	}
	
	private void instanciateUnitMapping() {
		this.aUnitMap = new HashMap<String, Character>();
	}
	
	private void instanciateValuationMapping() {
		this.aVariableMap = new HashMap<String, Double>();
	}
	
	public char getMapping(String pVariable) {
		char response = ' ';
		
		if ( isStringValid(pVariable) && this.aUnitMap != null) {
			response = this.aUnitMap.get(pVariable);
		}
		
		return response;
	}
	
	/**
	 * This method indicates if the parameter String contains a Unit to Roman Mapping Sentence
	 * 
	 * @param		pReadLine
	 * 
	 * @return		boolean		Indicantes if the	pReadLine	contais a Unit to Roman Mapping Sentence
	 */
	public static boolean isMappingSentence(String pReadLine) {
		List<String> sentenceTerms = getSentenceTerms(pReadLine);
		
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