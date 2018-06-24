package testSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.tw.math.ConverterCaseTest;
import com.tw.utils.UtilsCaseTest;

@RunWith(Suite.class)
@SuiteClasses({
	ConverterCaseTest.class,
	UtilsCaseTest.class
}
)
public class TestSuite {

}