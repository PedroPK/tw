package com.tw.math;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Scanner;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.tw.math.exceptions.EmptyRomanException;
import com.tw.math.exceptions.FourTimesRepetitionException;
import com.tw.math.exceptions.InvalidRomanException;
import com.tw.sentences.SentenceProcessor;

import static com.tw.math.Converter.*;
import static com.tw.utils.Constants.*;

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
 * @author pedroc.f.santos
 */
@FixMethodOrder(MethodSorters.JVM)
public class ConverterCaseTest {
	
	SentenceProcessor aSentenceProcessor;
	
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
	public void prepareConverter() {
		this.aSentenceProcessor = new SentenceProcessor();
		
		// Original Test cases
		aSentenceProcessor.addMapping("glob is I");
		aSentenceProcessor.addMapping("prok is V");
		aSentenceProcessor.addMapping("pish is X");
		aSentenceProcessor.addMapping("tegj is L");
		
		// Extended Test cases
		aSentenceProcessor.addMapping("splash is C");
		aSentenceProcessor.addMapping("smash is D");
		aSentenceProcessor.addMapping("slash is M");
		
		// Original Test cases
		this.aSentenceProcessor.addValuation("glob glob Silver is 34 Credits");
		this.aSentenceProcessor.addValuation("glob prok Gold is 57800 Credits");
		this.aSentenceProcessor.addValuation("pish pish Iron is 3910 Credits");
		
		// Extended Test cases
		this.aSentenceProcessor.addValuation("slash splash slash tegj pish pish pish glob glob glob BitCoin is 1 Credit");
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
		aSentenceProcessor.addMapping("glob is I");
		aSentenceProcessor.addMapping("prok is V");
		aSentenceProcessor.addMapping("pish is X");
		aSentenceProcessor.addMapping("tegj is L");
		aSentenceProcessor.addMapping("splash is C");
		aSentenceProcessor.addMapping("smash is D");
		aSentenceProcessor.addMapping("slash is M");
		
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
		aSentenceProcessor.addMapping("pish is X");
		this.aSentenceProcessor.addValuation("pish pish Iron is 3910 Credits");
		double ironValue = this.aSentenceProcessor.getVariableValue("Iron");
		
		assertEquals(195.5, ironValue, 0.01);
	}
	
	@Test(expected=EmptyRomanException.class)
	public void testNullConvertionRomanToArabic() {
		int response = convertRomanToArabic(null);
		
		assertEquals(0, response);
	}
	
	@Test(expected=EmptyRomanException.class)
	public void testEmptyConvertionRomanToArabic() {
		int response = convertRomanToArabic("");
		
		assertEquals(0, response);
	}
	
	@Test
	public void test_I_ConvertionRomanToArabic() {
		int response = convertRomanToArabic( "I" );
		
		assertEquals(1, response);
	}
	
	@Test
	public void test_V_ConvertionRomanToArabic() {
		int response = convertRomanToArabic( "V" );
		
		assertEquals(5, response);
	}
	
	@Test
	public void test_X_ConvertionRomanToArabic() {
		int response = convertRomanToArabic( "X" );
		
		assertEquals(10, response);
	}
	
	@Test
	public void test_L_ConvertionRomanToArabic() {
		int response = convertRomanToArabic( "L" );
		
		assertEquals(50, response);
	}
	
	@Test
	public void testCConvertionRomanToArabic() {
		int response = convertRomanToArabic( "C" );
		
		assertEquals(100, response);
	}
	
	@Test
	public void test_D_ConvertionRomanToArabic() {
		int response = convertRomanToArabic( "D" );
		
		assertEquals(500, response);
	}
	
	@Test
	public void test_M_ConvertionRomanToArabic() {
		int response = convertRomanToArabic( "M" );
		
		assertEquals(1000, response);
	}
	
	@Test
	public void test_II_ConvertionRomanToArabic() {
		int response = convertRomanToArabic( "II" );
		
		assertEquals(2, response);
	}
	
	@Test
	public void test_III_ConvertionRomanToArabic() {
		int response = convertRomanToArabic( "III" );
		
		assertEquals(3, response);
	}
	
	@Test(expected=FourTimesRepetitionException.class)
	public void test_IIII_ConvertionRomanToArabic() {
		convertRomanToArabic( "IIII" );
	}
	
	/**
	 * Numbers are formed by combining symbols together and adding the values. For example, MMVI is 1000 + 1000 + 5 + 1 = 2006. 
	 */
	@Test
	public void test_MMVI_ConvertionRomanToArabic() {
		int response = convertRomanToArabic( "MMVI" );
		
		assertEquals(2006, response);
	}
	
