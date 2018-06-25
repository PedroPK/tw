package com.tw.sentences;

import static com.tw.utils.Constants.*;
import static com.tw.utils.Utils.*;
import static com.tw.math.Converter.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.tw.math.Converter;
import com.tw.math.exceptions.EmptyRomanException;
import com.tw.math.exceptions.FourTimesRepetitionException;
import com.tw.math.exceptions.InvalidArabicException;
import com.tw.math.exceptions.InvalidRomanException;

/**
 * This is the Main class of this application, and the one that has a executable Main Method
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * This documentation will explain the decisions that I took during this problem solving and its implementation
 * 
 * 1) Fist thing that I thought was that I should be original and find the answers how to solve it by my self. It was very likely that I could find 
 * in the internet for a similar problem and its solution, tested by many people, with more elegant and simple implementations then I would do, 
 * but in my mind, you guys would want something made by me.
 * 
 * With this in mind, I did it by my way, organically, knowing that probably there are better ways, more elegant and simple, to do the same job.
 * This implementation evolved almost by natural selection. The solutions were improved by increasing the Test cases. Eventually, the implementation
 * suffered refactoring, once I was confident that the Tests would approve it if the resulting methods were not only more elegant, but also functional. 
 * 
 * 2) Having said that, the second thing that I was worried about was how to do conversions between Roman numbers to Arabic Numbers.
 * 
 * The best way that I know how to do this is using TDD (Test Driven Development). So, I created empty methods, jumped to implement the first test, 
 * executed it, saw it fail, and jump back to implement a minimal code to make the first test pass, and started creating more elaborated tests, 
 * keeping this cycle alive, until the moment that I feel that is enough robust to start implementing another methods.
 * 
 * 3) The third priority was how to map the Nouns (ex: glob, prok, pish, tegj, etc...) with its Roman numbers.
 * A subpriority was decide if its a valid mapping or not
 * 
 * 4) The fourth priority was to process sentences that was a Variable on it, mixed with Nouns and attributing then to Arabic quantity of Credits (ex: glob glob Silver is 34 Credits)
 * 
 * 5) Once this was done and with many Tests for many sceneries, the fifth priority was to process the How Much/Many questions, and answer then appropriately
 * Tests with JUnit also implemented to test this Question/Answers sentences, that was testing all previous implementations by chain reaction
 * 
 * 6) Finally, the last thing that was how to use the Standard Input, capture the keyboard text and integrate it with all the rest of application
 * 
 * 7) A last effort to be done is to do a great refactoring, doing a better organization, removing unnecessary code, making it a little bit better.
 * 
 * An additional explanation is that, once I decided to use Tests in almost anything implemented, there are many public methods, not based in  external utility,
 * but its the way to do Tests with JUnit, without use a more complex frameworks or complicate then with Reflection
 * 
 * @author pedro.c.f.santos
 */
public class SentenceProcessor {
	
	/**
	 * This Attribute do the Noun mapping in Roman Numbers
	 */
	private Map<String, Character>	aNounMultiplier_toRoman_Map;
	
	/**
	 * This Attribute do the Variable mapping in Numerical values
	 */
	private Map<String, Double>	aVariableMap;
	
	public SentenceProcessor() {
		instanciate_NounMultiplier_toRoman_Mapping();
		instanciateValuationMapping();
	}
	
	/**
	 * This method initializes the Noun/Multipliers mapping into Roman Numbers
	 */
	private void instanciate_NounMultiplier_toRoman_Mapping() {
		this.aNounMultiplier_toRoman_Map = new HashMap<String, Character>();
	}
	
	/**
	 * This method initializes the Variable mapping into Real Numbers
	 */
	private void instanciateValuationMapping() {
		this.aVariableMap = new HashMap<String, Double>();
	}
	
	public char getNounMultiplier_RomanNumber(String pVariable) {
		char response = ' ';
		
		if ( isStringValid(pVariable) && this.aNounMultiplier_toRoman_Map != null) {
			response = this.aNounMultiplier_toRoman_Map.get(pVariable);
		}
		
		return response;
	}
	
	/**
	 * This method looks for the Variable by	pVariableName	in the VariableMap, and if it exists, multiply its value by the		pArabicNumber and
	 * returns the resulting value
	 * 
	 * @param pVariableName		Variable name to be looked in aVariableMap
	 * @param pArabicNumer		A numeric multiplier to be multiplied with the variable value
	 * 
	 * @return					The result of Variable value multiplied by the 2nd parameter
	 */
	private BigDecimal calculateFinalValue(StringBuffer pVariableName, int pArabicNumer) {
		BigDecimal finalValue = new BigDecimal(pArabicNumer);
		
		if (	isStringValid( pVariableName.toString() )	) {
			String variableName = pVariableName.toString().trim();
			double variableValue = this.aVariableMap.get(variableName);
			finalValue = finalValue.multiply(new BigDecimal(variableValue));
		}
		return finalValue;
	}
	
