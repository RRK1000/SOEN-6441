import controller.ControllerTestSuite;
import game.GameTestSuite;
import gamelog.GameLogTestSuite;
import orders.OrdersTestSuite;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import phases.PhasesTestSuite;
import util.UtilTestSuite;

@Suite
@SelectClasses({ControllerTestSuite.class, GameLogTestSuite.class, OrdersTestSuite.class,
        PhasesTestSuite.class, UtilTestSuite.class, GameTestSuite.class})
/**
 * Main Test Suite for all tests
 * @author Anuja Somthankar
 */
public class TestSuite {
}





