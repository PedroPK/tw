package com.tw.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

import com.tw.utils.Utils;

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
public class ConverterProcessDecimalValuesTests {
	
	//private
	
	@Test
	public void testProcessDecimalValuesFromNull() {
		BigDecimal integerBigDecimal = Converter.processDecimalValues(null);
		
		assertNull(integerBigDecimal);
	}
	
	@Test
	public void testProcessDecimalValuesFrom10String() {
		BigDecimal integerBigDecimal = Converter.processDecimalValues(new BigDecimal("10.0"));
		
		assertNotNull(integerBigDecimal);
		
		BigDecimal ten = new BigDecimal(10);
		
		assertTrue(
			Utils.isEquals(integerBigDecimal, ten)
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