	/**
	 * This method will take three StringBuffers. 
	 * The 1st one is part of a Response Sentence to be shown to users.
	 * The 2nd one is a Variable name to be looked at aVariableMap, to get its Value.
	 * The 3rd one is a Multiplier to be multiplied by the Variable Value.
	 * 
	 * @param pResponse
	 * @param pVariableName
	 * @param pMultipliers
	 * 
	 * @return
	 */
	private StringBuffer appendNumericalValue(StringBuffer pResponse, StringBuffer pVariableName, StringBuffer pMultipliers) {
		String romanMultipliers = convertMultipliersToRoman(pMultipliers.toString());
		int arabicNumber = convertRomanToArabic(romanMultipliers);
		
		// Do the Math
		BigDecimal finalValue = calculateFinalValue(pVariableName, arabicNumber);
		
		finalValue = processDecimalValues(finalValue);
		
		pResponse = pResponse.append(" ").append(finalValue);
		return pResponse;
	}
	
	/**
	 * This method  will look for a Variable value in Variable Map.
	 * If the Variable Map does not contain the specified Variable Name, it will return the Minimal value possible for a Double
	 * 
	 * @param		pVariableName
	 * @return
	 */
	public double getVariableValue(String pVariableName) {
		double response = Double.MIN_VALUE;
		
		if ( this.aVariableMap.containsKey(pVariableName) ) {
			response = this.aVariableMap.get(pVariableName);
		}
		
		return response;
	}
	