	/**
	 * For example MCMXLIV = 1000 + (1000 − 100) + (50 − 10) + (5 − 1) = 1944.
	 */
	@Test
	public void test_MCMXLIV_ConvertionRomanToArabic() {
		int response = convertRomanToArabic( "MCMXLIV" );
		
		assertEquals(1944, response);
	}
	
	@Test
	public void testRepetitionValid_I() {
		boolean response = has4ConsecutiveRepetitions("I");
		
		assertFalse(response);
	}
	
	@Test
	public void testRepetitionValid_II() {
		boolean response = has4ConsecutiveRepetitions("II");
		
		assertFalse(response);
	}
	
	@Test
	public void testRepetitionValid_III() {
		boolean response = has4ConsecutiveRepetitions("III");
		
		assertFalse(response);
	}
	
	@Test
	public void testRepetitionValid_IIII() {
		boolean response = has4ConsecutiveRepetitions("IIII");
		
		assertTrue(response);
	}
	
	@Test
	public void testRepetitionValid_IIIIV() {
		boolean response = has4ConsecutiveRepetitions("IIIIV");
		
		assertTrue(response);
	}
	
	/**
	 * I is Smaller the M, and shouldn't be a valid Roman Number 
	 */
	@Test(expected=InvalidRomanException.class)
	public void testInvalid_IM() {
		convertRomanToArabic("IM");
	}
	
	/**
	 * M is Bigger the I, and should be a valid Roman Number 
	 */
	@Test
	public void testValid_MI() {
		int response = convertRomanToArabic("MI");
		
		assertEquals(1001, response);
	}
	
	/**
	 * "I" can be subtracted from "V" and "X" only. 
	 */
	@Test(expected=InvalidRomanException.class)
	public void testInvalid_MIM() {
		convertRomanToArabic("MIM");
	}
	
	@Test(expected=InvalidRomanException.class)
	public void testInvalidRoman_0() {
		String response = convertArabicToRoman("0");
		
		assertNotNull(response);
	}
	
	@Test
	public void testValidRoman_1() {
		String response = convertArabicToRoman("1");
		
		assertEquals("I", response);
	}
	
	@Test
	public void testValidRoman_5() {
		String response = convertArabicToRoman("5");
		
		assertEquals("V", response);
	}
	
	@Test
	public void testValidRoman_10() {
		String response = convertArabicToRoman("10");
		
		assertEquals("X", response);
	}
	
	@Test
	public void testValidRoman_11() {
		String response = convertArabicToRoman("11");
		
		assertEquals("XI", response);
	}
	
	@Test
	public void testValidRoman_12() {
		String response = convertArabicToRoman("12");
		
		assertEquals("XII", response);
	}
	
	@Test
	public void testValidRoman_13() {
		String response = convertArabicToRoman("13");
		
		assertEquals("XIII", response);
	}
	
	@Test
	public void testValidRoman_14() {
		String response = convertArabicToRoman("14");
		
		assertEquals("XIV", response);
	}
	
	@Test
	public void testValidRoman_15() {
		String response = convertArabicToRoman("15");
		
		assertEquals("XV", response);
	}
	
	@Test
	public void testValidRoman_16() {
		String response = convertArabicToRoman("16");
		
		assertEquals("XVI", response);
	}
	
	@Test
	public void testValidRoman_17() {
		String response = convertArabicToRoman("17");
		
		assertEquals("XVII", response);
	}
	
	@Test
	public void testValidRoman_18() {
		String response = convertArabicToRoman("18");
		
		assertEquals("XVIII", response);
	}
	
	@Test
	public void testValidRoman_19() {
		String response = convertArabicToRoman("19");
		
		assertEquals("XIX", response);
	}
	
	@Test
	public void testValidRoman_20() {
		String response = convertArabicToRoman("20");
		
		assertEquals("XX", response);
	}
	
	@Test
	public void testValidRoman_21() {
		String response = convertArabicToRoman("21");
		
		assertEquals("XXI", response);
	}
	
	@Test
	public void testValidRoman_25() {
		String response = convertArabicToRoman("25");
		
		assertEquals("XXV", response);
	}
	
	@Test
	public void testValidRoman_30() {
		String response = convertArabicToRoman("30");
		
		assertEquals("XXX", response);
	}
	
	@Test
	public void testValidRoman_35() {
		String response = convertArabicToRoman("35");
		
		assertEquals("XXXV", response);
	}
	
