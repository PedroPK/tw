package com.tw.main;

import static org.junit.Assert.*;
import static com.tw.utils.Constants.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

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
 * Extended Input:
 * 		how many Silver is glob Gold ?
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
public class SentenceProcessorTest {
	
	private SentenceProcessor aSentenceProcessor;
	
	@Test
	public void testHowManySilverIsGlobGold() {
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("glob is I");
		this.aSentenceProcessor.addValuation("glob Silver is 5 Credits");
		this.aSentenceProcessor.addValuation("glob Gold is 10 Credits");
		
		String answer = this.aSentenceProcessor.processHowSentence("how many Silver is glob Gold ?");
		
		assertEquals("glob Gold is 2 Silver", answer);
	}
	
	/**
	 * Original Test input:
	 * 		glob is I
	 * 		prok is V
	 * 		pish is X
	 * 		tegj is L
	 * 		
	 * 		glob glob Silver is 34 Credits
	 * 		glob prok Gold is 57800 Credits
	 * 		pish pish Iron is 3910 Credits
	 * 
	 * Extended Test input
	 * 		splash is C
	 * 		smash is D
	 * 		slash is M
	 * 		
	 * 		slash splash slash tegj pish pish	pish	glob glob glob BitCoin is 1 Credit
	 * 		M		C		M	L	X	X		X		I		I	I
	 * 
	 */
	@Before
	public void prepareSentenceProcessor() {
		this.aSentenceProcessor = new SentenceProcessor();
		
		// Original Test cases
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("glob is I");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("prok is V");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("pish is X");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("tegj is L");
		
		// Extended Test cases
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("splash is C");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("smash is D");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("slash is M");
		
		// Original Test cases
		this.aSentenceProcessor.addValuation("glob glob Silver is 34 Credits");
		this.aSentenceProcessor.addValuation("glob prok Gold is 57800 Credits");
		this.aSentenceProcessor.addValuation("pish pish Iron is 3910 Credits");
		
		// Extended Test cases
		this.aSentenceProcessor.addValuation("slash splash slash tegj pish pish pish glob glob glob BitCoin is 1 Credit");
	}
	
	/**
	 * glob is I
	 */
	@Test
	public void testMappingGlobToI() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addNounMultiplier_Roman_Mapping("glob is I");
		char response = sentenceProcessor.getNounMultiplier_RomanNumber("glob");
		
