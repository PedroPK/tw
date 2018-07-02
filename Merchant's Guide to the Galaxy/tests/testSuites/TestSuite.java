package testSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.tw.main.SentenceProcessorTest;
import com.tw.math.ConverterArabicToRomanTests;
import com.tw.math.ConverterExceptionTests;
import com.tw.math.ConverterGetArabicValueTest;
import com.tw.math.ConverterInvalidTests;
import com.tw.math.ConverterIsArabicValidTests;
import com.tw.math.ConverterIsMappingSentenceTest;
import com.tw.math.ConverterParametizedTests;
import com.tw.math.ConverterTest;
import com.tw.utils.UtilsTest;

/**
 * This Suite executes all the tests at once
 * 
 * @author pedro.c.f.santos
 */

@RunWith(Suite.class)
@SuiteClasses({
	ConverterTest.class,
	ConverterParametizedTests.class,
	ConverterExceptionTests.class,
	ConverterInvalidTests.class,
	ConverterArabicToRomanTests.class,
	ConverterIsArabicValidTests.class,
	ConverterGetArabicValueTest.class,
	ConverterIsMappingSentenceTest.class,
	UtilsTest.class,
	SentenceProcessorTest.class
})
public class TestSuite {}