	@Test
	public void testValidRoman_40() {
		String response = convertArabicToRoman("40");
		
		assertEquals("XL", response);
	}
	
	@Test
	public void testValidRoman_50() {
		String response = convertArabicToRoman("50");
		
		assertEquals("L", response);
	}
	
	@Test
	public void testValidRoman_75() {
		String response = convertArabicToRoman("75");
		
		assertEquals("LXXV", response);
	}
	
	@Test
	public void testValidRoman_100() {
		String response = convertArabicToRoman("100");
		
		assertEquals("C", response);
	}
	
	@Test
	public void testValidRoman_200() {
		String response = convertArabicToRoman("200");
		
		assertEquals("CC", response);
	}
	
	@Test
	public void testValidRoman_300() {
		String response = convertArabicToRoman("300");
		
		assertEquals("CCC", response);
	}
	
	@Test
	public void testValidRoman_400() {
		String response = convertArabicToRoman("400");
		
		assertEquals("CD", response);
	}
	
	@Test
	public void testValidRoman_450() {
		String response = convertArabicToRoman("450");
		
		assertEquals("CDL", response);
	}
	
	@Test
	public void testValidRoman_475() {
		String response = convertArabicToRoman("475");
		
		assertEquals("CDLXXV", response);
	}
	
	@Test
	public void testValidRoman_498() {
		String response = convertArabicToRoman("498");
		
		assertEquals("CDXCVIII", response);
	}
	
	@Test
	public void testValidRoman_500() {
		String response = convertArabicToRoman("500");
		
		assertEquals("D", response);
	}
	
	@Test
	public void testValidRoman_1000() {
		String response = convertArabicToRoman("1000");
		
		assertEquals("M", response);
	}
	
	/**
	 * Therefore, 1903 = MCMIII.
	 */
	@Test
	public void testValidRoman_1903() {
		String response = convertArabicToRoman("1903");
		
		assertEquals("MCMIII", response);
	}
	
	@Test
	public void testValidRoman_1983() {
		String response = convertArabicToRoman("1983");
		
		assertEquals("MCMLXXXIII", response);
	}
	
	@Test
	public void testValidRoman_2() {
		String response = convertArabicToRoman("2");
		
		assertEquals("II", response);
	}
	
	@Test
	public void testValidRoman_3() {
		String response = convertArabicToRoman("3");
		
		assertEquals("III", response);
	}
	
	@Test
	public void testValidRoman_4() {
		String response = convertArabicToRoman("4");
		
		assertEquals("IV", response);
	}
	
	@Test
	public void testValidRoman_6() {
		String response = convertArabicToRoman("6");
		
		assertEquals("VI", response);
	}
	
	@Test
	public void testValidRoman_7() {
		String response = convertArabicToRoman("7");
		
		assertEquals("VII", response);
	}
	
	@Test
	public void testValidRoman_8() {
		String response = convertArabicToRoman("8");
		
		assertEquals("VIII", response);
	}
	
	@Test
	public void testValidRoman_9() {
		String response = convertArabicToRoman("9");
		
		assertEquals("IX", response);
	}
	
	/**
	 * glob is I
	 */
	@Test
	public void testIsMappingSentence_GlobIsI() {
		boolean response = Converter.isMappingSentence("glob is I");
		
		assertTrue(response);
	}
	
	/**
	 * glob is A
	 */
	@Test
	public void testIsMappingSentence_GlobIsA() {
		boolean response = Converter.isMappingSentence("glob is A");
		
		assertFalse(response);
	}
	
	/**
	 * glob is V
	 */
	@Test
	public void testIsMappingSentence_GlobIsV() {
		boolean response = Converter.isMappingSentence("glob is V");
		
		assertTrue(response);
	}
	
	/**
	 * glob are X
	 */
	@Test
	public void testIsMappingSentence_GlobIsX() {
		boolean response = Converter.isMappingSentence("glob are X");
		
		assertFalse(response);
	}
	
	/**
	 * glob is I
	 */
	@Test
	public void testMappingGlobToI() {
		SentenceProcessor converter = new SentenceProcessor();
		converter.addMapping("glob is I");
		char response = converter.getMapping("glob");
		
		assertEquals('I', response);
	}
	
	/**
	 * prok is V
	 */
	@Test
	public void testMappingProkToV() {
		SentenceProcessor converter = new SentenceProcessor();
		converter.addMapping("prok is V");
		char response = converter.getMapping("prok");
		
		assertEquals('V', response);
	}
	