		assertEquals('I', response);
	}
	
	/**
	 * prok is V
	 */
	@Test
	public void testMappingProkToV() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addNounMultiplier_Roman_Mapping("prok is V");
		char response = sentenceProcessor.getNounMultiplier_RomanNumber("prok");
		
		assertEquals('V', response);
	}
	
	/**
	 * pish is X
	 */
	@Test
	public void testMappingPishToX() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addNounMultiplier_Roman_Mapping("pish is X");
		char response = sentenceProcessor.getNounMultiplier_RomanNumber("pish");
		
		assertEquals('X', response);
	}
	
	/**
	 * tegj is L
	 */
	@Test
	public void testMappingTegjToL() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addNounMultiplier_Roman_Mapping("tegj is L");
		char response = sentenceProcessor.getNounMultiplier_RomanNumber("tegj");
		
		assertEquals('L', response);
	}
	
	/**
	 * glob is I
	 * prok is V
	 * pish is X
	 * tegj is L
	 */
	@Test
	public void testFullMapping() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addNounMultiplier_Roman_Mapping("glob is I");
		sentenceProcessor.addNounMultiplier_Roman_Mapping("prok is V");
		sentenceProcessor.addNounMultiplier_Roman_Mapping("pish is X");
		sentenceProcessor.addNounMultiplier_Roman_Mapping("tegj is L");
		
		assertEquals('I', sentenceProcessor.getNounMultiplier_RomanNumber("glob"));
		assertEquals('V', sentenceProcessor.getNounMultiplier_RomanNumber("prok"));
		assertEquals('X', sentenceProcessor.getNounMultiplier_RomanNumber("pish"));
		assertEquals('L', sentenceProcessor.getNounMultiplier_RomanNumber("tegj"));
	}
	
	/**
	 * glob is I
	 * glob glob Silver is 34 Credits
	 */
	@Test
	public void testIsValuationSentenceGlobGlobSilverIs34Credits() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addNounMultiplier_Roman_Mapping("glob is I");
		
		assertTrue(sentenceProcessor.isValuationSentence("glob glob Silver is 34 Credits"));
	}
	
	/**
	 * glob is I
	 * prok is V
	 * pish is X
	 * tegj is L
	 * 
	 * glob glob Silver is 34 Credits
	 * glob prok Gold is 57800 Credits
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testIsValuationSentence_GlobGlobSilverIs34CreditsWithFullMapping() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addNounMultiplier_Roman_Mapping("glob is I");
		sentenceProcessor.addNounMultiplier_Roman_Mapping("prok is V");
		sentenceProcessor.addNounMultiplier_Roman_Mapping("pish is X");
		sentenceProcessor.addNounMultiplier_Roman_Mapping("tegj is L");
		
		assertTrue(sentenceProcessor.isValuationSentence("glob glob Silver is 34 Credits"));
	}
	
	/**
	 * glob is I
	 * prok is V
	 * pish is X
	 * tegj is L
	 * 
	 * glob prok Gold is 57800 Credits
	 */
	@Test
	public void testIsValuationSentence_globProkGoldIs57800CreditsWithFullMapping() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addNounMultiplier_Roman_Mapping("glob is I");
		sentenceProcessor.addNounMultiplier_Roman_Mapping("prok is V");
		sentenceProcessor.addNounMultiplier_Roman_Mapping("pish is X");
		sentenceProcessor.addNounMultiplier_Roman_Mapping("tegj is L");
		
		assertTrue(sentenceProcessor.isValuationSentence("glob prok Gold is 57800 Credits"));
	}
	
	/**
	 * glob is I
	 * 
	 * glob prok Gold is 57800 Credits
	 */
	@Test
	public void testIsValuationSentence_globProkGoldIs57800CreditsWithPartialMapping() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addNounMultiplier_Roman_Mapping("glob is I");
		
		assertFalse(sentenceProcessor.isValuationSentence("glob prok Gold is 57800 Credits"));
	}
	
	/**
	 * pish is X
	 * 
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testIsValuationSentence_pishPishIronIs3910CreditsWithFullMapping() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addNounMultiplier_Roman_Mapping("pish is X");
		
		assertTrue(sentenceProcessor.isValuationSentence("pish pish Iron is 3910 Credits"));
	}
	
	/**
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testIsValuationSentence_pishPishIronIs3910CreditsWithNoMapping() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		
		assertFalse(sentenceProcessor.isValuationSentence("pish pish Iron is 3910 Credits"));
	}
	
	/**
	 * glob glob Silver is 34 Credits
	 */
	@Test
	public void testGetOriginalMultiplierTermsGlobGlobSilverIs34Credits() {
		String originalMultiplier = SentenceProcessor.splitToGetOriginalMultiplierTermsFromValuationSentence("glob glob Silver is 34 Credits");
		
		assertEquals("glob glob", originalMultiplier);
	}
	
	/**
	 * glob prok Gold is 57800 Credits
	 */
	@Test
	public void testGetOriginalMultiplierTermsGlobProkGoldIs57800Credits() {
		String originalMultiplier = SentenceProcessor.splitToGetOriginalMultiplierTermsFromValuationSentence("glob prok Gold is 57800 Credits");
		
		assertEquals("glob prok", originalMultiplier);
	}
	
	/**
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testGetOriginalMultiplierTerms_PishPishIronIs3910Credits() {
		String originalMultiplier = SentenceProcessor.splitToGetOriginalMultiplierTermsFromValuationSentence("pish pish Iron is 3910 Credits");
		
		assertEquals("pish pish", originalMultiplier);
	}
	
	/**
	 * glob glob Silver is 34 Credits
	 */
	@Test
	public void testGetAttributedValue_globGlobSilverIs34Credits() {
		int attributedValue = this.aSentenceProcessor.getAttributedValue("glob glob Silver is 34 Credits");
		
		assertEquals(34, attributedValue);
	}
	
	/**
	 * glob prok Gold is 57800 Credits
	 */
	@Test
	public void testGetAttributedValue_globProkGoldIs57800Credits() {
		int attributedValue = this.aSentenceProcessor.getAttributedValue("glob prok Gold is 57800 Credits");
		
		assertEquals(57800, attributedValue);
	}
	
	/**
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testGetAttributedValue_pishPishIronIs3910Credits() {
		int attributedValue = this.aSentenceProcessor.getAttributedValue("pish pish Iron is 3910 Credits");
		
		assertEquals(3910, attributedValue);
	}
	
	/**
	 * glob is I
	 * 
	 * glob glob Silver is 34 Credits
	 */
	@Test
	public void testEvaluateAttributionSentence_globGlobSilverIs34Credits() {
		this.aSentenceProcessor.addValuation("glob glob Silver is 34 Credits");
		
		double value = this.aSentenceProcessor.getVariableValue("Silver");
		
		assertEquals(17, value, 0.01);
	}
	
	/**
	 * glob is I
	 * prok is V
	 * 
	 * glob prok Gold is 57800 Credits
	 */
	@Test
	public void testEvaluateAttributionSentence_globProkGoldIs57800Credits() {
		this.aSentenceProcessor.addValuation("glob prok Gold is 57800 Credits");
		double value = this.aSentenceProcessor.getVariableValue("Gold");
		
		assertEquals(14450, value, 0.01);
	}
	
	/**
	 * pish is X
	 * 
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testEvaluateAttributionSentence_pishPishIronIs3910Credits() {
		this.aSentenceProcessor.addValuation("pish pish Iron is 3910 Credits");
		double value = this.aSentenceProcessor.getVariableValue("Iron");
		
		assertEquals(195.5, value, 0.01);
	}
	
	/**
	 * Test this kind of Sentences, with the respective Answers
	 * 		how much is pish tegj glob glob ?
	 * 		
	 * Test Output:
	 * 		pish tegj glob glob is 42
	 */
	@Test
	public void testProcessHowSentence_howMuchIsPishTegjGlobGlobQuestionMark() {
		String answer = this.aSentenceProcessor.processHowSentence("how much is pish tegj glob glob ?");
		
		assertEquals("pish tegj glob glob is 42", answer);
	}
	
	/**
	 * Test this kind of Sentences, with the respective Answers
	 * 		how many Credits is glob prok Silver ?
	 * 		
	 * Test Output:
	 * 		glob prok Silver is 68 Credits
	 */
	@Test
	public void testProcessHowSentence_howManyCreditsIsGlobProkSilverQuestionMark() {
		String answer = this.aSentenceProcessor.processHowSentence("how many Credits is glob prok Silver ?");
		
		assertEquals("glob prok Silver is 68 Credits", answer);
	}
	
	/**
	 * Test this kind of Sentences, with the respective Answers
	 * 		how many Credits is glob prok Gold ?
	 * 		
	 * Test Output:
	 * 		glob prok Gold is 57800 Credits
	 */
	@Test
	public void testProcessHowSentence_howManyCreditsIsGlobProkGoldQuestionMark() {
		String answer = this.aSentenceProcessor.processHowSentence("how many Credits is glob prok Gold ?");
		
		assertEquals("glob prok Gold is 57800 Credits", answer);
	}
	
	/**
	 * Test this kind of Sentences, with the respective Answers
	 * 		how many Credits is glob prok Iron ?
	 * 		how much wood could a woodchuck chuck if a woodchuck could chuck wood ?
	 * 		
	 * Test Output:
	 * 		glob prok Iron is 782 Credits
	 * 		I have no idea what you are talking about
	 */
	@Test
	public void testProcessHowSentence_howManyCreditsIsGlobProkIronQuestionMark() {
		String answer = this.aSentenceProcessor.processHowSentence("how many Credits is glob prok Iron ?");
		
		assertEquals("glob prok Iron is 782 Credits", answer);
	}
	
	/**
	 * Original Test input:
	 * 		glob is I
	 * 		prok is V
	 * 		pish is X
	 * 		tegj is L
	 * 		
	 * Extended Test input
	 * 		splash is C
	 * 		smash is D
	 * 		slash is M
	 * 		
	 * 		slash	splash	slash	tegj	pish	pish	pish	glob	glob	glob	BitCoin is 1 Credit
	 * 		M		C		M		L		X		X		X		I		I		I
	 * 
	 * Test this kind of Sentences, with the respective Answers
	 * 		how many Credits is Plunct Plact Zum BitCoin ?
	 * 		
	 * Test Output:
	 * 		I have no idea what you are talking about
	 */
	@Test
	public void testAddValuation_withTegj_withoutPreviousMapping() {
		this.aSentenceProcessor = new SentenceProcessor();
		
		// Original Test cases
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("glob is I");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("prok is V");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("pish is X");
		//aSentenceProcessor.addMapping("tegj is L");
		
		// Extended Test cases
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("splash is C");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("smash is D");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("slash is M");
		
		// Originak Test cases
		this.aSentenceProcessor.addValuation("glob glob Silver is 34 Credits");
		this.aSentenceProcessor.addValuation("glob prok Gold is 57800 Credits");
		this.aSentenceProcessor.addValuation("pish pish Iron is 3910 Credits");
				
		// Extended Test cases
		this.aSentenceProcessor.addValuation("slash splash slash tegj pish pish pish glob glob glob BitCoin is 1 Credit");
	}
	
	/**
	 * Test this kind of Sentences, with the respective Answers
	 * 		how much wood could a woodchuck chuck if a woodchuck could chuck wood ?
	 * 		
	 * Test Output:
	 * 		I have no idea what you are talking about
	 */
	@Test
	public void testProcessHowSentence_howMuchWoodCouldAWoodchuckChuckIfAWoodchuckCouldChuckWoodQuestionMark() {
		String answer = this.aSentenceProcessor.processHowSentence("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
		
		assertEquals("I have no idea what you are talking about", answer);
	}
	
	@Test
	public void testGetScanner() {
		Scanner scanner = SentenceProcessor.getScanner();
		
		assertNotNull(scanner);
	}
	
	
	
	/**
	 * glob prok Gold is 57800 Credits
	 */
	@Test
	public void testAreAllOriginalMultipliersValid_GlobProkGoldIs57800Credits() {
		boolean response = this.aSentenceProcessor.areAllOriginalMultipliersValidFromValuationSentence("glob prok Gold is 57800 Credits");
		
		assertTrue(response);
	}
	
	/**
	 * glob glob Silver is 34 Credits
	 */
	@Test
	public void testAreAllOriginalMultipliersValid_GlobGlobSilverIs34Credits() {
		boolean response = this.aSentenceProcessor.areAllOriginalMultipliersValidFromValuationSentence("glob glob Silver is 34 Credits");
		
		assertTrue(response);
	}
	
	/**
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testAreAllOriginalMultipliersValid_PishPishIronIs3910Credits() {
		boolean response = this.aSentenceProcessor.areAllOriginalMultipliersValidFromValuationSentence("pish pish Iron is 3910 Credits");
		
		assertTrue(response);
	}
	
	/**
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testAreAllOriginalMultipliersValid_PishTishIronIs3910Credits() {
		boolean response = this.aSentenceProcessor.areAllOriginalMultipliersValidFromValuationSentence("pish tish Iron is 3910 Credits");
		
		assertFalse(response);
	}
	
	/**
	 * glob is I
	 * 
	 * glob glob Silver is 34 Credits
	 */
	@Test
	public void testConvertOriginalMultipliersToRoman_GlobGlobSilverIs34Credits() {
		String romanMultiplier = this.aSentenceProcessor.extratMultipliersAndConvertToRoman("glob glob Silver is 34 Credits");
		
		assertEquals("II", romanMultiplier);
	}
	
	/**
	 * glob is I
	 * prok is V
	 * 
	 * glob prok Gold is 57800 Credits
	 */
	@Test
	public void testConvertOriginalMultipliersToRoman_GlobProkGoldIs57800Credits() {
		String romanMultiplier = this.aSentenceProcessor.extratMultipliersAndConvertToRoman("glob prok Gold is 57800 Credits");
		
		assertEquals("IV", romanMultiplier);
	}
	
	/**
	 * pish is X
	 * 
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testConvertiOriginalMultipliersToRoman_PishPishIronIs3910Credits() {
		String romanMultiplier = this.aSentenceProcessor.extratMultipliersAndConvertToRoman("pish pish Iron is 3910 Credits");
		
		assertEquals("XX", romanMultiplier);
	}
	
	/** 
	 * how much is pish tegj glob glob ?
	 */
	@Test
	public void testIsHowMuchManySentenceValid_HowMuchIsPishTegjGlobGlobQuestionMark() {
		boolean response = this.aSentenceProcessor.isHowMuchManySentenceValid("how much is pish tegj glob glob ?");
		
		assertTrue(response);
	}
	
	/** 
	 * how much is pish tegj glob glob ?
	 */
	@Test
	public void testIsHowMuchManySentenceValid_HowMuchIsPishTegjGlobGlobExclamationMark() {
		boolean response = this.aSentenceProcessor.isHowMuchManySentenceValid("how much is pish tegj glob glob !");
		
		assertFalse(response);
	}
	
	/** 
	 * how much is pish tegj glob glob ?
	 */
	@Test
	public void testIsHowMuchManySentenceValid_HowMuchIsPishTegjGlobGlob() {
		boolean response = this.aSentenceProcessor.isHowMuchManySentenceValid("how much is pish tegj glob glob");
		
		assertFalse(response);
	}
	
	/** 
	 * how much is pish tegj glob glob ?
	 */
	@Test
	public void testIsHowMuchManySentenceValid_HowMuchCreditsPishTegjGlobGlobQuestionMark() {
		boolean response = this.aSentenceProcessor.isHowMuchManySentenceValid("how much Credits pish tegj glob glob ?");
		
		assertFalse(response);
	}
	
	/** 
	 * how much is pish tegj glob glob ?
	 * 
	 * This How Many sentence does not has an Variable in it
	 */
	@Test
	public void testIsHowMuchManySentenceValid_HowManyCreditsIsPishTegjGlobGlobQuestionMark() {
		boolean response = this.aSentenceProcessor.isHowMuchManySentenceValid("how many Credits is pish tegj glob glob ?");
		
		assertFalse(response);
	}
	
	/**
	 * Test input:
	 * 		glob is I
	 * 		
	 * 		glob glob Silver is 34 Credits
	 */
	@Test
	public void testGetVariableFromAttributionSentence_GlobGlobSilverIs34Credits() {
		String variable = SentenceProcessor.getVariableNameFromValuationSentence("glob glob Silver is 34 Credits");
		
		assertEquals("Silver", variable);
	}
	
	/**
	 * Test input:
	 * 		glob is I
	 * 		prok is V
	 * 		
	 * 		glob prok Gold is 57800 Credits
	 */
	@Test
	public void testGetVariableFromAttributionSentence_GlobProkGoldIs57800Credits() {
		String variable = SentenceProcessor.getVariableNameFromValuationSentence("glob prok Gold is 57800 Credits");
		
		assertEquals("Gold", variable);
	}
	
	/**
	 * Test input:
	 * 		pish is X
	 * 		
	 * 		pish pish Iron is 3910 Credits
	 */
	@Test
	public void testGetVariableFromAttributionSentence_PishPishIronIs3910Credits() {
		String variable = SentenceProcessor.getVariableNameFromValuationSentence("pish pish Iron is 3910 Credits");
		
		assertEquals("Iron", variable);
	}
	
	
	/**
	 * Original Test input:
	 * 		glob is I
	 * 		prok is V
	 * 		pish is X
	 * 		tegj is L
	 * 		
	 * 		glob glob Silver is 34 Credits
	 * 		glob prok Gold is 57800 Credits
	 * 		pish pish Iron is 3910 Credits
	 * 		
	 * Extended Test input
	 * 		splash is C
	 * 		smash is D
	 * 		slash is M
	 * 		
	 * 		slash	splash	slash	tegj	pish	pish	pish	glob	glob	glob BitCoin is 1 Credit
	 * 		M		C		M		L		X		X		X		I		I		I
	 */
	@Test
	public void testAddValuationSlashSplashSlashTegjPishPishPishGlobGlobGlobBitCoinIs1Credit() {
		this.aSentenceProcessor = new SentenceProcessor();
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("glob is I");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("prok is V");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("pish is X");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("tegj is L");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("splash is C");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("smash is D");
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("slash is M");
		
		this.aSentenceProcessor.addValuation("slash splash slash tegj pish pish pish glob glob glob BitCoin is 1 Credit");
	}
	
	/**
	 * Test the Iron Valuation
	 * Since pish is X, hence pish is 10, pish pish = 20
	 * So, 20 Iron = 3910;
	 * Then, 3910 / 20 = 195.5;
	 * 
	 * So, It should be 195.5
	 */
	@Test
	public void testIronValuation() {
		this.aSentenceProcessor.addNounMultiplier_Roman_Mapping("pish is X");
		this.aSentenceProcessor.addValuation("pish pish Iron is 3910 Credits");
		double ironValue = this.aSentenceProcessor.getVariableValue("Iron");
		
		assertEquals(195.5, ironValue, 0.01);
	}
	
	@Test
	public void testProcessInputLineRead_pishIsXX_Invalid() {
		String response = this.aSentenceProcessor.processInputLineRead("pish is XX");
		
		assertEquals(I_HAVE_NO_IDEA_WHAT_YOU_ARE_TALKING_ABOUT, response);
	}
	
	@Test
	public void testProcessInputLineRead_Scenario01_Valid() {
		String response = this.aSentenceProcessor.processInputLineRead("glob is I");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("prok is V");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("pish is X");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("tegj is L");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("glob glob Silver is 34 Credits");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("glob prok Gold is 57800 Credits");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("pish pish Iron is 3910 Credits");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("how much is pish tegj glob glob ?");
		assertEquals("pish tegj glob glob is 42", response);
		
		response = this.aSentenceProcessor.processInputLineRead("how many Credits is glob prok Silver ?");
		assertEquals("glob prok Silver is 68 Credits", response);
		
		response = this.aSentenceProcessor.processInputLineRead("how many Credits is glob prok Gold ?");
		assertEquals("glob prok Gold is 57800 Credits", response);
		
		response = this.aSentenceProcessor.processInputLineRead("how many Credits is glob prok Iron ?");
		assertEquals("glob prok Iron is 782 Credits", response);
		
		response = this.aSentenceProcessor.processInputLineRead("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
		assertEquals(I_HAVE_NO_IDEA_WHAT_YOU_ARE_TALKING_ABOUT, response);
	}
	
	@Test
	public void testProcessInputLineRead_Scenario02_Valid() {
		String response = this.aSentenceProcessor.processInputLineRead("glob is I");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("prok is V");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("pish is X");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("tegj is L");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("splash is C");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("smash is D");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("slash is M");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("glob glob Silver is 34 Credits");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("glob prok Gold is 57800 Credits");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("pish pish Iron is 3910 Credits");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("slash splash slash tegj pish pish pish glob glob glob BitCoin is 1 Credit");
		assertEquals("", response);
		
		response = this.aSentenceProcessor.processInputLineRead("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
		assertEquals(I_HAVE_NO_IDEA_WHAT_YOU_ARE_TALKING_ABOUT, response);
	}
	
}