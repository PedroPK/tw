package com.tw.sentences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class SentenceProcessorTest {
	
	private SentenceProcessor aSentenceProcessor;
	
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
		this.aSentenceProcessor.addMapping("glob is I");
		this.aSentenceProcessor.addMapping("prok is V");
		this.aSentenceProcessor.addMapping("pish is X");
		this.aSentenceProcessor.addMapping("tegj is L");
		
		// Extended Test cases
		this.aSentenceProcessor.addMapping("splash is C");
		this.aSentenceProcessor.addMapping("smash is D");
		this.aSentenceProcessor.addMapping("slash is M");
		
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
		sentenceProcessor.addMapping("glob is I");
		char response = sentenceProcessor.getMapping("glob");
		
		assertEquals('I', response);
	}
	
	/**
	 * prok is V
	 */
	@Test
	public void testMappingProkToV() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addMapping("prok is V");
		char response = sentenceProcessor.getMapping("prok");
		
		assertEquals('V', response);
	}
	
	/**
	 * pish is X
	 */
	@Test
	public void testMappingPishToX() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addMapping("pish is X");
		char response = sentenceProcessor.getMapping("pish");
		
		assertEquals('X', response);
	}
	
	/**
	 * tegj is L
	 */
	@Test
	public void testMappingTegjToL() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addMapping("tegj is L");
		char response = sentenceProcessor.getMapping("tegj");
		
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
		sentenceProcessor.addMapping("glob is I");
		sentenceProcessor.addMapping("prok is V");
		sentenceProcessor.addMapping("pish is X");
		sentenceProcessor.addMapping("tegj is L");
		
		assertEquals('I', sentenceProcessor.getMapping("glob"));
		assertEquals('V', sentenceProcessor.getMapping("prok"));
		assertEquals('X', sentenceProcessor.getMapping("pish"));
		assertEquals('L', sentenceProcessor.getMapping("tegj"));
	}
	
	/**
	 * glob is I
	 * glob glob Silver is 34 Credits
	 */
	@Test
	public void testIsValuationSentenceGlobGlobSilverIs34Credits() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addMapping("glob is I");
		
		assertTrue(sentenceProcessor.isValuationSentence("glob glob Silver is 34 Credits"));
	}
	
	/**
	 * glob is I
	 * prok is V
	 * pish is X
	 * tegj is L
	 * 
	 * glob glob Silver is 34 Credits
	 */
	@Test
	public void testIsValuationSentenceGlobGlobSilverIs34CreditsWithFullMapping() {
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		sentenceProcessor.addMapping("glob is I");
		sentenceProcessor.addMapping("prok is V");
		sentenceProcessor.addMapping("pish is X");
		sentenceProcessor.addMapping("tegj is L");
		
		assertTrue(sentenceProcessor.isValuationSentence("glob glob Silver is 34 Credits"));
	}
	
	/**
	 * glob glob Silver is 34 Credits
	 */
	@Test
	public void testGetOriginalMultiplierTermsGlobGlobSilverIs34Credits() {
		String originalMultiplier = SentenceProcessor.splitToGetOriginalMultiplierTerms("glob glob Silver is 34 Credits");
		
		assertEquals("glob glob", originalMultiplier);
	}
	
	/**
	 * glob prok Gold is 57800 Credits
	 */
	@Test
	public void testGetOriginalMultiplierTermsGlobProkGoldIs57800Credits() {
		String originalMultiplier = SentenceProcessor.splitToGetOriginalMultiplierTerms("glob prok Gold is 57800 Credits");
		
		assertEquals("glob prok", originalMultiplier);
	}
	
	/**
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testGetOriginalMultiplierTerms_PishPishIronIs3910Credits() {
		String originalMultiplier = SentenceProcessor.splitToGetOriginalMultiplierTerms("pish pish Iron is 3910 Credits");
		
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
		this.aSentenceProcessor.addMapping("glob is I");
		this.aSentenceProcessor.addMapping("prok is V");
		this.aSentenceProcessor.addMapping("pish is X");
		//aSentenceProcessor.addMapping("tegj is L");
		
		// Extended Test cases
		this.aSentenceProcessor.addMapping("splash is C");
		this.aSentenceProcessor.addMapping("smash is D");
		this.aSentenceProcessor.addMapping("slash is M");
		
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
		boolean response = this.aSentenceProcessor.areAllOriginalMultipliersValid("glob prok Gold is 57800 Credits");
		
		assertTrue(response);
	}
	
	/**
	 * glob glob Silver is 34 Credits
	 */
	@Test
	public void testAreAllOriginalMultipliersValid_GlobGlobSilverIs34Credits() {
		boolean response = this.aSentenceProcessor.areAllOriginalMultipliersValid("glob glob Silver is 34 Credits");
		
		assertTrue(response);
	}
	
	/**
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testAreAllOriginalMultipliersValid_PishPishIronIs3910Credits() {
		boolean response = this.aSentenceProcessor.areAllOriginalMultipliersValid("pish pish Iron is 3910 Credits");
		
		assertTrue(response);
	}
	
	/**
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testAreAllOriginalMultipliersValid_PishTishIronIs3910Credits() {
		boolean response = this.aSentenceProcessor.areAllOriginalMultipliersValid("pish tish Iron is 3910 Credits");
		
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
		String variable = SentenceProcessor.getVariableName("glob glob Silver is 34 Credits");
		
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
		String variable = SentenceProcessor.getVariableName("glob prok Gold is 57800 Credits");
		
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
		String variable = SentenceProcessor.getVariableName("pish pish Iron is 3910 Credits");
		
		assertEquals("Iron", variable);
	}
	
	/**
	 * glob glob Silver is 34 Credits
	 */
	@Test
	public void testGetOriginalMultiplierGlobGlogSilverIs34Credits() {
		String response = 
				this.aSentenceProcessor.getSentenceOriginalMultiplier("glob glob Silver is 34 Credits");
		
		assertEquals("glob glob", response);
	}
	
	/**
	 * glob prok Gold is 57800 Credits
	 */
	@Test
	public void testGetOriginalMultiplierGlobProkGoldIs57800Credits() {
		String response = 
				this.aSentenceProcessor.getSentenceOriginalMultiplier("glob prok Gold is 57800 Credits");
		
		assertEquals("glob prok", response);
	}
	
	/**
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testGetOriginalMultiplierPishPishIronIs3910Credits() {
		String response = 
			this.aSentenceProcessor.getSentenceOriginalMultiplier("pish pish Iron is 3910 Credits");
		
		assertEquals("pish pish", response);
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
		this.aSentenceProcessor.addMapping("glob is I");
		this.aSentenceProcessor.addMapping("prok is V");
		this.aSentenceProcessor.addMapping("pish is X");
		this.aSentenceProcessor.addMapping("tegj is L");
		this.aSentenceProcessor.addMapping("splash is C");
		this.aSentenceProcessor.addMapping("smash is D");
		this.aSentenceProcessor.addMapping("slash is M");
		
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
		this.aSentenceProcessor.addMapping("pish is X");
		this.aSentenceProcessor.addValuation("pish pish Iron is 3910 Credits");
		double ironValue = this.aSentenceProcessor.getVariableValue("Iron");
		
		assertEquals(195.5, ironValue, 0.01);
	}
	
}