package com.tw.utils;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;

public class UtilsTest {
	
	@Test
	public void testSplitNull() {
		List<String> result = Utils.split(null);
		
		assertNull(result);
	}
	
	@Test
	public void testSplitEmpty() {
		List<String> result = Utils.split("");
		
		assertNull(result);
	}
	
	@Test
	public void testSplitStringWithoutSpace() {
		List<String> result = Utils.split("String");
		
		assertNotNull(result);
		assertTrue(result.size() == 1);
	}
	
	@Test
	public void testEqualsZeroNull() {
		boolean result = Utils.equalsZero(null);
		
		assertFalse(result);
	}
	
	@Test
	public void testEqualsZeroBigDecimalZeroConstant() {
		boolean result = Utils.equalsZero(BigDecimal.ZERO);
		
		assertTrue(result);
	}
	
	@Test
	public void testEqualsZeroNewBigDecimalZero() {
		boolean result = Utils.equalsZero(new BigDecimal(0));
		
		assertTrue(result);
	}
	
	@Test
	public void testEqualsZeroNewBigDecimalZeroString() {
		boolean result = Utils.equalsZero(new BigDecimal("0.00"));
		
		assertTrue(result);
	}
	
	@Test
	public void testEqualsZeroNewBigDecimalNotExactlyZeroString() {
		boolean result = Utils.equalsZero(new BigDecimal("0.00000000000000001"));
		
		assertFalse(result);
	}
	
	@Test
	public void testEqualsQuestionMarkNull() {
		boolean result = Utils.isEqualsQuestionMark(null);
		
		assertFalse(result);
	}
	
	@Test
	public void testEqualsQuestionMarkEmptyString() {
		boolean result = Utils.isEqualsQuestionMark("");
		
		assertFalse(result);
	}
	
	@Test
	public void testIsEqualsBigDecimalsNullNull() {
		boolean result = Utils.isEquals(null, null);
		
		assertFalse(result);
	}
	
	@Test
	public void testIsEqualsBigDecimalsZeroNull() {
		boolean result = Utils.isEquals(BigDecimal.ZERO, null);
		
		assertFalse(result);
	}
	
	@Test
	public void testIsEqualsBigDecimalsNullZero() {
		boolean result = Utils.isEquals(null, BigDecimal.ZERO);
		
		assertFalse(result);
	}
	
	@Test
	public void testIsEqualsBigDecimalsZeroZero() {
		boolean result = Utils.isEquals(BigDecimal.ZERO, BigDecimal.ZERO);
		
		assertTrue(result);
	}
	
	@Test
	public void testIsEqualsBigDecimalsZeroNewZero() {
		boolean result = Utils.isEquals(BigDecimal.ZERO, new BigDecimal(0));
		
		assertTrue(result);
	}
	
	@Test
	public void testIsEqualsBigDecimalsZeroNewZeroPointZeroSixTimes() {
		boolean result = Utils.isEquals(BigDecimal.ZERO, new BigDecimal(0.000000));
		
		assertTrue(result);
	}
	
	@Test
	public void testIsEqualsBigDecimalsZeroNewZeroPointZeroSixTimesString() {
		boolean result = Utils.isEquals(BigDecimal.ZERO, new BigDecimal("0.000000"));
		
		assertTrue(result);
	}
	
	@Test
	public void testIsEqualsBigDecimalsZeroNewZeroPointZeroSixTimesOneString() {
		boolean result = Utils.isEquals(BigDecimal.ZERO, new BigDecimal("0.0000001"));
		
		assertFalse(result);
	}
	
	@Test
	public void testIsEqualsBigDecimalsZeroOne() {
		boolean result = Utils.isEquals(BigDecimal.ZERO, BigDecimal.ONE);
		
		assertFalse(result);
	}
	
	@Test
	public void testIsEqualsZeroBigDecimalZero() {
		boolean result = Utils.isEqualsZero(BigDecimal.ZERO);
		
		assertTrue(result);
	}
}