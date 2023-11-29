package phases;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit test suite for phases classes.
 */
@Suite
@SelectClasses({InitMapPhaseTest.class, IssueOrderPhaseTest.class, StartupPhaseTest.class, ExecuteOrderPhaseTest.class, PhaseTest.class})
public class PhasesTestSuite {

}