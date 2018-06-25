package testSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.tw.main.SentenceProcessorTest;
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
	UtilsTest.class,
	SentenceProcessorTest.class
})
public class TestSuite {}