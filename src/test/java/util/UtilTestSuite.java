package util;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({CommandUtilTest.class, MapUtilTest.class})
public class UtilTestSuite {

}