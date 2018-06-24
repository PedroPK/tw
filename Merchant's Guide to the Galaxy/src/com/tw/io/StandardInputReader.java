package com.tw.io;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.tw.math.Converter;

public class StandardInputReader {
	
	private Converter aConverter;
	
	public StandardInputReader() {
		this.aConverter = new Converter();
	}
	
	public static Scanner getScanner() {
		InputStream is = System.in;
		InputStreamReader isr = new InputStreamReader(is);
		Scanner scanner = new Scanner(isr);
		
		return scanner;
	}
	
	
	public void readlineFromScanner() {
		Scanner scanner = getScanner();
		
		while ( scanner.hasNextLine() ) {
			String line = scanner.nextLine();
			
			//System.out.println("Linha lida = " + line);
			
			if ( line.equalsIgnoreCase("Stop") ) {
				break;
			}
			processInputLineRead(line);
		}
		
		scanner.close();
	}
	
	/**
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
	 * @param pReadLine
	 */
	public void processInputLineRead(String pReadLine) {
		/* Here I have to receive a Sentence, and do this:
		 * 		Define what kind of sentence it is:
		 * 			Unit to Value attribution
		 * 			Variable attribution
		 * 			Question sentence
		 * 		Call the right method do process this sentence in Converter
		 * 		If its a Question Sentence, to Print the Response sentence
		 */
		if (
				Converter.isMappingSentence(pReadLine)				||
				aConverter.isValuationSentence(pReadLine)			||
				aConverter.isHowMuchManySentenceValid(pReadLine)
		) {
			if (	Converter.isMappingSentence(pReadLine)		) {
				aConverter.addMapping(pReadLine);
			} else if ( aConverter.isValuationSentence(pReadLine)	) {
				aConverter.addValuation(pReadLine);
			} else if ( aConverter.isHowMuchManySentenceValid(pReadLine) ) {
				String response = aConverter.processHowSentence(pReadLine);
				System.out.println(response);
			}
		} else {
			System.out.println("I have no idea what you are talking about");
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Iniciando a classe StandardInputReader");
		System.out.println("Tentando ler via Scanner");
		
		StandardInputReader sir = new StandardInputReader();
		
		sir.readlineFromScanner();
	}
	
}