package phases;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({InitMapPhaseTest.class, IssueOrderPhaseTest.class, StartupPhaseTest.class, ExecuteOrderPhaseTest.class, Phase.class})
public class PhasesTestSuite {

}