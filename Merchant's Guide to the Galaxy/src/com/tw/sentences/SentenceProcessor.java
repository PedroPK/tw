package com.tw.sentences;

import static com.tw.utils.Constants.*;
import static com.tw.utils.Utils.*;
import static com.tw.math.Converter.*;

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
 * This documentation will explain the decisions that I took during this problem solving and its implementation
 * 
 * Fist thing that I thought was that I should be original and find the answers how to solve it by my self. It was very likely that I could look for
 * in the internet for a similar problem and its solution, tested by many people, with more elegant and simple implementations then I would do, 
 * but in my mind, you guys would want something made by me, so I did this way, organically, knowing that probably there are better ways, more elegant ways, to do the same job.
 * 
 * Having said that, the second thing that I was worried about was how to do conversions between Roman numbers to Arabic Numbers.
 * 
 * The best way that I know how to do this is using TDD (Test Driven Development). So, I created empty methods, jumped to implement the first test, 
 * executed it, saw it fail, and jump back to implement a minimal code to make the first test pass, and started creating more elaborated tests, 
 * keeping this cycle alive, until the moment that I feel that is enough robust to start implementing another method.
 * 
 * The third priority was how to map the Nouns (ex: glob, prok, pish, tegj, etc...) with its Roman numbers.
 * A subpriority was decide if its a valid mapping or not
 * 
 * The fourth priority was to process sentences that was a Variable on it, mixed with Nouns and attributing then to Arabic quantity of Credits (ex: glob glob Silver is 34 Credits)
 * 
 * Once this was done and with many Tests for many sceneries, the fifth priority was to process the How Much/Many questions, and answer then appropriately
 * Tests with JUnit also implemented to test this Question/Answers sentences, that was testing all previous implementations by chain reaction
 * 
 * Finally, the last thing that was how to use the Standard Input, capture the keyboard text and integrate it with all the rest of application
 * 
 * A last effort to be done is to do a great refactoring, doing a better organization, removing unecessary code, making it a little bit better.
 * 
 * An additional explanation is that, once I decided to use Tests in almost anything implemented, there are many public methods, not based in  external utility,
 * but its the way to do Tests with JUnit, without use a more complex frameworks or complicate then with Reflection
 * 
 * @author pedro.c.f.santos
 */

/**
 * You decided to give up on earth after the latest financial collapse left 99.99% of the earth's population with 0.01% of the wealth. 
 * Luckily, with the scant sum of money that is left in your account, you are able to afford to rent a spaceship, leave earth, and fly all over the galaxy to sell common metals and dirt (which apparently is worth a lot).
 * Buying and selling over the galaxy requires you to convert numbers and units, and you decided to write a program to help you.
 * The numbers used for intergalactic transactions follows similar convention to the roman numerals and you have painstakingly collected the appropriate translation between them.
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
 * For example MCMXLIV = 1000 + (1000 − 100) + (50 − 10) + (5 − 1) = 1944.
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
 * (Source: Wikipedia http://en.wikipedia.org/wiki/Roman_numerals)
 * 
 * Input to your program consists of lines of text detailing your notes on the conversion between intergalactic units and roman numerals.
 * You are expected to handle invalid queries appropriately.
 * 
 * Test input:
 * 		glob is I
 * 		prok is V
 * 		pish is X
 * 		tegj is L
 * 		
 * 		glob glob Silver is 34 Credits
 * 		glob prok Gold is 57800 Credits
 * 		pish pish Iron is 3910 Credits
 * 		
 * 		how much is pish tegj glob glob ?
 * 		how many Credits is glob prok Silver ?
 * 		how many Credits is glob prok Gold ?
 * 		how many Credits is glob prok Iron ?
 * 		how much wood could a woodchuck chuck if a woodchuck could chuck wood ?
 * 
 * Test Output:
 * 		pish tegj glob glob is 42
 * 		glob prok Silver is 68 Credits
 * 		glob prok Gold is 57800 Credits
 * 		glob prok Iron is 782 Credits
 * 		I have no idea what you are talking about
 * 
 * @author pedro.c.f.santos
 */
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
						
						if ( !this.aUnitMap.containsKey(romanianTerm) ) {
							isMappingSentence = false;
							break;	// Does not have at least one of this terms, so the response should be False.
						}
					}
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
	
	public static Scanner getScanner() {
		InputStream is = System.in;
		InputStreamReader isr = new InputStreamReader(is);
		Scanner scanner = new Scanner(isr);
		
		return scanner;
	}
	
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
		
		System.out.println("Bye! See you soon!");
		scanner.close();
	}
	
	/**
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
	 * 		glob glob Silver is 34 Credits
	 * 		glob prok Gold is 57800 Credits
	 * 		pish pish Iron is 3910 Credits
	 * 		
	 * 		slash splash slash tegj pish pish pish glob glob glob BitCoin is 1 Credit
	 * 		
	 * 		how much is pish tegj glob glob ?
	 * 		how many Credits is glob prok Silver ?
	 * 		how many Credits is glob prok Gold ?
	 * 		how many Credits is glob prok Iron ?
	 * 		how much wood could a woodchuck chuck if a woodchuck could chuck wood ?
	 * 		
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
	 * @param pReadLine
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
					Converter.isMappingSentence(pReadLine)				||
					this.isValuationSentence(pReadLine)			||
					this.isHowMuchManySentenceValid(pReadLine)
			) {
				if (	Converter.isMappingSentence(pReadLine)		) {
					this.addMapping(pReadLine);
				} else if ( this.isValuationSentence(pReadLine)	) {
					this.addValuation(pReadLine);
				} else if ( this.isHowMuchManySentenceValid(pReadLine) ) {
					String response = this.processHowSentence(pReadLine);
					System.out.println(response);
				}
			} else {
				System.out.println(I_HAVE_NO_IDEA_WHAT_YOU_ARE_TALKING_ABOUT);
			}
		} catch ( EmptyRomanException | FourTimesRepetitionException | InvalidArabicException | InvalidRomanException  exception ) {
			System.out.println(I_HAVE_NO_IDEA_WHAT_YOU_ARE_TALKING_ABOUT);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome! These is a Sentence Processor of Roman numbers into Nouns and Variables, to define theys Credit values");
		System.out.println("There are three types of valid sentence.\n");
		System.out.println("The 1st type is the sentences that does an asignment of a Roman Number to a Noun. \nEx: Alice is I. \n");
		System.out.println("The 2nd type is the sentences that uses the Nouns from previous sentences, introduces a new Variable, an do another asignment of a Numerical quantity of Credits. \nEx: Alice Bob is 5 Credits.\n");
		System.out.println("The 3rd type is the sentences that does a Question, based on the Nouns and Variables from previous sentences. \nEx: How Many Credits is Alice Alce Bob ?\n");
		System.out.println("You can start writing sentences below.");
		
		SentenceProcessor sir = new SentenceProcessor();
		
		sir.readlineFromScanner();
	}
	
}