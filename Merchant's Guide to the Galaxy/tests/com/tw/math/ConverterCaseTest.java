package com.tw.math;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.tw.math.exceptions.EmptyRomanException;
import com.tw.math.exceptions.FourTimesRepetitionException;
import com.tw.math.exceptions.InvalidRomanException;

import static com.tw.math.Converter.*;

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
 *
 */
@FixMethodOrder(MethodSorters.JVM)
public class ConverterCaseTest {
	
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
	public void testMappingGlobToI() {
		Converter converter = new Converter();
		converter.addMapping("glob is I");
		char response = converter.getMapping("glob");
		
		assertEquals('I', response);
	}
	
	/**
	 * prok is V
	 */
	@Test
	public void testMappingProkToV() {
		Converter converter = new Converter();
		converter.addMapping("prok is V");
		char response = converter.getMapping("prok");
		
		assertEquals('V', response);
	}
	
	/**
	 * pish is X
	 */
	@Test
	public void testMappingPishToX() {
		Converter converter = new Converter();
		converter.addMapping("pish is X");
		char response = converter.getMapping("pish");
		
		assertEquals('X', response);
	}
	
	/**
	 * tegj is L
	 */
	@Test
	public void testMappingTegjToL() {
		Converter converter = new Converter();
		converter.addMapping("tegj is L");
		char response = converter.getMapping("tegj");
		
		assertEquals('L', response);
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
	public void testFullMapping() {
		Converter converter = new Converter();
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
		Converter converter = new Converter();
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
		Converter converter = new Converter();
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
		Converter converter = new Converter();
		String response = 
			converter.getSentenceOriginalMultiplier("glob glob Silver is 34 Credits");
		
		assertEquals("glob glob", response);
	}
	
	/**
	 * glob prok Gold is 57800 Credits
	 */
	@Test
	public void testGetOriginalMultiplierGlobProkGoldIs57800Credits() {
		Converter converter = new Converter();
		String response = 
			converter.getSentenceOriginalMultiplier("glob prok Gold is 57800 Credits");
		
		assertEquals("glob prok", response);
	}
	
	/**
	 * pish pish Iron is 3910 Credits
	 */
	@Test
	public void testGetOriginalMultiplierPishPishIronIs3910Credits() {
		Converter converter = new Converter();
		String response = 
			converter.getSentenceOriginalMultiplier("pish pish Iron is 3910 Credits");
		
		assertEquals("pish pish", response);
	}
	
}