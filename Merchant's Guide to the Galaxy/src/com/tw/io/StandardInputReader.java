package com.tw.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class StandardInputReader {
	
	public static BufferedReader getBufferedReader() {
		InputStream is = System.in;
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader bf = new BufferedReader(isr);
		
		return bf;
	}
	
	public static Scanner getScanner() {
		InputStream is = System.in;
		InputStreamReader isr = new InputStreamReader(is);
		Scanner scanner = new Scanner(isr);
		
		return scanner;
	}
	
	public static void readlineFromBufferedReader() throws IOException {
		BufferedReader bf = getBufferedReader();
		String line = bf.readLine();
		
		while ( line != null ) {
			System.out.println("Linha lida = " + line);
			line = bf.readLine();
		}
		
		bf.close();
	}
	
	public static void readlineFromScanner() {
		Scanner scanner = getScanner();
		
		while ( scanner.hasNextLine() ) {
			String line = scanner.nextLine();
			System.out.println("Linha lida = " + line);
		}
		
		scanner.close();
	}
	
	public static void main(String[] args) {
		System.out.println("Iniciando a classe StandardInputReader");
		
		// System.out.println("Tentando ler via BufferedReader");
		//BufferedReader bf = getBufferedReader();
		
		System.out.println("Tentando ler via Scanner");
		Scanner scanner = getScanner();
		
		//try {
			//bf.readLine();
			readlineFromScanner();
		/*} catch (IOException e) {
			//try {
				//bf.close();
				scanner.close();
				System.out.println("Erro ao tentar Ler uma Linha.");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
				System.out.println("Erro ao tentar Fechar o Buffered Reader.");
			}
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

}