	/**
	 * This method receives a Read Line Sentence, as a String.
	 * If this Sentence is a kind of Valuation (has Nouns as multipliers, a Variable and a Quantity of Credits assigned to this),
	 * 		It will process it, extract its numerical values, do the necessary calculations and store them into the Variable Map.
	 * 
	 * @param pReadLine
	 */
	public void addValuation(String pReadLine) {
		if ( this.isValuationSentence(pReadLine) ) {
			String romanMultiplier = this.extratMultipliersAndConvertToRoman(pReadLine);
			int multiplier		= convertRomanToArabic(romanMultiplier);
			String variable		= getVariableNameFromValuationSentence(pReadLine);
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
	
	/**
	 * This method assumes that the Read Line contains a How Many kind of Sentence
	 * Assuming that, it will extract all the multiplier Nouns, put them in a List collection of String, and return it.
	 * 
	 * @param		pReadLine	A sentence of How Many kind
	 * 
	 * @return		List collection with all multiplier Nouns
	 */
	private List<String> getMultipliersFromHowManySentence(String pReadLine) {
		List<String> terms		= split(pReadLine);
		
		int finalMultipliersIndex = terms.size() - 3;
		List<String> multipliers = new ArrayList<String>();
		for ( int index = 4; index <= finalMultipliersIndex; index = index + 1 ) {
			multipliers.add(terms.get(index));
		}
		return multipliers;
	}
	
	/**
	 * This method will assume that the 	pTerms		are a List containing all terms from a How Many kind of sentence
	 * Assuming that, it will extract all the multiplier Nouns, put them in a List collection of String, and return it.
	 * 
	 * @param		pTerms		A List with all Terms from a How Many kind of Sentence
	 * 
	 * @return		List collection with only multiplier Nouns
	 */
	private List<String> getMultipliersFromHowManySentence(List<String> pTerms) {
		int finalMultipliersIndex = pTerms.size() - 3;
		List<String> multipliers = new ArrayList<String>();
		for ( int index = 4; index < finalMultipliersIndex; index = index + 1 ) {
			multipliers.add(pTerms.get(index));
		}
		return multipliers;
	}
	
	/**
	 * This method will receive a Sentence, and test if its a kind of How Many sentence
	 * 
	 * Example of Valid Sentences
	 * 		how many Credits is glob prok Silver ?
	 * 		how many Credits is glob prok Gold ?
	 * 		how many Credits is glob prok Iron ?
	 * 
	 * @param		pReadLine	A How Many kind of Sentence
	 * 
	 * @return		boolean		Indicates if the 	pReadLine	is a Valid Sentence
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
	 * This method will receive a Sentence, and test if its a kind of How Much or a How Many sentence
	 * 
	 * Example of Valid Sentences
	 * 		how much is pish tegj glob glob ?
	 * 		how many Credits is glob prok Silver ?
	 * 		how many Credits is glob prok Gold ?
	 * 		how many Credits is glob prok Iron ?
	 * 
	 * @param		pReadLine	A How Much/Many kind of Sentence
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
	 * This method assumes that it will receive a Valuation Sentence
	 * It will extract from it the Noun/Multipliers, and then convert them to a Roman Number
	 * 
	 * @param		pReadLineValuationSentence		A Valuation Sentence
	 * @return		String							A Roman Number
	 */
	public String extratMultipliersAndConvertToRoman(String pReadLineValuationSentence) {
		String response = null;
		
		if (	isStringValid(pReadLineValuationSentence)	) {
			boolean areAllOriginalMultipliersValid = areAllOriginalMultipliersValidFromValuationSentence(pReadLineValuationSentence);
			
			if ( areAllOriginalMultipliersValid ) {
				List<String> originalMultipliers= 
					split(
						splitToGetOriginalMultiplierTermsFromValuationSentence(pReadLineValuationSentence)
					);
				StringBuffer sbMultipliers = new StringBuffer("");
				for (String multiplier : originalMultipliers) {
					Character romanMultiplier = this.aNounMultiplier_toRoman_Map.get(multiplier);
					sbMultipliers = sbMultipliers.append(Character.toString(romanMultiplier));
				}
				response = sbMultipliers.toString();
				
			}
			
		}
		
		return response;
	}
	
	/**
	 * This method will test if all the terms in the List it receives are all Nouns/Multiplier
	 * 
	 * @param		pOriginalMultiplierTerms		List with Terms
	 * @return		boolean							Answer about if all terms are Nouns/Multipliers
	 */
	private boolean areAllOriginalMultipliersValid(List<String> pOriginalMultiplierTerms) {
		boolean areAllOriginalMultipliersValid = true;
		for ( String actualMultiplier : pOriginalMultiplierTerms ) {
			
			if ( !this.aNounMultiplier_toRoman_Map.containsKey(actualMultiplier) ) {
				areAllOriginalMultipliersValid = false;
			}
		}
		return areAllOriginalMultipliersValid;
	}
	
	/**
	 * This method assumes that it will receive a Valuation Sentence
	 * Assuming that, it will test if all Nouns/Multipliers are Valid
	 * 
	 * @param		pReadLineValuationSentence		a Valuation Sentence
	 * @return		boolean							Answer if all Nouns/Multipliers are valid
	 */
	public boolean areAllOriginalMultipliersValidFromValuationSentence(String pReadLineValuationSentence) {
		String variableName = getVariableNameFromValuationSentence(pReadLineValuationSentence);
		List<String> multiplierAndPredicateTerms = split(pReadLineValuationSentence, variableName);
		List<String> multipliers = split(multiplierAndPredicateTerms.get(0));
		
		boolean areAllOriginalMultipliersValid = 
			areAllOriginalMultipliersValid(multipliers);
		return areAllOriginalMultipliersValid;
	}
	
	/**
	 * This method will test if the		pReadLine		is a How Much kind of sentence
	 * 
	 * Example of Valid Sentences
	 * 		how much is pish tegj glob glob ?
	 * 
	 * @param		pReadLine	Sentence to be tested to be or not a How Much
	 * 
	 * @return		boolean		Indicates if the 	pReadLine	is a Valid How Much sentence
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
	
	/**
	 * This method assumes that its receiving a string with Nouns/Multipliers
	 * Assuming this, it will convert it to Roman Numbers
	 * 
	 * @param		pMultipliers	String containing only Nouns/Multipliers
	 * @return		String			A Roman Number
	 */
	public String convertMultipliersToRoman(String pMultipliers) {
		String response = null;
		
		if ( 
				isStringValid(pMultipliers)
		) {
			List<String> originalMultipliers= split( pMultipliers );
			
			StringBuffer sbMultipliers = new StringBuffer("");
			for (String multiplier : originalMultipliers) {
				Character romanMultiplier = this.aNounMultiplier_toRoman_Map.get(multiplier);
				sbMultipliers = sbMultipliers.append(Character.toString(romanMultiplier));
			}
			response = sbMultipliers.toString();
		}
		
		return response;
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
			List<String> sentenceTerms = split(pReadLine);
			
			if (	sentenceTerms != null && sentenceTerms.size() >= 3		) {
				String creditTerm	= sentenceTerms.get(sentenceTerms.size() - 1 );
				String numericTerm	= sentenceTerms.get(sentenceTerms.size() - 2 );
				String isVerbTerm	= sentenceTerms.get(sentenceTerms.size() - 3 );
				
				if (	sentenceTerms != null && sentenceTerms.size() >= 5		) {
					if (	
							(
								creditTerm.equals(CREDITS)	||
								creditTerm.equals(CREDIT)
							)									&&
							isNumeric(numericTerm)				&&
							isVerbTerm.equals(IS)		
					) {
						//String variableTerm	= getVariableName(sentenceTerms);
						isMappingSentence = true;
						for ( int index = sentenceTerms.size() - 5; index >= 0; index = index - 1 ) {
							String romanianTerm	= sentenceTerms.get( index );
							
							if ( !this.aNounMultiplier_toRoman_Map.containsKey(romanianTerm) ) {
								isMappingSentence = false;
								break;	// Does not have at least one of this terms, so the response should be False.
							}
						}
					}
				}
			}
		}
		
		return isMappingSentence;
	}
	
	/**
	 * This method add at	aUnitMap	a mapping from a Noun/Multiplier to a Roman Number
	 * 
	 * @param	pReadLine	A Noun/Multiplier to Roman attribution Sentence
	 */
	public void addNounMultiplier_Roman_Mapping(String pReadLine) {
		if ( isMappingSentence(pReadLine) ) {
			if ( this.aNounMultiplier_toRoman_Map == null ) {
				instanciate_NounMultiplier_toRoman_Mapping();
			}
			
			List<String> sentenceTerms = split(pReadLine);
			
			this.aNounMultiplier_toRoman_Map.put( 
				sentenceTerms.get(0),			//	Variable
				//sentenceTerms.get(1)				is
				sentenceTerms.get(2).charAt(0)	//	Roman Numeral
			);
		}
	}
	
	/**
	 * This method assumes that it will receive a Valuation Sentence
	 * Assuming this, it will extract the Numeric quantity of Credits assigned to it
	 * 
	 * Example of valid sentences
	 * 		glob glob Silver is 34 Credits
	 * 		glob prok Gold is 57800 Credits
	 * 		pish pish Iron is 3910 Credits
	 * 
	 * @param	pReadLineValuationSentence		a Valuation Sentence
	 */
	public int getAttributedValue(String pReadLineValuationSentence) {
		int value = Integer.MIN_VALUE;
		
		if ( isValuationSentence(pReadLineValuationSentence) ) {
			List<String> terms = split(pReadLineValuationSentence);
			
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
	
	/**
	 * This method assumes that it will receive a Valuation Sentence, and its going to extract from it 
	 * 
	 * @param		pReadLineValuationSentence		a Valuation Sentence
	 * @return		String							A String containing all the Multiplier Nouns
	 */
	public static String splitToGetOriginalMultiplierTermsFromValuationSentence(String pReadLineValuationSentence) {
		String variableTerm = getVariableNameFromValuationSentence(pReadLineValuationSentence);
		
		List<String> sentenceTerms = split(pReadLineValuationSentence, " " + variableTerm);
		String response = sentenceTerms.get(0);
		return response;
	}
	
	/**
	 * This method assumed that it will receive a Valuation Sentence, and will extract from it the Variable Name
	 * 
	 * @param		pReadLineValuationSentence		a Valuation Sentence
	 * @return		String							the Variable Name
	 */
	public static String getVariableNameFromValuationSentence(String pReadLineValuationSentence) {
		List<String> sentenceTerms = split(pReadLineValuationSentence);
		
		String variableTerm	= getVariableNameFromValuationSentenceTerms(sentenceTerms);
		return variableTerm;
	}
	
	/**
	 * This method assumes that it will receive a List with all Terms from a Valuation Sentence, and extract from them the Variable name
	 * 
	 * @param		pValuationSentenceTerms		a List with all Terms from a Valuation Sentence
	 * @return		String						The Variable Name
	 */
	private static String getVariableNameFromValuationSentenceTerms(List<String> pValuationSentenceTerms) {
		return pValuationSentenceTerms.get( pValuationSentenceTerms.size() - 4 );
	}
	
	/**
	 * This method gets a Scanner, that allows to receive data from the Standard Input by Console/Terminal
	 * 
	 * @return		Scanner
	 */
	public static Scanner getScanner() {
		InputStream is			= System.in;
		InputStreamReader isr	= new InputStreamReader(is);
		BufferedReader br		= new BufferedReader(isr);
		Scanner scanner			= new Scanner(br);
		
		return scanner;
	}
	
	/**
	 * This method will get a Scanner, to receive data from the Standard Input, and will keep reading anything coming from users
	 * If the user press an Empty Enter or type "Stop", the application is going to stop. 
	 */
	public void readlineFromScanner() {
		Scanner scanner = getScanner();
		
		while ( scanner.hasNextLine() ) {
			String line = scanner.nextLine();
			
			if (
					!isStringValid(line)	||
					line.equalsIgnoreCase("Stop") 
			) {
				break;
			}
			processInputLineRead(line);
		}
		
		print("Bye! See you soon!");
		scanner.close();
	}
	
	/**
	 * This method will process the Sentences typed by the user, and give the proper response to them
	 * 
	 * Test input:
	 * 		glob is I
	 * 		prok is V
	 * 		pish is X
	 * 		
	 * Extended Test input
	 * 		splash is C
	 * 		smash is D
	 * 		slash is M
	 * 
	 * Test input:
	 * 		glob glob Silver is 34 Credits
	 * 		glob prok Gold is 57800 Credits
	 * 		pish pish Iron is 3910 Credits
	 * 		
	 * Extended Test input
	 * 		slash splash slash tegj pish pish pish glob glob glob BitCoin is 1 Credit
	 * 		
	 * Test input:
	 * 		how much is pish tegj glob glob ?
	 * 		how many Credits is glob prok Silver ?
	 * 		how many Credits is glob prok Gold ?
	 * 		how many Credits is glob prok Iron ?
	 * 		how much wood could a woodchuck chuck if a woodchuck could chuck wood ?
	 * 		
	 * Extended Test input
	 * 		how many Credits is pish pish pish prok BitCoin ?
	 * 		how many Credits is pish pish pish prok Ethereum ?
	 * 		how many Credits is Plunct Plact Zum BitCoin 
	 * 
	 * Test Output:
	 * 		pish tegj glob glob is 42
	 * 		glob prok Silver is 68 Credits
	 * 		glob prok Gold is 57800 Credits
	 * 		glob prok Iron is 782 Credits
	 * 		I have no idea what you are talking about
	 * 		
	 * 		pish pish pish prok BitCoin is 0.01765 Credits
	 * 
	 * @param	pReadLine	A Sentence that can be a 
	 * 				Noun/Multiplier Mapping sentence
	 * 				Valuation sentence
	 * 				Question sentence
	 */
	public void processInputLineRead(String pReadLine) {
		/* Here I have to receive a Sentence, and do this:
		 * 		Define what kind of sentence it is:
		 * 			Unit to Value attribution
		 * 			Variable attribution
		 * 			Question sentence
		 * 		Call the right method do process this sentence in Converter
		 * 		If its a Question Sentence, to Print the Response sentence
		 */
		try {
			if (
					Converter.isMappingSentence(pReadLine)			||
					this.isValuationSentence(pReadLine)				||
					this.isHowMuchManySentenceValid(pReadLine)
			) {
				if (	Converter.isMappingSentence(pReadLine)		) {
					this.addNounMultiplier_Roman_Mapping(pReadLine);
				} else if ( this.isValuationSentence(pReadLine)	) {
					this.addValuation(pReadLine);
				} else if ( this.isHowMuchManySentenceValid(pReadLine) ) {
					String response = this.processHowSentence(pReadLine);
					print(response);
				}
			} else {
				print(I_HAVE_NO_IDEA_WHAT_YOU_ARE_TALKING_ABOUT);
			}
		} catch ( EmptyRomanException | FourTimesRepetitionException | InvalidArabicException | InvalidRomanException  exception ) {
			print(I_HAVE_NO_IDEA_WHAT_YOU_ARE_TALKING_ABOUT);
		}
	}
	
	/**
	 * This method only simplifies the Printing of answers
	 * @param pResponse		String to be printed
	 */
	private static void print(String pResponse) {
		System.out.println(pResponse);
	}
	
	/**
	 * The Main Method for this class and from the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		printInstructions();
		
		SentenceProcessor sir = new SentenceProcessor();
		
		sir.readlineFromScanner();
	}
	
	/**
	 * This method just print the basic instructions to the users, about how to use it.
	 */
	private static void printInstructions() {
		print("Welcome! These is a Sentence Processor of Roman numbers into Nouns and Variables, to define theys Credit values");
		print("There are three types of valid sentence.\n");
		print("The 1st type is the sentences that does an asignment of a Roman Number to a Noun. \nEx: Alice is I. \n");
		print("The 2nd type is the sentences that uses the Nouns from previous sentences, introduces a new Variable, an do another asignment of a Numerical quantity of Credits. \nEx: Alice Bob is 5 Credits.\n");
		print("The 3rd type is the sentences that does a Question, based on the Nouns and Variables from previous sentences. \nEx: How Many Credits is Alice Alce Bob ?\n");
		print("You can start writing sentences below.");
		print("======================================");
	}
	
}