package com.tw.io;

import static org.junit.Assert.assertNotNull;

import java.util.Scanner;

import org.junit.Test;

public class StandardInputReaderTestCase {
	
	@Test
	public void testGetScanner() {
		Scanner scanner = StandardInputReader.getScanner();
		
		assertNotNull(scanner);
	}
}