package com.tw.io;

import java.io.BufferedReader;
import static org.junit.Assert.*;

import org.junit.Test;

public class StandardInputReaderTestCase {
	
	@Test
	public void testGetBufferedReader() {
		BufferedReader bf = StandardInputReader.getBufferedReader();
		
		assertNotNull(bf);
	}
}