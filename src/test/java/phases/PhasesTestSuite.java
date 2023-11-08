package phases;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({InitMapPhaseTest.class, IssueOrderPhaseTest.class, StartupPhaseTest.class})
/**
 * Test suite for phases package
 * @author Anuja Somthankar
 */
public class PhasesTestSuite {

}