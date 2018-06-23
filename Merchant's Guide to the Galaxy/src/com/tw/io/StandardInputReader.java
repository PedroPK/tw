package com.tw.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StandardInputReader {
	
	public static BufferedReader getBufferedReader() {
		BufferedReader bf = null;
		
		InputStream is = System.in;
		InputStreamReader isr = new InputStreamReader(is);
		bf = new BufferedReader(isr);
		
		return bf;
	}

}
