package gamelog;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({LogEntryBufferTest.class, LogFileWriterTest.class})
public class GameLogTestSuite {

}