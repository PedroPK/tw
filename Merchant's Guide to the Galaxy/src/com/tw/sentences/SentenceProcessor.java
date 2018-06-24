package com.tw.sentences;

import static com.tw.utils.Constants.*;
import static com.tw.utils.Utils.*;
import static com.tw.math.Converter.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentenceProcessor {
	
	private Map<String, Character>	aUnitMap;
	private Map<String, Double>	aVariableMap;
	
	public SentenceProcessor() {
		instanciateUnitMapping();
		instanciateValuationMapping();
	}
	
	private BigDecimal calculateFinalValue(StringBuffer pVariableName, int pArabicNumer) {
		BigDecimal finalValue = new BigDecimal(pArabicNumer);
		
		if (	isStringValid( pVariableName.toString() )	) {
			String variableName = pVariableName.toString().trim();
			double variableValue = this.aVariableMap.get(variableName);
			finalValue = finalValue.multiply(new BigDecimal(variableValue));
		}
		return finalValue;
	}
	
	private StringBuffer appendNumericalValue(StringBuffer pResponse, StringBuffer pVariableName, StringBuffer pMultipliers) {
		String romanMultipliers = convertMultipliersToRoman(pMultipliers.toString());
		int arabicNumber = convertRomanToArabic(romanMultipliers);
		
		// Do the Math
		BigDecimal finalValue = calculateFinalValue(pVariableName, arabicNumber);
		
		finalValue = processDecimalValues(finalValue);
		
		pResponse = pResponse.append(" ").append(finalValue);
		return pResponse;
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
			BigDecimal divisionResult = dividend.divide(divisor, 10, RoundingMode.HALF_EVEN);
			double variableValue = divisionResult.doubleValue();
			
			this.aVariableMap.put(variable, variableValue);
		}
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
			
			List<String> multipliersList = new ArrayList<String>();
			if ( isHowMuchSentenceValid(pReadLine) ) {
				multipliersList = getMultipliersFromHowMuchSentence(pReadLine);
			} else if ( isHowManySentenceValid(pReadLine) ) {
				multipliersList = getMultipliersFromHowManySentence(pReadLine);
				
				List<String> terms = split(pReadLine);
				variable = variable.append(terms.get( terms.size() -2 )).append(" ");
				
				credits = new StringBuffer(" Credits");
			}
			
			// Repeating the Multipliers
			StringBuffer multipliersSB = getMultipliers(multipliersList);
			
			// Append Multipliers to the Response sentence
			response = response.append(multipliersSB);
			
			// Append Variable name, if its a How Many sentence
			response = response.append(variable);
			
			// Put the IS verb
			response = response.append(IS);
			
			// Append the Numerical Value;
			response = appendNumericalValue(response, variable, multipliersSB);
			
			// Append Credits, if its a How Many sentence
			response = response.append(credits);
		} else {
			response = new StringBuffer(I_HAVE_NO_IDEA_WHAT_YOU_ARE_TALKING_ABOUT);
		}
		
		return response.toString();
	}
	
	/**
	 * Assuming that the	pReadLine	is a How Much sentence [this test can be done by isHowMuchSentenceValid() method], 
	 * this method are going to split it in its terms, and extract from them the multipliers (ex: glob prok pish tegj)
	 * 
	 * @param	pReadLine	String with a HowMuch sentence
	 * 
	 * @return	List containing only the multipliers of the How Much sentence
	 */
	private List<String> getMultipliersFromHowMuchSentence(String pReadLine) {
		List<String> terms		= split(pReadLine);
		
		int finalMultipliersIndex = terms.size() - 2;
		List<String> multipliers = new ArrayList<String>();
		for ( int index = 3; index <= finalMultipliersIndex; index = index + 1 ) {
			multipliers.add(terms.get(index));
		}
		return multipliers;
	}
	
	/**
	 * Assuming that the	pReadLine	is a How Much sentence [this test can be done by isHowMuchSentenceValid() method], 
	 * this method are going take the terms, spliced before by the homonym overloaded method, and extract from them only the multipliers (ex: glob prok pish tegj)
	 * 
	 * @param		pTerms	List collection, containing all the terms from a How Much sentence
	 * @return		List containing only the multipliers of the How Much sentence
	 */
	private List<String> getMultipliersFromHowMuchSentence(List<String> pTerms) {
		int finalMultipliersIndex = pTerms.size() - 2;
		List<String> multipliers = new ArrayList<String>();
		for ( int index = 4; index < finalMultipliersIndex; index = index + 1 ) {
			multipliers.add(pTerms.get(index));
		}
		return multipliers;
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
	
	private boolean areAllOriginalMultipliersValid(List<String> pOriginalMultiplierTerms) {
		boolean areAllOriginalMultipliersValid = true;
		for ( String actualMultiplier : pOriginalMultiplierTerms ) {
			
			if ( !this.aUnitMap.containsKey(actualMultiplier) ) {
				areAllOriginalMultipliersValid = false;
			}
		}
		return areAllOriginalMultipliersValid;
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
				if (	
						(
							creditTerm.equals(CREDITS)	||
							creditTerm.equals(CREDIT)
						)									&&
						isNumeric(numericTerm)				&&
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
	
	public String getSentenceOriginalMultiplier(String pReadLine) {
		String response = null;
		
		if ( this.isValuationSentence(pReadLine) ) {
			response = splitToGetOriginalMultiplierTerms(pReadLine);
		}
		
		return response;
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
	 * This method gets a List of Multipliers, previously extrated from a Sentence, and converts them to a StringBuffer
	 * 
	 * @param		pMultipliersList	A List collection with Multipliers 
	 * 
	 * @return		StringBuffer		A StringBuffer containing all the Multipliers, in the original order
	 */
	public static StringBuffer getMultipliers(List<String> pMultipliersList) {
		StringBuffer multipliersSB = new StringBuffer();
		for ( String multiplier: pMultipliersList ) {
			multipliersSB = multipliersSB.append(multiplier).append(" ");
		}
		return multipliersSB;
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
	
	public static List<String> getSentenceTerms(String pReadLine) {
		return split(pReadLine, " ");
	}
	
	private static String getVariableName(List<String> pSentenceTerms) {
		return pSentenceTerms.get( pSentenceTerms.size() - 4 );
	}
	
}