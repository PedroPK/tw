package com.tw.io;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class StandardInputReader {
	
	public static Scanner getScanner() {
		InputStream is = System.in;
		InputStreamReader isr = new InputStreamReader(is);
		Scanner scanner = new Scanner(isr);
		
		return scanner;
	}
	
	
	public static void readlineFromScanner() {
		Scanner scanner = getScanner();
		
		while ( scanner.hasNextLine() ) {
			String line = scanner.nextLine();
			
			System.out.println("Linha lida = " + line);
			
			if ( line.equalsIgnoreCase("Stop") ) {
				break;
			}
		}
		
		scanner.close();
	}
	
	public static void main(String[] args) {
		System.out.println("Iniciando a classe StandardInputReader");
		
		System.out.println("Tentando ler via Scanner");
		
		readlineFromScanner();
	}

}
