package com.tw.math;

import static com.tw.math.Converter.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

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
@RunWith(Parameterized.class)
public class ConverterArabicToRomanTests {
	
	private String	aArabic;
	private String	aRoman;
	
	public ConverterArabicToRomanTests(String pArabic, String pRoman) {
		this.aArabic = pArabic;
		this.aRoman = pRoman;
	}
	
	@Parameters
	public static Collection<Object[]> generateTestData() {
		return Arrays.asList(new Object[][] {
			{"1", "I"},
			{"2", "II"},
			{"3", "III"},
			{"4", "IV"},
			{"5", "V"},
			{"6", "VI"},
			{"7", "VII"},
			{"8", "VIII"},
			{"9", "IX"},
			{"10", "X"},
			{"11", "XI"},
			{"12", "XII"},
			{"13", "XIII"},
			{"14", "XIV"},
			{"15", "XV"},
			{"16", "XVI"},
			{"17", "XVII"},
			{"18", "XVIII"},
			{"19", "XIX"},
			{"20", "XX"},
			{"21", "XXI"},
			{"25", "XXV"},
			{"30", "XXX"},
			{"35", "XXXV"},
			{"40", "XL"},
			{"50", "L"},
			{"75", "LXXV"},
			{"100", "C"},
			{"200", "CC"},
			{"300", "CCC"},
			{"400", "CD"},
			{"450", "CDL"},
			{"475", "CDLXXV"},
			{"498", "CDXCVIII"},
			{"500", "D"},
			{"1000", "M"},
			{"1903", "MCMIII"},
			{"1983", "MCMLXXXIII"},
			{"1", "I"},
		});
	}
	
	@Test
	public void testConvertionArabicToRoman() {
		assertEquals(this.aRoman, convertArabicToRoman(this.aArabic));
	}
	
}