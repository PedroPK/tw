package testSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.tw.math.ConverterTest;
import com.tw.sentences.SentenceProcessorTest;
import com.tw.utils.UtilsTest;

@RunWith(Suite.class)
@SuiteClasses({
	ConverterTest.class,
	UtilsTest.class,
	SentenceProcessorTest.class
})
public class TestSuite {

}