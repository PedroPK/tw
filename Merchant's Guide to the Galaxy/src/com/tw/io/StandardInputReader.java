package com.tw.io;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.tw.math.Converter;
import com.tw.sentences.SentenceProcessor;
import static com.tw.utils.Constants.*;
import static com.tw.utils.Utils.*;

public class StandardInputReader {
	
	private SentenceProcessor aConverter;
	
	public StandardInputReader() {
		this.aConverter = new SentenceProcessor();
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
			
			if ( !isStringValid(line) || line.equalsIgnoreCase("Stop") ) {
				break;
			}
			processInputLineRead(line);
		}
		
		System.out.println("Bye! See you soon!");
		scanner.close();
	}
	
	/**
	 * Test input:
	 * 		glob is I
	 * 		prok is V
	 * 		pish is X
	 * 		
	 * Extended Test input
	 * 		splash is C
	 * 		smash is D
	 * 		slash is M
	 * 		
	 * 		glob glob Silver is 34 Credits
	 * 		glob prok Gold is 57800 Credits
	 * 		pish pish Iron is 3910 Credits
	 * 		
	 * 		slash splash slash tegj pish pish pish glob glob glob BitCoin is 1 Credit
	 * 		
	 * 		how much is pish tegj glob glob ?
	 * 		how many Credits is glob prok Silver ?
	 * 		how many Credits is glob prok Gold ?
	 * 		how many Credits is glob prok Iron ?
	 * 		how much wood could a woodchuck chuck if a woodchuck could chuck wood ?
	 * 		
	 * 		how many Credits is pish pish pish prok BitCoin ?
	 * 		how many Credits is pish pish pish prok Ethereum ?
	 * 		how many Credits is Plunct Plact Zum BitCoin 
	 * 
	 * Test Output:
	 * 		pish tegj glob glob is 42
	 * 		glob prok Silver is 68 Credits
	 * 		glob prok Gold is 57800 Credits
	 * 		glob prok Iron is 782 Credits
	 * 		I have no idea what you are talking about
	 * 		
	 * 		pish pish pish prok BitCoin is 0.01765 Credits
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
			System.out.println(I_HAVE_NO_IDEA_WHAT_YOU_ARE_TALKING_ABOUT);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome! These is a Sentence Processor of Roman numbers into Nouns and Variables, to define theys Credit values");
		System.out.println("There are three types of valid sentence.\n");
		System.out.println("The 1st type is the sentences that does an asignment of a Roman Number to a Noun. \nEx: Alice is I. \n");
		System.out.println("The 2nd type is the sentences that uses the Nouns from previous sentences, introduces a new Variable, an do another asignment of a Numerical quantity of Credits. \nEx: Alice Bob is 5 Credits.\n");
		System.out.println("The 3rd type is the sentences that does a Question, based on the Nouns and Variables from previous sentences. \nEx: How Many Credits is Alice Alce Bob ?\n");
		System.out.println("You can start writing sentences below.");
		
		StandardInputReader sir = new StandardInputReader();
		
		sir.readlineFromScanner();
	}
	
}