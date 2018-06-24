package com.tw.main;

/**
 * You decided to give up on earth after the latest financial collapse left 99.99% of the earth's population with 0.01% of the wealth. 
 * Luckily, with the scant sum of money that is left in your account, you are able to afford to rent a spaceship, leave earth, and fly all over the galaxy to sell common metals and dirt (which apparently is worth a lot).
 * Buying and selling over the galaxy requires you to convert numbers and units, and you decided to write a program to help you.
 * The numbers used for intergalactic transactions follows similar convention to the roman numerals and you have painstakingly collected the appropriate translation between them.
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
 * For example MCMXLIV = 1000 + (1000 − 100) + (50 − 10) + (5 − 1) = 1944.
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
 * @author pedro.c.f.santos
 */
public class MainClass {
	
	/**
	 * This documentation will explain the decisions that I took during this problem solving and its implementation
	 * 
	 * Fist thing that I thought was that I should be original and find the answers how to solve it by my self. It was very likely that I could look for
	 * in the internet for a similar problem and its solution, tested by many people, with more elegant and simple implementations then I would do, 
	 * but in my mind, you guys would want something made by me, so I did this way, organically, knowing that probably there are better ways, more elegant ways, to do the same job.
	 * 
	 * Having said that, the second thing that I was worried about was how to do conversions between Roman numbers to Arabic Numbers.
	 * 
	 * The best way that I know how to do this is using TDD (Test Driven Development). So, I created empty methods, jumped to implement the first test, 
	 * executed it, saw it fail, and jump back to implement a minimal code to make the first test pass, and started creating more elaborated tests, 
	 * keeping this cycle alive, until the moment that I feel that is enough robust to start implementing another method.
	 * 
	 * The third priority was how to map the Nouns (ex: glob, prok, pish, tegj, etc...) with its Roman numbers.
	 * A subpriority was decide if its a valid mapping or not
	 * 
	 * The fourth priority was to process sentences that was a Variable on it, mixed with Nouns and attributing then to Arabic quantity of Credits (ex: glob glob Silver is 34 Credits)
	 * 
	 * Once this was done and with many Tests for many sceneries, the fifth priority was to process the How Much/Many questions, and answer then appropriately
	 * Tests with JUnit also implemented to test this Question/Answers sentences, that was testing all previous implementations by chain reaction
	 * 
	 * Finally, the last thing that was how to use the Standard Input, capture the keyboard text and integrate it with all the rest of application
	 * 
	 * A last effort to be done is to do a great refactoring, doing a better organization, removing unecessary code, making it a little bit better.
	 * 
	 * An additional explanation is that, once I decided to use Tests in almost anything implemented, there are many public methods, not based in  external utility,
	 * but its the way to do Tests with JUnit, without use a more complex frameworks or complicate then with Reflection
	 */
	
}