	/**
	 * pish is X
	 */
	@Test
	public void testMappingPishToX() {
		SentenceProcessor converter = new SentenceProcessor();
		converter.addMapping("pish is X");
		char response = converter.getMapping("pish");
		
		assertEquals('X', response);
	}
	
	/**
	 * tegj is L
	 */
	@Test
	public void testMappingTegjToL() {
		SentenceProcessor converter = new SentenceProcessor();
		converter.addMapping("tegj is L");
		char response = converter.getMapping("tegj");
		
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
		SentenceProcessor converter = new SentenceProcessor();
		converter.addMapping("glob is I");
		converter.addMapping("prok is V");
		converter.addMapping("pish is X");
		converter.addMapping("tegj is L");
		
		assertEquals('I', converter.getMapping("glob"));
		assertEquals('V', converter.getMapping("prok"));
		assertEquals('X', converter.getMapping("pish"));
		assertEquals('L', converter.getMapping("tegj"));
	}
	
	/**
	 * glob is I
	 * glob glob Silver is 34 Credits
	 */
	@Test
	public void testIsValuationSentenceGlobGlobSilverIs34Credits() {
		SentenceProcessor converter = new SentenceProcessor();
		converter.addMapping("glob is I");
		
		assertTrue(converter.isValuationSentence("glob glob Silver is 34 Credits"));
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
		SentenceProcessor converter = new SentenceProcessor();
		converter.addMapping("glob is I");
		converter.addMapping("prok is V");
		converter.addMapping("pish is X");
		converter.addMapping("tegj is L");
		
		assertTrue(converter.isValuationSentence("glob glob Silver is 34 Credits"));
	}
	
	/**
	 * glob glob Silver is 34 Credits
	 */
	@Test
	public void testGetOriginalMultiplierGlobGlogSilverIs34Credits() {
		String response = 
			aSentenceProcessor.getSentenceOriginalMultiplier("glob glob Silver is 34 Credits");
		
		assertEquals("glob glob", response);
	}
	
