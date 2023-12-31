package util;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit test suite for utility classes.
 */

@Suite
@SelectClasses({CommandUtilTest.class, MapUtilTest.class, DominationMapFileReaderTest.class,
            ConquestMapFileReaderAdapterTest.class})
public class UtilTestSuite {

}