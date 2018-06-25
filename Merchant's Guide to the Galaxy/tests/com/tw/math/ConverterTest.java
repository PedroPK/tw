package com.tw.math;

import static com.tw.math.Converter.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.tw.math.exceptions.EmptyRomanException;
import com.tw.math.exceptions.FourTimesRepetitionException;
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
public class ConverterTest {
	
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
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_I() {
		boolean response = hasInvalidConsecutiveRepetitions("I");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_II() {
		boolean response = hasInvalidConsecutiveRepetitions("II");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_III() {
		boolean response = hasInvalidConsecutiveRepetitions("III");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_IIII() {
		boolean response = hasInvalidConsecutiveRepetitions("IIII");
		
		assertTrue(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_X() {
		boolean response = hasInvalidConsecutiveRepetitions("X");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_XX() {
		boolean response = hasInvalidConsecutiveRepetitions("XX");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_XXX() {
		boolean response = hasInvalidConsecutiveRepetitions("XXX");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_XXXX() {
		boolean response = hasInvalidConsecutiveRepetitions("XXXX");
		
		assertTrue(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_C() {
		boolean response = hasInvalidConsecutiveRepetitions("C");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_CC() {
		boolean response = hasInvalidConsecutiveRepetitions("CC");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_CCC() {
		boolean response = hasInvalidConsecutiveRepetitions("CCC");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_CCCC() {
		boolean response = hasInvalidConsecutiveRepetitions("CCCC");
		
		assertTrue(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_M() {
		boolean response = hasInvalidConsecutiveRepetitions("M");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_MM() {
		boolean response = hasInvalidConsecutiveRepetitions("MM");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_MMM() {
		boolean response = hasInvalidConsecutiveRepetitions("MMM");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_MMMM() {
		boolean response = hasInvalidConsecutiveRepetitions("MMMM");
		
		assertTrue(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_IIIIV() {
		boolean response = hasInvalidConsecutiveRepetitions("IIIIV");
		
		assertTrue(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_V() {
		boolean response = hasInvalidConsecutiveRepetitions("V");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_VV() {
		boolean response = hasInvalidConsecutiveRepetitions("VV");
		
		assertTrue(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_VVV() {
		boolean response = hasInvalidConsecutiveRepetitions("VVV");
		
		assertTrue(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_VVVV() {
		boolean response = hasInvalidConsecutiveRepetitions("VVVV");
		
		assertTrue(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_L() {
		boolean response = hasInvalidConsecutiveRepetitions("L");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_LL() {
		boolean response = hasInvalidConsecutiveRepetitions("LL");
		
		assertTrue(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_LLL() {
		boolean response = hasInvalidConsecutiveRepetitions("LLL");
		
		assertTrue(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_LLLL() {
		boolean response = hasInvalidConsecutiveRepetitions("LLLL");
		
		assertTrue(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_D() {
		boolean response = hasInvalidConsecutiveRepetitions("D");
		
		assertFalse(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_DD() {
		boolean response = hasInvalidConsecutiveRepetitions("DD");
		
		assertTrue(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_DDD() {
		boolean response = hasInvalidConsecutiveRepetitions("DDD");
		
		assertTrue(response);
	}
	
	/**
	 * - The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. 
	 * - (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
	 */
	@Test
	public void testRepetitionValid_DDDD() {
		boolean response = hasInvalidConsecutiveRepetitions("DDDD");
		
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
	
	@Test
	public void testGetArabicValueFromNull() {
		int response = Converter.getArabicValue(null);
		
		assertEquals(Integer.MIN_VALUE, response);
	}
	
	@Test
	public void testGetArabicValueFromEmptyString() {
		int response = Converter.getArabicValue("");
		
		assertEquals(Integer.MIN_VALUE, response);
	}
	
	@Test
	public void testGetArabicValueFromSpaceString() {
		int response = Converter.getArabicValue(" ");
		
		assertEquals(Integer.MIN_VALUE, response);
	}
	
	@Test
	public void testGetArabicDigit() {
		int response = Converter.getArabicDigit(' ');
		
		assertEquals(Integer.MIN_VALUE, response);
	}
	
	@Test
	public void testIsArabicValidNull() {
		boolean response = Converter.isArabicValid(null);
		
		assertFalse(response);
	}
	
	@Test
	public void testIsArabicValidEmpty() {
		boolean response = Converter.isArabicValid("");
		
		assertFalse(response);
	}
	
	@Test
	public void testIsArabicValidSpace() {
		boolean response = Converter.isArabicValid(" ");
		
		assertFalse(response);
	}
	
}