	/**
	 * glob prok Gold is 57800 Credits
	 */
	@Test
	public void testGetOriginalMultiplierGlobProkGoldIs57800Credits() {
		String response = 
			aSentenceProcessor.getSentenceOriginalMultiplier("glob prok Gold is 57800 Credits");
		
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
	public void testConvertionOriginalMultiplierToRoman_GlobGlobSilverIs34Credits() {
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
	public void testConvertionOriginalMultiplierToRoman_GlobProkGoldIs57800Credits() {
		String romanMultiplier = this.aSentenceProcessor.extratMultipliersAndConvertToRoman("glob prok Gold is 57800 Credits");
		
		assertEquals("IV", romanMultiplier);
	}
	
	/**
	 * pish is X
	 * 
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testConvertionOriginalMultiplierToRoman_PishPishIronIs3910Credits() {
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
	 * 		how many Credits is pish pish pish prok BitCoin ?
	 * 		
	 * Test Output:
	 * 		pish pish pish prok BitCoin is 0.01765 Credits
	 */
	@Test
	public void testProcessHowSentence_howManyCreditsIsPishPishPishProkBitcoinQuestionMark() {
		// Original Test cases
		aSentenceProcessor.addMapping("glob is I");
		aSentenceProcessor.addMapping("prok is V");
		aSentenceProcessor.addMapping("pish is X");
		aSentenceProcessor.addMapping("tegj is L");
		
		// Extended Test cases
		aSentenceProcessor.addMapping("splash is C");
		aSentenceProcessor.addMapping("smash is D");
		aSentenceProcessor.addMapping("slash is M");
		
		// Extended Test cases
		this.aSentenceProcessor.addValuation("slash splash slash tegj pish pish pish glob glob glob BitCoin is 1 Credit");
		
		String answer = this.aSentenceProcessor.processHowSentence("how many Credits is pish pish pish prok BitCoin ?");
		
		assertEquals("pish pish pish prok BitCoin is 0.01765 Credits", answer);
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
	 * 		how many Credits is pish pish pish prok Ethereum ?
	 * 		
	 * Test Output:
	 * 		I have no idea what you are talking about
	 */
	@Test
	public void testProcessHowSentence_howManyCreditsIsPishPishPishProkEthereumQuestionMark() {
		// Original Test cases
		aSentenceProcessor.addMapping("glob is I");
		aSentenceProcessor.addMapping("prok is V");
		aSentenceProcessor.addMapping("pish is X");
		aSentenceProcessor.addMapping("tegj is L");
		
		// Extended Test cases
		aSentenceProcessor.addMapping("splash is C");
		aSentenceProcessor.addMapping("smash is D");
		aSentenceProcessor.addMapping("slash is M");
		
		// Extended Test cases
		this.aSentenceProcessor.addValuation("slash splash slash tegj pish pish pish glob glob glob BitCoin is 1 Credit");
		
		String answer = this.aSentenceProcessor.processHowSentence("how many Credits is pish pish pish prok Ethereum ?");
		
		assertEquals(I_HAVE_NO_IDEA_WHAT_YOU_ARE_TALKING_ABOUT, answer);
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
	public void testProcessHowSentence_howManyCreditsIsPlunctPlactZumBitcoinQuestionMark() {
		// Original Test cases
		aSentenceProcessor.addMapping("glob is I");
		aSentenceProcessor.addMapping("prok is V");
		aSentenceProcessor.addMapping("pish is X");
		aSentenceProcessor.addMapping("tegj is L");
		
		// Extended Test cases
		aSentenceProcessor.addMapping("splash is C");
		aSentenceProcessor.addMapping("smash is D");
		aSentenceProcessor.addMapping("slash is M");
		
		// Extended Test cases
		this.aSentenceProcessor.addValuation("slash splash slash tegj pish pish pish glob glob glob BitCoin is 1 Credit");
		
		String answer = this.aSentenceProcessor.processHowSentence("how many Credits is Plunct Plact Zum BitCoin ?");
		
		assertEquals(I_HAVE_NO_IDEA_WHAT_YOU_ARE_TALKING_ABOUT, answer);
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
		aSentenceProcessor.addMapping("glob is I");
		aSentenceProcessor.addMapping("prok is V");
		aSentenceProcessor.addMapping("pish is X");
		//aConverter.addMapping("tegj is L");
		
		// Extended Test cases
		aSentenceProcessor.addMapping("splash is C");
		aSentenceProcessor.addMapping("smash is D");
		aSentenceProcessor.addMapping("slash is M");
		
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
	
	@Test
	public void testProcessDecimalValuesFromNull() {
		BigDecimal integerBigDecimal = Converter.processDecimalValues(null);
		
		assertNull(integerBigDecimal);
	}
	
	@Test
	public void testProcessDecimalValuesFrom10String() {
		BigDecimal integerBigDecimal = Converter.processDecimalValues(new BigDecimal("10.0"));
		
		assertNotNull(integerBigDecimal);
		
		assertTrue(
			integerBigDecimal.compareTo(new BigDecimal(10)) == 0
		);
	}
	
	@Test
	public void testProcessDecimalValuesFrom10Point1String() {
		BigDecimal integerBigDecimal = Converter.processDecimalValues(new BigDecimal("10.1"));
		
		assertNotNull(integerBigDecimal);
		
		assertFalse(
			integerBigDecimal.compareTo(new BigDecimal(10)) == 0
		);
	}
	
	@Test
	public void testProcessDecimalValuesFrom10Point01String() {
		BigDecimal integerBigDecimal = Converter.processDecimalValues(new BigDecimal("10.01"));
		
		assertNotNull(integerBigDecimal);
		
		assertFalse(
			integerBigDecimal.compareTo(new BigDecimal(10)) == 0
		);
	}
	
	@Test
	public void testProcessDecimalValuesFrom10Point001String() {
		BigDecimal integerBigDecimal = Converter.processDecimalValues(new BigDecimal("10.001"));
		
		assertNotNull(integerBigDecimal);
		
		assertFalse(
			integerBigDecimal.compareTo(new BigDecimal(10)) == 0
		);
	}
	
	@Test
	public void testProcessDecimalValuesFrom10Point0001String() {
		BigDecimal integerBigDecimal = Converter.processDecimalValues(new BigDecimal("10.0001"));
		
		assertNotNull(integerBigDecimal);
		
		assertFalse(
			integerBigDecimal.compareTo(new BigDecimal(10)) == 0
		);
	}
	
	@Test
	public void testProcessDecimalValuesFrom10Point00001String() {
		BigDecimal integerBigDecimal = Converter.processDecimalValues(new BigDecimal("10.00001"));
		
		assertNotNull(integerBigDecimal);
		
		assertFalse(
			integerBigDecimal.compareTo(new BigDecimal(10)) == 0
		);
	}
	
	@Test
	public void testProcessDecimalValuesFrom10Point000001String() {
		BigDecimal integerBigDecimal = Converter.processDecimalValues(new BigDecimal("10.000001"));
		
		assertNotNull(integerBigDecimal);
		
		assertTrue(
			integerBigDecimal.compareTo(new BigDecimal(10)) == 0
		);
	}
	
}